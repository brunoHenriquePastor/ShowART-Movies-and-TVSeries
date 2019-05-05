package br.ufop.tomaz.controller;

import br.ufop.tomaz.dao.FactoryDAOCreator;
import br.ufop.tomaz.dao.SeriesDAO;
import br.ufop.tomaz.model.*;
import br.ufop.tomaz.util.ControlledScreen;
import br.ufop.tomaz.util.ScreensController;
import br.ufop.tomaz.util.SeriesGridCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.util.Callback;
import org.controlsfx.control.GridCell;
import org.controlsfx.control.GridView;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class FXMLSeriesLibraryController implements Initializable, ControlledScreen {

    private ScreensController parentController;
    @FXML
    private GridView<Series> gridView;
    private ObservableList<Series> seriesLibrary;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gridView.setCellFactory(new Callback<GridView<Series>, GridCell<Series>>() {
            @Override
            public GridCell<Series> call(GridView<Series> param) {
                return new SeriesGridCell();
            }
        });
    }

    @Override
    public void loadContent() {
        /* TODO - Create another thread to load the files.
         Implement the load method, where will load all series */
        Task loadSeriesFromDB = new Task<List<Series>>() {

            @Override
            protected List<Series> call() throws Exception {
                User user = UserSession.getInstance().getUser();
                SeriesDAO seriesDAO = Objects.requireNonNull(FactoryDAOCreator.createFactoryDAO()).getSeriesDAO();
                return seriesDAO.retrieveSeries(user);
            }

            @Override
            protected void succeeded() {
                seriesLibrary = FXCollections.observableArrayList(getValue());
                gridView.setItems(seriesLibrary);
            }

            @Override
            protected void failed() {
                seriesLibrary = FXCollections.observableArrayList();
            }
        };

        new Thread(loadSeriesFromDB).start();
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
}
