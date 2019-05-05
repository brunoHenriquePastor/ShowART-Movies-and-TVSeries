package br.ufop.tomaz.model;

import javafx.beans.property.*;
import javafx.scene.image.Image;
import javafx.scene.media.Media;

import java.util.Date;

public class Episode {

    private StringProperty title;
    private StringProperty description;
    private Image image;
    private Date releaseDate;
    private BooleanProperty isPlayed;
    private DoubleProperty timePlayed;
    private Media media;

    public Episode(String title, String description, Image image, Date releaseDate, javafx.scene.media.Media media) {
        this.title = new SimpleStringProperty(title);
        this.description = new SimpleStringProperty(description);
        this.releaseDate = releaseDate;
        this.media = media;
        this.image = image;
        this.isPlayed = new SimpleBooleanProperty(false);
        this.timePlayed = new SimpleDoubleProperty(0.0);
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

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public boolean isIsPlayed() {
        return isPlayed.get();
    }

    public BooleanProperty isPlayedProperty() {
        return isPlayed;
    }

    public void setIsPlayed(boolean isPlayed) {
        this.isPlayed.set(isPlayed);
    }

    public double getTimePlayed() {
        return timePlayed.get();
    }

    public DoubleProperty timePlayedProperty() {
        return timePlayed;
    }

    public void setTimePlayed(double timePlayed) {
        this.timePlayed.set(timePlayed);
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(javafx.scene.media.Media media) {
        this.media = media;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
