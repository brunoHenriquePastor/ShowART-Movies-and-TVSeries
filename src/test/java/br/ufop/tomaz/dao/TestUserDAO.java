package br.ufop.tomaz.dao;

import br.ufop.tomaz.model.User;
import junit.framework.TestCase;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import java.util.Calendar;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestUserDAO extends TestCase {

    private final User testUser = getTestUser();


    public void testAAddUser(){
        UserDAO userDAO = UserDAOImpl.getInstance();
        boolean added = userDAO.addUser(testUser);

        assertTrue(added);
    }

    public void testBRetrieveUser(){
        UserDAO userDAO = UserDAOImpl.getInstance();
        User retrievedUser = userDAO.retrieveUser(testUser.getUsername());

        assertNotNull(retrievedUser);
        assertEquals(testUser.getPassword(), retrievedUser.getPassword());
    }

    public void testCUpdateUser(){
        UserDAO userDAO = UserDAOImpl.getInstance();
        testUser.setEmail("testuser@gmail.com");
        boolean updated = userDAO.updateUser(testUser);

        assertTrue(updated);

    }


    public void testDRemoveUser(){
        UserDAO userDAO = UserDAOImpl.getInstance();
        boolean removed = userDAO.removeUser(testUser);

        assertTrue(removed);
    }

    private User getTestUser(){
        User testUser = new User();
        testUser.setUsername("TestUser");
        testUser.setEmail("testuser@email.com");
        testUser.setPassword("testUserPassword");
        testUser.setImgProfilePath(User.class.getResource("/br/ufop/tomaz/icons/defaultProfileImage.png").getPath());
        testUser.setLastLogin(Calendar.getInstance().getTime());
        testUser.setRememberMyPassword(true);
        return testUser;
    }


}
