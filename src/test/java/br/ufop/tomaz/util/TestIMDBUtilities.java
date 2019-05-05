package br.ufop.tomaz.util;

import br.ufop.tomaz.model.Actor;
import br.ufop.tomaz.model.SerieAndMovieInterface.Category;
import br.ufop.tomaz.model.User;
import br.ufop.tomaz.model.UserSession;
import javafx.scene.image.Image;
import junit.framework.TestCase;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class TestIMDBUtilities extends TestCase {

    public void testGetDescription(){
        String description = new IMDBUtilities("Gotham").getDescription();
        String expectedDescription = "The story behind Detective James Gordon's rise to prominence in " +
                                     "Gotham City in the years before Batman's arrival.";
        assertNotNull(description);
        assertFalse(description.isEmpty());
        assertEquals(description,expectedDescription);
    }

    public void testGetCategories(){
        List<Category> categories = new IMDBUtilities("Gotham").getCategories();
        assertNotNull(categories);
        assertFalse(categories.isEmpty());
        assertTrue(categories.containsAll(Arrays.asList(Category.ACTION,Category.DRAMA,Category.CRIME)));
        assertFalse(categories.contains(Category.ANIMATION));
    }

    public void testGetCover(){
        User user = new User("Test User","testuser",null,"testuser@email.com",false);
        UserSession.getInstance().setUser(user);
        File dir = new File(user.getResourcesPath());
        dir.mkdirs();
        Image cover = new IMDBUtilities("The Big Bang Theory").getImageCover();
        assertNotNull(cover);

        //Delete the test directory
        dir.deleteOnExit();
    }

    public void testGetOriginalTitle(){
        IMDBUtilities imdbUtilities = new IMDBUtilities("Big Bang: A Teoria");
        String title = imdbUtilities.getTitle();
        String expectedTitle = "The Big Bang Theory";
        assertEquals(expectedTitle,title);
    }

    public void testGetRate(){
        Integer rate = new IMDBUtilities("Gotham").getRate();
        assertNotNull(rate);
        assertFalse(rate <= 0);
        assertFalse(rate > 10);
    }

    public void testGetReleaseDate(){
        String title = "Amarog";
        Date date = new IMDBUtilities(title).getReleaseDate();

        assertNotNull(date);
    }

    public void testGetEpisodesDescription(){
        int season = 4;
        int expectedNumberOfEpisodes = 22;
        int testEpisode = 3;
        String expectedDescriptionForTestEpisode = "Gordon travels to Miami hoping to convince Carmine Falcone " +
                                                   "to help him fight the Penguin, only to have his daughter follow " +
                                                   "him back to Gotham.";

        Map<Integer,String> mapEpisodesDescription = new IMDBUtilities("Gotham").getEpisodesDescription(season);
        assertNotNull(mapEpisodesDescription);
        assertFalse(mapEpisodesDescription.isEmpty());
        assertEquals(expectedNumberOfEpisodes,mapEpisodesDescription.size());
        assertEquals(expectedDescriptionForTestEpisode,mapEpisodesDescription.get(testEpisode-1));
    }

    public void testGetEpisodesTitles(){
        int season = 3;
        int expectedNumberOfEpisodes = 23;
        int testEpisode = 5;
        String titleForTextEpisode;
        String expectedTitleForTestEpisode = "The Library";

        Map<Integer,String> mapEpisodesTitles = new IMDBUtilities("Seinfeld").getEpisodesTitles(season);
        titleForTextEpisode = mapEpisodesTitles.get(testEpisode-1);

        assertNotNull(mapEpisodesTitles);
        assertFalse(mapEpisodesTitles.isEmpty());
        assertEquals(expectedNumberOfEpisodes,mapEpisodesTitles.size());
        assertEquals(expectedTitleForTestEpisode,titleForTextEpisode);
    }

    public void testGetEpisodeImages(){
        int season = 1;
        int expectedNumberOfEpisodes = 23;
        User user = new User("Test User","testuser",null,"testuser@email.com",false);
        UserSession.getInstance().setUser(user);
        File dir = new File(user.getResourcesPath());
        dir.mkdirs();
        Map<Integer,Image> mapEpisodesImages = new IMDBUtilities("Flash").getEpisodesImages(season);

        assertNotNull(mapEpisodesImages);
        assertEquals(expectedNumberOfEpisodes,mapEpisodesImages.size());
        mapEpisodesImages.forEach((index,image)-> assertNotNull(image));

        //Delete the test directory
        dir.deleteOnExit();
    }

    public void testGetEpisodeReleaseDates(){
        int season = 1;
        Map<Integer, Date> episodesReleaseDatesMap = new IMDBUtilities("Dexter").getEpisodesReleaseDates(season);
        int expectedNumberOfEpisodes = 12;

        assertNotNull(episodesReleaseDatesMap);
        assertFalse(episodesReleaseDatesMap.isEmpty());
        assertEquals(expectedNumberOfEpisodes,episodesReleaseDatesMap.size());
        episodesReleaseDatesMap.forEach((k,v)-> assertNotNull(v));
    }

    public void testGetEpisodeRates(){
        int season = 2;
        String title = "Dexter";
        Map<Integer,Integer> episodesRatesMap = new IMDBUtilities(title).getEpisodesRates(season);
        int expectedNumberOfEpisodes = 12;

        assertNotNull(episodesRatesMap);
        assertFalse(episodesRatesMap.isEmpty());
        assertEquals(expectedNumberOfEpisodes,episodesRatesMap.size());
        episodesRatesMap.forEach((k,v)->assertTrue(v >=0 && v <= 10));
    }

    public void testGetCast(){
        User user = new User("Test User","testuser",null,"testuser@email.com",false);
        UserSession.getInstance().setUser(user);
        String title = "Grey's Anatomy";
        List<Actor> cast = new IMDBUtilities(title).getCast();

        assertNotNull(cast);
        assertFalse(cast.isEmpty());
        cast
            .forEach((actor)->{
                assertNotNull(actor.getImage());
                assertFalse(actor.getName().isEmpty());
                assertFalse(actor.getWebPageLink().isEmpty());
                System.out.println(actor.getName());
            });
    }
}
