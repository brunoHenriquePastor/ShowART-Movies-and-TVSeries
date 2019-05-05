package br.ufop.tomaz.util;

import br.ufop.tomaz.model.Actor;
import br.ufop.tomaz.model.SerieAndMovieInterface.Category;
import br.ufop.tomaz.model.Series;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import org.controlsfx.control.CheckComboBox;


import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddSeriesDialog extends Dialog<Series> {

    private AddSeriesDialogContent content;

    public AddSeriesDialog() {
        super();
        initComponents();
        this.setDialogPane(content);
        setResultConverter((result) -> {
            if (result == ButtonType.OK) {
                String title = content.edtTitle.getText();
                String description = content.edtDescription.getText();
                Image imgCover = content.imgCover.getImage();
                Integer rate = 3;
                List<Category> categories = content.ckbCategories.checkModelProperty().getValue().getCheckedItems();
                List<Actor> cast = content.castListView.getItems();
                Date releaseDate = Date.from(Instant.from(content.dpReleaseDate.getValue().atStartOfDay(ZoneId.systemDefault())));
                return new Series(title,description,categories,rate,imgCover,cast,releaseDate);
            } else return null;
        });
    }

    private void initComponents() {
        this.content = new AddSeriesDialogContent();
    }

    private class AddSeriesDialogContent extends DialogPane {

        @FXML
        private TextField edtTitle;

        @FXML
        private TextArea edtDescription;

        @FXML
        private ImageView imgCover;

        @FXML
        private Button btnSearch;

        @FXML
        private DatePicker dpReleaseDate;

        @FXML
        private ProgressIndicator downloadProgressIndicator;

        @FXML
        private CheckComboBox<Category> ckbCategories;

        @FXML
        private ListView<Actor> castListView;

        public AddSeriesDialogContent() {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FXMLAddSeriesDialogContent.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            try {
                loader.load();
                initComponents();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void initComponents() {
            btnSearch.disableProperty().bind(edtTitle.textProperty().isEmpty());
            btnSearch.setOnAction(event -> {
                cleanFields();
                searchImdbInformation();
            });
            ckbCategories.getItems().setAll(Category.values());
            castListView.setCellFactory(new Callback<ListView<Actor>, ListCell<Actor>>() {
                @Override
                public ListCell<Actor> call(ListView<Actor> param) {
                    ListCell<Actor> cell = new ActorListCell();
                    cell.setOnMouseClicked((e)->{
                        if(cell.getItem() != null)
                           openActorIMDBPage(cell.getItem().getWebPageLink());
                        e.consume();
                    });
                    return cell;
                }
            });
        }

        private void cleanFields(){
            this.edtDescription.setText("");
            this.castListView.getItems().clear();
            this.imgCover.setImage(null);
            this.ckbCategories.getItems().forEach(e -> ckbCategories.getItemBooleanProperty(e).setValue(false));
        }

        private void searchImdbInformation(){
            DoubleProperty tasksProgress = new SimpleDoubleProperty(0.0);
            downloadProgressIndicator.progressProperty().bind(tasksProgress);

            IMDBUtilities imdbUtilities = new IMDBUtilities(edtTitle.getText());
            Task<Map<String, String>> srtInformationIMDB = new Task<Map<String, String>>() {
                @Override
                protected Map<String, String> call() throws Exception {
                    Map<String, String> mapInformation = new HashMap<>(2);
                    mapInformation.put("title", imdbUtilities.getTitle());
                    mapInformation.put("description", imdbUtilities.getDescription());
                    return mapInformation;
                }

                @Override
                protected void succeeded() {
                    String title = getValue().get("title");
                    String description = getValue().get("description");
                    Platform.runLater(() -> {
                        tasksProgress.setValue(tasksProgress.getValue()+0.20);
                        edtTitle.setText(title);
                        edtDescription.setText(description);
                    });
                }

            };
            Task<Image> imageIMDBTask = new Task<Image>() {
                @Override
                protected Image call() throws Exception {
                    return imdbUtilities.getImageCover();
                }

                @Override
                protected void succeeded() {
                    Platform.runLater(() -> {imgCover.setImage(getValue());
                        tasksProgress.setValue(tasksProgress.getValue()+0.20);});
                }
            };
            Task<List<Category>> categoriesTask = new Task<List<Category>>() {
                @Override
                protected List<Category> call() throws Exception {
                    return imdbUtilities.getCategories();
                }

                @Override
                protected void succeeded() {
                    Platform.runLater(() -> {
                        ckbCategories.getItems()
                                .forEach((category -> {
                                    if(getValue().contains(category))
                                        ckbCategories.getItemBooleanProperty(category).setValue(true);
                                    else
                                        ckbCategories.getItemBooleanProperty(category).setValue(false);
                                }));
                        tasksProgress.setValue(tasksProgress.getValue() + 0.20);
                    });
                }
            };
            Task<Integer> rateIMDBTask = new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    return imdbUtilities.getRate();
                }

            };
            Task<Date> releaseDateIMDBTask = new Task<Date>() {
                @Override
                protected Date call() throws Exception {
                    return imdbUtilities.getReleaseDate();
                }

                @Override
                protected void succeeded() {
                    LocalDate localDate = getValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    Platform.runLater(()->{dpReleaseDate.setValue(localDate);
                        tasksProgress.setValue(tasksProgress.getValue() + 0.20);
                    });
                }
            };
            Task<List<Actor>> actorsIMDBTask = new Task<List<Actor>>() {
                @Override
                protected List<Actor> call() throws Exception {
                    return imdbUtilities.getCast();
                }

                @Override
                protected void succeeded() {
                    Platform.runLater(()->castListView.getItems().setAll(getValue()));
                }
            };
            new Thread(actorsIMDBTask).start();
            new Thread(srtInformationIMDB).start();
            new Thread(categoriesTask).start();
            new Thread(imageIMDBTask).start();
            new Thread(rateIMDBTask).start();
            new Thread(releaseDateIMDBTask).start();
        }

        private void openActorIMDBPage(String url){

            if(Desktop.isDesktopSupported()){
                Desktop desktop = Desktop.getDesktop();
                try {
                    desktop.browse(new URI(url));
                } catch (IOException | URISyntaxException e) {
                    e.printStackTrace();
                }
            }
            else{
                Runtime runtime = Runtime.getRuntime();
                try {
                    runtime.exec("xdg-open " + url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
