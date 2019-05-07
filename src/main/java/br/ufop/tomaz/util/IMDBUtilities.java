package br.ufop.tomaz.util;

import br.ufop.tomaz.model.Actor;
import br.ufop.tomaz.model.SerieAndMovieInterface.Category;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.net.ssl.HttpsURLConnection;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

public class IMDBUtilities {

    // -------------------------------------- STATIC VARIABLES --------------------------------------------//

    // This is the suffix that have to be add in url to search some season(?) in IMDB database.
    private static final String SEARCH_SEASON_SUFFIX = "episodes?season=";

    // ----------------------------------------------------------------------------------------------------//

    // -------------------------------------- INSTANCE VARIABLES --------------------------------------------//
    private Document imdbPage;
    private URL url;
    private final StringProperty title = new SimpleStringProperty("");
    // ----------------------------------------------------------------------------------------------------//


    public IMDBUtilities(String title) {
        init(title);
    }

    private String prepareStringUrlToSearch(String str) {
        String urlBuilder = "https://www.imdb.com/find?ref_=nv_sr_fn&q=" +
                str.replace(" ", "+") + "&s=all";
        return urlBuilder;
    }

    public String getDescription() {
        StringBuilder description = new StringBuilder();
        Element descriptionContent = imdbPage.getElementsByClass("summary_text").first();
        description.append(descriptionContent.text());
        return description.toString();
    }

    public List<Category> getCategories() {
        List<Category> categories = new ArrayList<>();
        List<Element> htmlCategories = imdbPage.getElementsByClass("subtext").first()
                .getElementsByTag("a")
                .stream()
                .filter((element -> element.attr("href").contains("genres")))
                .collect(Collectors.toList());
        htmlCategories.forEach(element -> {
            String category = element.text()
                    .replace("-", "_")
                    .replace(" ", "_")
                    .toUpperCase();
            categories.add(Category.valueOf(category));
        });

        return categories;
    }

    public Image getImageCover() { //TODO -- Download the large version of image
        Element divPoster = imdbPage.getElementsByClass("poster").first();
        String separator = System.getProperty("file.separator");
        String imgUrl = divPoster.getElementsByTag("img").first().attr("src");
        String dir = System.getProperty("user.home");
        File directory = new File(dir);
        directory.mkdirs();
        return downloadImage(title.getValue(),imgUrl,dir);
    }

    public int getRate() {
        Element divRating = imdbPage.getElementsByClass("ratingValue").first();
        String rate = divRating.getElementsByAttributeValue("itemprop", "ratingValue").first().text();
        return ((Number) Double.parseDouble(rate)).intValue();
    }

    public Map<Integer, String> getEpisodesDescription(int season) {
        Map<Integer, String> mapEpisodesInformation = new HashMap<>();
        Document seasonIMDBPage = getSeasonPage(season);
        if (seasonIMDBPage != null) {
            List<Element> episodesDescription = seasonIMDBPage.getElementsByClass("item_description");
            episodesDescription.forEach(ep -> mapEpisodesInformation.put(episodesDescription.indexOf(ep), ep.text()));
        }
        return mapEpisodesInformation;
    }

    private Document getSeasonPage(Integer season) {
        Document seasonIMDBPage;
        String pageBaseUrl = imdbPage.location();
        String urlSeasonPage = pageBaseUrl.concat(SEARCH_SEASON_SUFFIX).concat(season.toString());
        try {
            seasonIMDBPage = Jsoup.connect(urlSeasonPage).get();
            return seasonIMDBPage;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void init(String title) {
        try {
            Document document = Jsoup.connect(prepareStringUrlToSearch(title)).get();
            Element findSeries = document.getElementsByClass("findList").first(); //This capture the series that matches with the search
            Element series = findSeries.getElementsByClass("findResult odd").first(); // This capture the first match series
            StringBuilder strURL = new StringBuilder();
            strURL.append("https://www.imdb.com").append(series.getAllElements().attr("href")); //This capture the URL of the series
            this.url = new URL(strURL.substring(0, strURL.indexOf("?")));
            // This is the WebPage of the series in IMDB with all seasons
            this.imdbPage = Jsoup.connect(url.toString()).get();

            String titleIMDB = imdbPage.getElementsByClass("originalTitle")
                    .first().text()
                    .replace("(original title)","")
                    .trim();
            setTitle(titleIMDB);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            setTitle(title);
        }
    }

    public Map<Integer, String> getEpisodesTitles(Integer season) {
        Document seasonIMDBPage = getSeasonPage(season);
        if (seasonIMDBPage != null) {
            Map<Integer, String> mapEpisodesTitles = new HashMap<>();
            List<Element> episodes = seasonIMDBPage.getElementsByClass("list detail eplist")
                    .first()
                    .getElementsByAttributeValue("itemprop", "name");
            episodes.forEach(element -> {
                int epNumber = episodes.indexOf(element);
                String title = element.text();
                mapEpisodesTitles.put(epNumber, title);
            });
            return mapEpisodesTitles;
        } else return null;
    }

    public Map<Integer, Image> getEpisodesImages(Integer season) {

        ConcurrentMap<Integer, Image> mapEpisodesImages = new ConcurrentHashMap<>();
        Document seasonIMDBPage = getSeasonPage(season);
        String separator = System.getProperty("file.separator");
        String seasonImagesPath = System.getProperty("user.home")
                .concat(separator).concat("Series")
                .concat(separator).concat(title.getValue())
                .concat(separator).concat("Season ")
                .concat(season.toString()).concat(separator)
                .concat("Images");
        File directory = new File(seasonImagesPath);
        directory.mkdirs();

        List<Element> imagesContent = seasonIMDBPage.getElementsByClass("image");
        List<String> imagesUrl = new ArrayList<>();
        imagesContent.forEach((e) -> {
            String url = e.getElementsByTag("img").first()
                    .attr("src");
            imagesUrl.add(url);
        });

        imagesUrl.parallelStream()
                 .forEach((url) -> {
                    Integer imgNumber = imagesUrl.indexOf(url) + 1;
                    String imgName = "Episode_".concat(imgNumber.toString());
                    Image img = downloadImage(imgName, url, seasonImagesPath);
                    mapEpisodesImages.put(imgNumber, img);
                });

        return mapEpisodesImages;

    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    private CustomImage downloadImage(String imgName, String imgUrl, String dirUrl) {
        String fileExtension = imgUrl.substring(imgUrl.lastIndexOf("."));
        String separator = System.getProperty("file.separator");
        File dir = new File(dirUrl);
        if(!dir.exists())
            dir.mkdirs();
        File imgFile = new File(dirUrl.concat(separator).concat(imgName).concat(fileExtension));
        CustomImage image;
        HttpsURLConnection connection = null;
        try {
            if(!imgFile.exists()) {
                connection = (HttpsURLConnection) new URL(imgUrl).openConnection();
                Files.copy(connection.getInputStream(), imgFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
            image = new CustomImage(Files.newInputStream(imgFile.toPath(), StandardOpenOption.READ),imgFile.getPath());
            return image;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if(connection != null)
                connection.disconnect();
        }
    }

    public Map<Integer, Date> getEpisodesReleaseDates(Integer season) {
        Map<Integer,Date> releaseDatesMap = new HashMap<>();
        Document seasonIMDBPage = getSeasonPage(season);
        List<Element> htmlDates = seasonIMDBPage.getElementsByClass("airdate");
        DateFormat dateFormat = new SimpleDateFormat("d MMM. yyyy",Locale.US);

        htmlDates.forEach((element)->{
            String strDate = element.text();
            int episodeNumber = htmlDates.indexOf(element);
            try {
                Date releaseDate = dateFormat.parse(strDate);
                releaseDatesMap.put(episodeNumber,releaseDate);
            } catch (ParseException e) {
                releaseDatesMap.put(episodeNumber,null);
                e.printStackTrace();
            }
        });
        return releaseDatesMap;
    }

    public Map<Integer, Integer> getEpisodesRates(Integer season) {
        Map<Integer,Integer> ratesMap = new HashMap<>();
        Document seasonIMDBPage = getSeasonPage(season);
        List<Element> divsRates = seasonIMDBPage.getElementsByClass("ipl-rating-star small");
        divsRates.forEach(element -> {
            Element e = element.getElementsByClass("ipl-rating-star__rating").first();
            Number rate = Double.parseDouble(e.text());
            int episodeNumber = divsRates.indexOf(element);
            ratesMap.put(episodeNumber,rate.intValue());
        });
        return ratesMap;
    }

    public Date getReleaseDate() {
        Element divReleaseDate = imdbPage.getElementById("titleDetails")
                                         .getElementsByClass("txt-block")
                                         .stream()
                                         .filter((element -> element.text().contains("Release Date")))
                                         .collect(Collectors.toList()).get(0);
        divReleaseDate.getElementsByClass("inline").first().remove();
        divReleaseDate.getElementsByClass("see-more inline").first().remove();
        String date = divReleaseDate.text().substring(0, divReleaseDate.text().lastIndexOf(" "));
        DateFormat dateFormat = new SimpleDateFormat("d MMMM yyyy",Locale.US);

        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Actor> getCast() {
        ConcurrentMap <String, Actor> cast = new ConcurrentHashMap<>();
        String separator = System.getProperty("file.separator");
        String dir = System.getProperty("user.home")
                                .concat(separator).concat("Series")
                                .concat(separator).concat(title.getValue())
                                .concat(separator).concat("Cast");
        Element tblCastList = imdbPage.getElementsByClass("cast_list").first();
        Elements tblCastRows = tblCastList.select("[href*=/name/]")
                                          .select(":has([title])");
        Elements nEpisodesPart = tblCastList.select("a.toggle-episodes");
        tblCastRows.parallelStream().forEach(row->{
            String name = row.getElementsByTag("img").attr("title");
            String urlIMDBPage = "https://www.imdb.com".concat(row.attr("href"));
            String nEpisodes = nEpisodesPart.get(tblCastRows.indexOf(row)).attr("data-n");
            CustomImage image;
            try {
                Element imdbImage = Jsoup.connect(urlIMDBPage).get()
                                         .getElementById("name-poster");
                if(imdbImage != null){
                    String imageURL = imdbImage.attr("src");
                    image = downloadImage(name,imageURL,dir);
                    cast.put(name,new Actor(name,image,urlIMDBPage,Integer.parseInt(nEpisodes)));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        List<Actor> castSortedList = new ArrayList<>(cast.values());
        castSortedList.sort((a1,a2)->{
            if(a1.getnEpisodes() > a2.getnEpisodes()) return -1;
            else if(a1.getnEpisodes() < a2.getnEpisodes()) return 1;
            else return 0;
        });

        return castSortedList;
    }

}
