package br.ufop.tomaz;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent login = FXMLLoader.load(getClass().getResource("/br/ufop/tomaz/fxml/FXMLLogin.fxml"));
        primaryStage.setScene(new Scene(login));
        primaryStage.show();
    }

}
