package br.ufop.tomaz.controller;

import br.ufop.tomaz.model.Series;
import br.ufop.tomaz.util.ScreensController;
import br.ufop.tomaz.util.SeriesGridCell;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.util.Callback;
import org.controlsfx.control.GridCell;
import org.controlsfx.control.GridView;

import java.net.URL;
import java.util.ResourceBundle;

public class FXMLSeriesLibraryController implements Initializable {

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

}
