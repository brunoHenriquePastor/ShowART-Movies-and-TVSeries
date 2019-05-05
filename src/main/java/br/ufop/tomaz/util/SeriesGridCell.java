package br.ufop.tomaz.util;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import br.ufop.tomaz.model.Series;
import org.controlsfx.control.GridCell;

public class SeriesGridCell extends GridCell<Series> {

    private VBox cell;
    private ImageView imgCover;
    private Label lblTitle;

    public SeriesGridCell() {
        initComponents();
    }

    public void initComponents(){
        this.cell = new VBox();
        this.imgCover = new ImageView();
        this.lblTitle = new Label("Title");
        imgCover.setFitWidth(130);
        imgCover.setFitHeight(191);
        cell.getChildren().addAll(imgCover,lblTitle);
        cell.setAlignment(Pos.CENTER);
        cell.setSpacing(5.0);
    }

    @Override
    protected void updateItem(Series item, boolean empty) {
        super.updateItem(item, empty);
        if(item == null || empty)
            this.setGraphic(null);
        else{
            imgCover.setImage(item.getImgCover());
            lblTitle.setText(item.getTitle());
            setGraphic(cell);
        }
    }
}
