package br.ufop.tomaz.util;

import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.util.HashMap;

public class ScreensController extends AnchorPane {

    public static final int SERIES_LIBRARY = 0;
    public static final int MOVIES_LIBRARY = 1;

    private HashMap<Integer, Node> screens = new HashMap<>();

    public ScreensController() {
        super();
    }

    private void registerScreen(Integer key, Node screen) {
        screens.put(key, screen);
    }

    private Node getScreen(Integer id) {
        return screens.get(id);
    }

    public boolean loadScreen(Integer id, String FXMLpath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FXMLpath));
            Parent screen = loader.load();
            ControlledScreen myController = loader.getController();
            screen.setUserData(myController); // Insert the controller in NODE to access it in setScreen method
            registerScreen(id, screen);
            myController.setContentParent(this);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void setScreen(Integer id) {
        Node newScreen = getScreen(id);
        if (getChildren().size() == 0) {
            FadeTransition fadeIn = new FadeTransition();
            fadeIn.setNode(newScreen);
            fadeIn.setDuration(new Duration(1000));
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            getChildren().add(newScreen);
            AnchorPane.setBottomAnchor(newScreen, 0.0);
            AnchorPane.setRightAnchor(newScreen, 0.0);
            AnchorPane.setLeftAnchor(newScreen, 0.0);
            AnchorPane.setTopAnchor(newScreen, 0.0);
            fadeIn.play();

            // Load the screen's content
            ControlledScreen screenController = (ControlledScreen) newScreen.getUserData();
            screenController.loadContent();
        } else {
            getChildren().remove(0);
            setScreen(id);
        }
    }

}
