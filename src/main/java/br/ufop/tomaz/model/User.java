package br.ufop.tomaz.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;

@Entity
@Access(AccessType.PROPERTY)
@NamedQuery(name = "User.findAllRemembered", query = "SELECT U FROM User U WHERE U.rememberMyPassword = TRUE")
public class User {

    private final StringProperty username = new SimpleStringProperty("");
    private final StringProperty password = new SimpleStringProperty("");
    private final StringProperty imgProfilePath = new SimpleStringProperty("");
    private final StringProperty email = new SimpleStringProperty("");
    private final BooleanProperty rememberMyPassword = new SimpleBooleanProperty(false);
    private Date lastLogin;

    public User(){
        setLastLogin(Date.from(Instant.now()));
    }

    public User(String username,
                String password,
                String imgProfilePath,
                String email,
                boolean rememberMyPassword,
                Date lastLogin
    ) {
        setUsername(username);
        setPassword(password);
        setImgProfilePath(imgProfilePath);
        setEmail(email);
        setRememberMyPassword(rememberMyPassword);
        setLastLogin(lastLogin);
    }

    @Id
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

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public boolean isRememberMyPassword() {
        return rememberMyPassword.get();
    }

    public BooleanProperty rememberMyPasswordProperty() {
        return rememberMyPassword;
    }

    public void setRememberMyPassword(boolean rememberMyPassword) {
        this.rememberMyPassword.set(rememberMyPassword);
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getImgProfilePath() {
        return imgProfilePath.get();
    }

    public StringProperty imgProfilePathProperty() {
        return imgProfilePath;
    }

    public void setImgProfilePath(String imgProfilePath) {
        this.imgProfilePath.set(imgProfilePath);
    }
}
