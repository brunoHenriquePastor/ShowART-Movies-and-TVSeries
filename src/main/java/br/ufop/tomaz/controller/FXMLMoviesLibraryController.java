package br.ufop.tomaz.controller;

import javafx.fxml.Initializable;
import br.ufop.tomaz.model.Episode;
import br.ufop.tomaz.model.Movie;
import br.ufop.tomaz.model.Series;
import br.ufop.tomaz.util.ControlledScreen;
import br.ufop.tomaz.util.ScreensController;

import java.net.URL;
import java.util.ResourceBundle;

public class FXMLMoviesLibraryController implements Initializable, ControlledScreen {

    private ScreensController parentController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public void setContentParent(ScreensController myController) {
        this.parentController = myController;
    }

    @Override
    public void loadContent(Episode episode) {

    }

    @Override
    public void loadContent(Series series) {

    }

    @Override
    public void loadContent(Series series, Integer season) {

    }

    @Override
    public void loadContent(Movie movie) {

    }

    @Override
    public void loadContent() {
          /* TODO
         Implement the load method, where will load all movies */

    }
}
