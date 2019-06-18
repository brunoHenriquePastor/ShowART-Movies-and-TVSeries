package br.ufop.tomaz.controller.elements;

import br.ufop.tomaz.interfaces.MediaInterface;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.controlsfx.control.GridCell;

import java.io.IOException;

public class Card<T extends MediaInterface> extends GridCell<T>{

    @FXML private VBox content;
    @FXML private ImageView imgPoster;
    @FXML private Label lblTitle;

    public Card(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource(getFxmlUrl()));
        loader.setRoot(this);
        loader.setController(this);
        try{
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if(item == null || empty){
            setGraphic(null);
        }else{
            imgPoster.setImage(new Image(item.getImagePosterUrl()));
            lblTitle.setText(item.getTitle());
            setGraphic(content);
        }
    }

    private String getFxmlUrl(){
        return "/br/ufop/tomaz/fxml/elements/Card.fxml";
    }
}
