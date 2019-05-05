package br.ufop.tomaz.dao;

import junit.framework.TestCase;
import br.ufop.tomaz.model.User;
import br.ufop.tomaz.util.CustomImage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;

public class TestUserDao extends TestCase {

    public void testAddUserOnDB() throws IOException {
        File imgFile = new File("C://Users/Felipe Tomaz/Desktop/TestUser.jpg");
        User testUser = new User("Test User","0000",new CustomImage(Files.newInputStream(imgFile.toPath(), StandardOpenOption.READ),imgFile.getPath()),
                                "testuser@showart.com",false, Date.from(Instant.now()));
        UserDAO userDAO = FactoryDAOCreator.createFactoryDAO().getUserDAO();
        final boolean insert = userDAO.addUserOnDB(testUser);
        userDAO.closeConnection();
        assertTrue(insert);
    }

    public void testRetrieveUser (){
        UserDAO userDAO = FactoryDAOCreator.createFactoryDAO().getUserDAO();
        String username = "Test User";
        String expectedEmail = "testuser@showart.com";
        String expectedPassword = "0000";
        User testUser = userDAO.retrieveUser(username);
        userDAO.closeConnection();

        assertNotNull(testUser);
        assertFalse(testUser.getIsRememberMyPassword());
        assertEquals(expectedEmail,testUser.getEmail());
        assertEquals(expectedPassword,testUser.getPassword());
    }

    public void testUpdateUser(){
        String username = "Test User";
        UserDAO userDAO = FactoryDAOCreator.createFactoryDAO().getUserDAO();
        User testBefore = userDAO.retrieveUser(username);
        String newEmail = "testuser@gmail.com";
        String newPassword = "12345";
        testBefore.setEmail(newEmail);
        testBefore.setPassword(newPassword);
        boolean updated = userDAO.updateUserOnDB(testBefore);
        User testAfter = userDAO.retrieveUser(username);
        userDAO.closeConnection();

        assertTrue(updated);
        assertEquals(newEmail,testAfter.getEmail());
        assertEquals(newPassword,testAfter.getPassword());

    }

    public void testDeleteUserOnDB(){
        String username = "Test User";
        UserDAO userDAO = FactoryDAOCreator.createFactoryDAO().getUserDAO();
        boolean deleted = userDAO.deleteUserOnDB(username);
        userDAO.closeConnection();

        assertTrue(deleted);
    }

    public void testRetrieveKeepConnected() throws IOException {
        UserDAO userDAO = FactoryDAOCreator.createFactoryDAO().getUserDAO();
        int expectedNumberOfElements = 2;
        File imgFile = new File("C://Users/Felipe Tomaz/Desktop/TestUser.jpg");
        User testUser1 = new User("User 1","0000",new CustomImage(Files.newInputStream(imgFile.toPath(), StandardOpenOption.READ),imgFile.getPath()),
                "testuser1@showart.com",true, Date.from(Instant.now()));
        User testUser2 = new User("User 2","0000",new CustomImage(Files.newInputStream(imgFile.toPath(), StandardOpenOption.READ),imgFile.getPath()),
                "testuser2@showart.com",true, Date.from(Instant.now()));
        userDAO.addUserOnDB(testUser1);
        userDAO.addUserOnDB(testUser2);
        LinkedList<User> keepConnected = userDAO.retrieveKeepConnected();

        assertNotNull(keepConnected);
        assertEquals(expectedNumberOfElements,keepConnected.size());

        userDAO.deleteUserOnDB(testUser1.getUsername());
        userDAO.deleteUserOnDB(testUser2.getUsername());
        userDAO.closeConnection();
    }

    public void testLastLogged() throws IOException, ParseException {
        UserDAO userDAO = FactoryDAOCreator.createFactoryDAO().getUserDAO();
        int expectedNumberOfElements = 2;
        File imgFile = new File("C://Users/Felipe Tomaz/Desktop/TestUser.jpg");
        User testUser1 = new User("User 1","0000",new CustomImage(Files.newInputStream(imgFile.toPath(), StandardOpenOption.READ),imgFile.getPath()),
                "testuser1@showart.com",true, Date.from(Instant.now()));
        User testUser2 = new User("User 2","0000",new CustomImage(Files.newInputStream(imgFile.toPath(), StandardOpenOption.READ),imgFile.getPath()),
                "testuser2@showart.com",true, Date.from(Instant.now()));
        userDAO.addUserOnDB(testUser1);
        userDAO.addUserOnDB(testUser2);
        Calendar newLogin = Calendar.getInstance(Locale.getDefault());
        String newDateLogin = "21-10-2025 12:35";
        newLogin.setTime(new SimpleDateFormat("dd-MM-YYYY HH:mm").parse(newDateLogin));
        testUser1.setLastLogin(newLogin.getTime());
        userDAO.updateUserOnDB(testUser1);
        User lastLogged = userDAO.lastLogged();
        userDAO.deleteUserOnDB(testUser1.getUsername());
        userDAO.deleteUserOnDB(testUser2.getUsername());
        userDAO.closeConnection();

        assertNotNull(lastLogged);
        assertEquals(testUser1.getEmail(),lastLogged.getEmail());
        assertEquals(testUser1.getUsername(),lastLogged.getUsername());
        assertEquals(testUser1.getIsRememberMyPassword(),lastLogged.getIsRememberMyPassword());
        assertEquals(testUser1.getPassword(),lastLogged.getPassword());
        assertEquals(testUser1.getLastLogin().toString(),lastLogged.getLastLogin().toString());
        assertEquals(testUser1.getImgProfile().getUrl(),lastLogged.getImgProfile().getUrl());

    }

}
