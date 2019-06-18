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
    }

    public static void setScreen(Screen screen) throws IOException {
        Parent newScreen = FXMLLoader.load(Main.class.getResource(screen.getPath()));
        Scene oldScene = stage.getScene();
        double width = oldScene != null ? oldScene.getWidth() : 740.0;
        double height = oldScene != null ? oldScene.getHeight() : 580.0;
        Scene scene = new Scene(newScreen, width, height);
        stage.setScene(scene);
        stage.show();
    }

}
