package br.ufop.tomaz;
import br.ufop.tomaz.util.Screen;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;
        setScreen(Screen.LOGIN);
        /*
        Parent login = FXMLLoader.load(getClass().getResource("/br/ufop/tomaz/fxml/FXMLLogin.fxml"));
        primaryStage.setScene(new Scene(login));
        primaryStage.show();
        */
    }

    public static void setScreen(Screen screen) throws IOException {
        Parent newScreen = FXMLLoader.load(Main.class.getResource(screen.getPath()));
        stage.setScene(new Scene(newScreen));
        stage.show();
    }

}
