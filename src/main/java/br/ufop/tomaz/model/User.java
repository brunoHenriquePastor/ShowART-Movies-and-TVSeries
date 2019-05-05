package br.ufop.tomaz.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import br.ufop.tomaz.util.CustomImage;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Calendar;
import java.util.Date;

@Entity
public class User {

    @Id
    private final StringProperty username;
    private final StringProperty password;
    private CustomImage imgProfile;
    private final StringProperty email;
    private final BooleanProperty isRememberMyPassword;
    private Date lastLogin;
    private final StringProperty resourcesPath = new SimpleStringProperty("");

    public User(String username,
                String password,
                CustomImage imgProfile,
                String email,
                boolean isRememberMyPassword,
                Date lastLogin
    ) {
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.imgProfile = imgProfile;
        this.email = new SimpleStringProperty(email);
        this.isRememberMyPassword = new SimpleBooleanProperty(isRememberMyPassword);
        this.lastLogin = lastLogin;
        this.makeUserResourcesDirectories();
    }

    public User(String username, String password, CustomImage imgProfile, String email, boolean isRememberMyPassword) {
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.imgProfile = imgProfile;
        this.email = new SimpleStringProperty(email);
        this.isRememberMyPassword = new SimpleBooleanProperty(isRememberMyPassword);
        this.lastLogin = Calendar.getInstance().getTime();
        this.makeUserResourcesDirectories();
    }

    public String getUsername() {
        return username.get();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public CustomImage getImgProfile() {
        return imgProfile;
    }

    public void setImgProfile(CustomImage imgProfile) {
        this.imgProfile = imgProfile;
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public boolean getIsRememberMyPassword() {
        return isRememberMyPassword.get();
    }

    public BooleanProperty isRememberMyPasswordProperty() {
        return isRememberMyPassword;
    }

    public void setIsRememberMyPassword(boolean isRememberMyPassword) {
        this.isRememberMyPassword.set(isRememberMyPassword);
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getResourcesPath() {
        return resourcesPath.get();
    }

    public StringProperty resourcesPathProperty() {
        return resourcesPath;
    }

    public void setResourcesPath(String resourcesPath) {
        this.resourcesPath.set(resourcesPath);
    }

    private void makeUserResourcesDirectories() {
        String userResourcePath = System.getProperty("user.home")
                .concat(System.getProperty("file.separator"))
                .concat("ShowART---Movies-and-Series")
                .concat(System.getProperty("file.separator"))
                .concat(username.getValue());
        setResourcesPath(userResourcePath);
    }
}
