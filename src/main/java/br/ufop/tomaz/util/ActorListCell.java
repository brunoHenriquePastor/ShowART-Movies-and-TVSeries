package br.ufop.tomaz.util;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import br.ufop.tomaz.model.Actor;
import net.coobird.thumbnailator.Thumbnailator;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ActorListCell extends ListCell<Actor> {

    private final VBox vBox = new VBox();
    private final ImageView imvPhoto = new ImageView();
    private final Label lblName = new Label("");

    public ActorListCell(){
        initComponents();
    }

    private void initComponents(){
        // init imvPhoto
        imvPhoto.setFitHeight(104.0);
        imvPhoto.setFitWidth(70.0);
        double centerX = (imvPhoto.getFitWidth()/2) + imvPhoto.getX();
        double centerY = (imvPhoto.getFitHeight()/2) + imvPhoto.getY();
        imvPhoto.setClip(new Circle(centerX, centerY,35.0));

        // put the photo and the name in vBox
        vBox.getChildren().addAll(imvPhoto,lblName);
        vBox.setAlignment(Pos.CENTER);
    }

    private void loadActor(Actor actor){
        String imgUrl = actor.getImage().getUrl();
        try {
            BufferedImage thumbnail = Thumbnailator.createThumbnail(new File(imgUrl),70,104);
            Image thumbnailFX = SwingFXUtils.toFXImage(thumbnail,null);
            this.imvPhoto.setImage(thumbnailFX);
            this.lblName.setText(actor.getName());
            setGraphic(vBox);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void updateItem(Actor item, boolean empty) {
        super.updateItem(item, empty);
        if(item == null || empty){
            setGraphic(null);
        }else{
            loadActor(item);
        }
    }
}
