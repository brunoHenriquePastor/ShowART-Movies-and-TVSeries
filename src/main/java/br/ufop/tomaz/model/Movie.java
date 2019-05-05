package br.ufop.tomaz.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Movie implements SerieAndMovieInterface{

    private StringProperty category;
    private Episode episode;
    private IntegerProperty id;

    public Movie(String category, Episode episode, Integer id) {
        this.category = new SimpleStringProperty(category);
        this.episode = episode;
        this.id = new SimpleIntegerProperty(id);
    }

    public String getCategory() {
        return category.get();
    }

    public StringProperty categoryProperty() {
        return category;
    }

    public void setCategory(String category) {
        this.category.set(category);
    }

    public Episode getEpisode() {
        return episode;
    }

    public void setEpisode(Episode episode) {
        this.episode = episode;
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    @Override
    public void loadInformationFromInternet() {
        //TODO
        //Implements the load information from internet with some framework
    }
}
