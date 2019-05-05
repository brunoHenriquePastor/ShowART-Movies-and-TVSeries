package br.ufop.tomaz.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UserSession {
    private static UserSession ourInstance = new UserSession();

    //Keep the user that owns the session
    private User user;
    private StringProperty userPathResources = new SimpleStringProperty("");

    public static UserSession getInstance() {
        return ourInstance;
    }

    private UserSession() {
    }

    public static UserSession getOurInstance() {
        return ourInstance;
    }

    public static void setOurInstance(UserSession ourInstance) {
        UserSession.ourInstance = ourInstance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUserPathResources() {
        return userPathResources.get();
    }

    public StringProperty userPathResourcesProperty() {
        return userPathResources;
    }

    public void setUserPathResources(String userPathResources) {
        this.userPathResources.set(userPathResources);
    }
}
