package br.ufop.tomaz.model;

import junit.framework.TestCase;

import java.io.File;

public class TestUser extends TestCase {

    public void testMakeUserResourcesDirectories(){
        User user = new User("Test User","testuser",null,"testuser@email.com",false);
        File dir = new File(user.getResourcesPath());
        dir.mkdirs();
        dir.deleteOnExit();
        assertTrue(dir.exists());
        assertNotNull(dir);
        assertTrue(dir.canWrite());
        assertTrue(dir.canRead());
    }
}
