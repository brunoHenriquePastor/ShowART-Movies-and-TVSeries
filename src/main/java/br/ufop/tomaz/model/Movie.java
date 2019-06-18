package br.ufop.tomaz.model;

import br.ufop.tomaz.interfaces.ExecutableMediaInterface;
import br.ufop.tomaz.interfaces.MediaInterface;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.Duration;
import java.util.Date;
import java.util.List;

public class Movie implements MediaInterface, ExecutableMediaInterface {

    private StringProperty title = new SimpleStringProperty();
    private StringProperty description = new SimpleStringProperty();
    private StringProperty imagePosterUrl = new SimpleStringProperty();
    private StringProperty imagePath = new SimpleStringProperty();
    private List<Actor> cast;
    private StringProperty fileUrl = new SimpleStringProperty();
    private Duration duration;

    @Override
    public void setImagePosterUrl(String imagePosterUrl) {
        this.imagePosterUrl.set(imagePosterUrl);
    }

    @Override
    public void setTitle(String title) {
        this.title.set(title);
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public Date getReleaseDate() {
        return null;
    }

    @Override
    public List<Actor> getCast() {
        return null;
    }

    @Override
    public String getImagePosterUrl() {
        return imagePosterUrl.get();
    }

    @Override
    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public StringProperty imagePosterUrlProperty() {
        return imagePosterUrl;
    }

    public StringProperty imagePathProperty() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath.set(imagePath);
    }

    public void setCast(List<Actor> cast) {
        this.cast = cast;
    }

    public String getFileUrl() {
        return fileUrl.get();
    }

    public StringProperty fileUrlProperty() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl.set(fileUrl);
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    @Override
    public double getRating() {
        return 0;
    }

    @Override
    public double getWatchedRate() {
        return -1;
    }

    @Override
    public String getSeriesName() {
        return title.get();
    }

    @Override
    public String getImagePath() {
        return imagePath.get();
    }

    @Override
    public String getEpisodeTitle() {
        return title.get();
    }

    @Override
    public boolean isWatched() {
        return false;
    }
}
