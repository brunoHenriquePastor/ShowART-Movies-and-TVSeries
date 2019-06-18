package br.ufop.tomaz.controller;

import br.ufop.tomaz.util.Screen;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import org.controlsfx.control.HiddenSidesPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FXMLMainController implements Initializable {

    @FXML private HiddenSidesPane content;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            setContent(Screen.SERIES_LIBRARY);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void home() throws IOException {
        //TODO -- Create a home screen
    }

    @FXML
    private void search() throws IOException{
        //TODO -- Create a search screen
    }

    @FXML
    private void setContent(Screen screen) throws IOException { // TODO -- Implement a fade transition
        Parent content = FXMLLoader.load(getClass().getResource(screen.getPath()));
        this.content.setContent(content);
    }
}
