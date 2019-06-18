package br.ufop.tomaz.controller;

import br.ufop.tomaz.controller.elements.Card;
import br.ufop.tomaz.controller.elements.VideoCard;
import br.ufop.tomaz.interfaces.ExecutableMediaInterface;
import br.ufop.tomaz.interfaces.MediaInterface;
import br.ufop.tomaz.model.Movie;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import org.controlsfx.control.GridView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FXMLSeriesLibraryController implements Initializable {

    @FXML private JFXListView<ExecutableMediaInterface> youWereWatchingListView;
    @FXML private GridView<MediaInterface> libraryGridView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initYouWereWatchingListView();
        initLibraryGridView();


    }

    private void initYouWereWatchingListView(){
        youWereWatchingListView.getItems().addAll(getRecentlyPlayed());
        youWereWatchingListView.setCellFactory(param -> new VideoCard<>());
    }

    private void initLibraryGridView(){
        libraryGridView.getItems().addAll(FXCollections.observableList(getRecentlyPlayed()));
        libraryGridView.setCellFactory(param -> new Card<>());
    }

    private List<Movie> getRecentlyPlayed(){
        List<Movie> recentlyPlayed = new ArrayList<>();
        for(int i = 0; i < 30; i++){
            Movie media = new Movie();
            media.setTitle("Dexter");
            Image wideImage = new Image("/br/ufop/tomaz/images/yQKuW0jqdVy5ENGlvcKsB82IWwY.jpg");
            Image posterImage = new Image("/br/ufop/tomaz/images/flash.jpg");
            media.setImagePath(wideImage.getUrl());
            media.setImagePosterUrl(posterImage.getUrl());
            recentlyPlayed.add(media);
        }
        return recentlyPlayed;
    }
}
