package br.ufop.tomaz.interfaces;

import br.ufop.tomaz.model.Actor;

import java.util.Date;
import java.util.List;

public interface MediaInterface {
    String getDescription();
    Date getReleaseDate();
    List<Actor> getCast();
    String getImagePosterUrl();
    String getTitle();
    double getRating();
    void setImagePosterUrl(String imagePosterUrl);
    void setTitle(String title);
}
