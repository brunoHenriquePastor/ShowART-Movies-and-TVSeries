package br.ufop.tomaz.util;

import br.ufop.tomaz.model.Episode;
import br.ufop.tomaz.model.Movie;
import br.ufop.tomaz.model.Series;

public interface ControlledScreen {

    //This method sets the parent's controller that each screen that is able to be loaded
    //This make possible a child sets the contents of a parent
    void setContentParent(ScreensController myController);
    void loadContent(Episode episode);
    void loadContent(Series series);
    void loadContent(Series series,Integer season);
    void loadContent(Movie movie);
    void loadContent();
}
