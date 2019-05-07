package br.ufop.tomaz.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class FXMLRegisterController implements Initializable {

    @FXML
    private MenuBar menuBar;

    @FXML
    private JFXTextField edtUsername;

    @FXML
    private JFXTextField edtEmail;

    @FXML
    private JFXTextField edtVisiblePassword;

    @FXML
    private JFXPasswordField edtHiddenPassword;

    @FXML
    private JFXButton btnShowPassword;

    @FXML
    private JFXCheckBox ckbKeepMe;

    @FXML
    private JFXButton btnLogin;

    @FXML
    private StackPane imgProfilePane;

    @FXML
    private ImageView imgViewProfile;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.initComponents();
        //TODO -- Disable buttons if any field is empty.
    }

    private void initComponents() {
        //Make a rounded imageView
        double centerX = imgViewProfile.getX() + imgViewProfile.getFitWidth() / 2;
        double centerY = imgViewProfile.getY() + imgViewProfile.getFitHeight() / 2;
        double radius = imgViewProfile.getFitHeight() / 2;
        imgProfilePane.setClip(new Circle(centerX, centerY, radius));
    }

    @FXML
    private void changeImage(ActionEvent event){
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Pick your image's profile");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image File","*.png","*.bmp","*.jpg","*.jpeg"));
        File imgFile = chooser.showOpenDialog(((Node)event.getSource()).getScene().getWindow());
        if(imgFile != null){
            try {
                Image newProfileImage = new Image(new FileInputStream(imgFile));
                System.out.println(newProfileImage.getUrl());
                imgViewProfile.setImage(newProfileImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}
