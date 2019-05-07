package br.ufop.tomaz.controller;


import br.ufop.tomaz.Main;
import br.ufop.tomaz.util.Screen;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class FXMLLoginController implements Initializable {

    @FXML private ImageView imgProfile;
    @FXML private JFXTextField edtUsername;
    @FXML private JFXPasswordField edtHiddenPassword;
    @FXML private JFXTextField edtVisiblePassword;
    @FXML private JFXCheckBox ckbKeepMe;
    @FXML private JFXButton btnShowPassword;
    @FXML private MenuBar menuBar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Make a rounded imageView
        double centerX = imgProfile.getX() + imgProfile.getFitWidth() / 2;
        double centerY = imgProfile.getY() + imgProfile.getFitHeight() / 2;
        double radius = imgProfile.getFitHeight() / 2;
        imgProfile.setClip(new Circle(centerX, centerY, radius));

        //Controls the show/hide password behavior
        edtVisiblePassword.textProperty().bind(edtHiddenPassword.textProperty());
        edtVisiblePassword.visibleProperty().bind(btnShowPassword.pressedProperty());
        edtHiddenPassword.visibleProperty().bind(btnShowPassword.pressedProperty().not());

        //TODO -- Disable buttons if any field is empty.
    }

    @FXML
    private void signUp() throws IOException {
        Main.setScreen(Screen.REGISTER);
    }


}

