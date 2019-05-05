package br.ufop.tomaz.model;

import br.ufop.tomaz.util.IMDBUtilities;
import javafx.beans.property.*;
import javafx.scene.image.Image;

import java.util.*;


public class Series implements SerieAndMovieInterface{

    private StringProperty title;
    private StringProperty description;
    private HashMap<Integer,List<Episode>> seasons;
    private List<Category> categories;
    private IntegerProperty rating;
    private ObjectProperty<Image> imgCover;
    private List<Actor> cast;
    private Calendar releaseDate = Calendar.getInstance();

    public Series(String title, String description, Image imgCover, HashMap<Integer, List<Episode>> seasons, List<Category>categories, Integer rating, List<Actor> cast) {
        this.title = new SimpleStringProperty(title);
        this.description = new SimpleStringProperty(description);
        this.seasons = seasons;
        this.categories = categories;
        this.rating = new SimpleIntegerProperty(rating);
        this.imgCover = new SimpleObjectProperty<>(imgCover);
        this.cast = cast;
    }

    public Series(String title, String description, List<Category> categories, Integer rating, Image imgCover, List<Actor> cast, Date releaseDate) {
        this.title = new SimpleStringProperty(title);
        this.description = new SimpleStringProperty(description);
        this.categories = categories;
        this.rating = new SimpleIntegerProperty(rating);
        this.imgCover = new SimpleObjectProperty<>(imgCover);
        this.cast = cast;
        this.releaseDate.setTime(releaseDate);
        this.seasons = new HashMap<>();
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

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public HashMap<Integer, List<Episode>> getSeasons() {
        return seasons;
    }

    public void setSeasons(HashMap<Integer, List<Episode>> seasons) {
        this.seasons = seasons;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public int getRating() {
        return rating.get();
    }

    public IntegerProperty ratingProperty() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating.set(rating);
    }

    public Image getImgCover() {
        return imgCover.get();
    }

    public ObjectProperty<Image> imgCoverProperty() {
        return imgCover;
    }

    public void setImgCover(Image imgCover) {
        this.imgCover.set(imgCover);
    }

    public List<Actor> getCast() {
        return cast;
    }

    public void setCast(List<Actor> cast) {
        this.cast = cast;
    }

    public Calendar getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Calendar releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public void loadInformationFromInternet() {
        //TODO -- Implements method to download information from internet
        IMDBUtilities imdbUtilities = new IMDBUtilities(title.getValue());
        setDescription(imdbUtilities.getDescription());
        setImgCover(imdbUtilities.getImageCover());
        setRating(imdbUtilities.getRate());
        setCategories(imdbUtilities.getCategories());
    }
}
