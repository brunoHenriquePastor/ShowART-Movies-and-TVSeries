package br.ufop.tomaz.controller.elements;

import br.ufop.tomaz.interfaces.ExecutableMediaInterface;
import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXProgressBar;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;


public class VideoCard<T extends ExecutableMediaInterface> extends JFXListCell<T> {

    @FXML private AnchorPane content;
    @FXML private ImageView episodeImageView;
    @FXML private Label lblSerieTitle;
    @FXML private Label lblEpisodeTitle;
    @FXML private JFXProgressBar watchProgress;

    public VideoCard() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(getPath()));
        loader.setController(this);
        loader.setRoot(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null || empty) {
            setGraphic(null);
        } else {
            episodeImageView.setImage(new Image(item.getImagePath()));
            lblEpisodeTitle.setText(item.getEpisodeTitle());
            lblSerieTitle.setText(item.getSeriesName());
            watchProgress.setProgress(item.getWatchedRate());
            setGraphic(content);
        }
    }

    private String getPath() {
        return "/br/ufop/tomaz/fxml/elements/VideoCard.fxml";
    }
}
