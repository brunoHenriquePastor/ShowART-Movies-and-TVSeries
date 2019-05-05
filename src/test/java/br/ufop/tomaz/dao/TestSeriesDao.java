package br.ufop.tomaz.dao;

import junit.framework.TestCase;

import br.ufop.tomaz.model.Series;
import br.ufop.tomaz.model.User;
import br.ufop.tomaz.model.UserSession;
import br.ufop.tomaz.util.CustomImage;
import br.ufop.tomaz.util.IMDBUtilities;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.*;


public class TestSeriesDao extends TestCase {

    public void testGetSeriesFromDB(){
        User testUser = new User("Test User","testuser",null,"testuser@email.com",false, Calendar.getInstance().getTime());
        UserSession.getInstance().setUser(testUser);
        List<Series> series = FactoryDAOCreator.createFactoryDAO().getSeriesDAO().retrieveSeries(testUser);

        assertNotNull(series);
        series.forEach(TestCase::assertNotNull);
    }

    public void testAddSeriesToDB() throws IOException {
        FactoryDAO factoryDAO = FactoryDAOCreator.createFactoryDAO();
        String imgURL = "C://Users//Felipe Tomaz//ShowART---Movies-and-Series//Stefany//Series//Gotham//Cast//Camren Bicondova.jpg";
        CustomImage image = new CustomImage(Files.newInputStream(new File(imgURL).toPath(), StandardOpenOption.READ),imgURL);
        User user = new User("Test User","0000",image, "testuser@showart.com",false);
        UserSession.getInstance().setUser(user);
        IMDBUtilities imdbUtilities = new IMDBUtilities("Dexter");
        Series series = new Series(imdbUtilities.getTitle(),imdbUtilities.getDescription(),imdbUtilities.getCategories(),
                                    imdbUtilities.getRate(),imdbUtilities.getImageCover(),imdbUtilities.getCast(),imdbUtilities.getReleaseDate());
        UserDAO userDAO = factoryDAO.getUserDAO();
        SeriesDAO seriesDAO = factoryDAO.getSeriesDAO();
        userDAO.addUserOnDB(user);

        boolean insert = seriesDAO.addSeriesToDB(series,user);
        seriesDAO.closeConnection();
        assertTrue(insert);

    }

    public void testDeleteSeriesFromDB() throws IOException {
        String imgURL = "C://Users//Felipe Tomaz//ShowART---Movies-and-Series//Stefany//Series//Gotham//Cast//Camren Bicondova.jpg";
        CustomImage image = new CustomImage(Files.newInputStream(new File(imgURL).toPath(), StandardOpenOption.READ),imgURL);
        User user = new User("Test User","0000",image, "testuser@showart.com",false);
        UserSession.getInstance().setUser(user);
        IMDBUtilities imdbUtilities = new IMDBUtilities("Dexter");
        Series series = new Series(imdbUtilities.getTitle(),imdbUtilities.getDescription(),imdbUtilities.getCategories(),
                imdbUtilities.getRate(),imdbUtilities.getImageCover(),imdbUtilities.getCast(),imdbUtilities.getReleaseDate());
        SeriesDAO seriesDAO = FactoryDAOCreator.createFactoryDAO().getSeriesDAO();

        boolean deleted = seriesDAO.deleteSeriesFromDB(series,user);
        assertTrue(deleted);
    }
}
