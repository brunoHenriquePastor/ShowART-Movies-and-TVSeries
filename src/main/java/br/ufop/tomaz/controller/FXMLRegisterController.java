package br.ufop.tomaz.controller;

import br.ufop.tomaz.Main;
import br.ufop.tomaz.dao.UserDAO;
import br.ufop.tomaz.dao.UserDAOImpl;
import br.ufop.tomaz.model.User;
import br.ufop.tomaz.util.Screen;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.util.Calendar;
import java.util.ResourceBundle;

public class FXMLRegisterController implements Initializable {

    @FXML private MenuBar menuBar;
    @FXML private JFXTextField edtUsername;
    @FXML private JFXTextField edtEmail;
    @FXML private JFXTextField edtVisiblePassword;
    @FXML private JFXPasswordField edtHiddenPassword;
    @FXML private JFXButton btnShowPassword;
    @FXML private JFXCheckBox ckbKeepMe;
    @FXML private JFXButton btnSignUp;
    @FXML private StackPane imgProfilePane;
    @FXML private ImageView imgViewProfile;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Make a rounded imageView
        double centerX = imgViewProfile.getX() + imgViewProfile.getFitWidth() / 2;
        double centerY = imgViewProfile.getY() + imgViewProfile.getFitHeight() / 2;
        double radius = imgViewProfile.getFitHeight() / 2;
        imgProfilePane.setClip(new Circle(centerX, centerY, radius));

        //Configure show/hide button behavior
        edtHiddenPassword.visibleProperty().bind(btnShowPassword.pressedProperty().not());
        edtVisiblePassword.visibleProperty().bind(btnShowPassword.pressedProperty());
        edtVisiblePassword.textProperty().bind(edtHiddenPassword.textProperty());

        //Configure sign up button behavior
        btnSignUp.disableProperty().bind(
                edtUsername.textProperty().isEmpty()
                        .or(edtEmail.textProperty().isEmpty()
                                .or(edtHiddenPassword.textProperty().isEmpty())
                        )
        );
    }

    @FXML
    private void changeImage(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Pick your image's profile");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image File", "*.png", "*.bmp", "*.jpg", "*.jpeg"));
        File imgFile = chooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());
        if (imgFile != null) {
            try {
                Image newProfileImage = new Image(imgFile.toURI().toURL().toExternalForm());
                imgViewProfile.setImage(newProfileImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void registerUser() throws IOException {
        User user = getUserToRegister();
        boolean isValid = validateEmail(user.getEmail());
        String message;
        Alert alert;

        if (isValid) {
            UserDAO userDAO = UserDAOImpl.getInstance();
            if (userDAO.addUser(user)) {
                createUserDirectories(user.getUsername());
                String imgProfilePath = saveImageProfile(user.getUsername());
                user.setImgProfilePath(imgProfilePath);
                userDAO.updateUser(user);
                message = user.getUsername().concat(" was registered with success!");
                alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
                alert.showAndWait();
                Main.setScreen(Screen.MAIN);
            } else {
                message = "The informed username/email is already registered!";
                alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
                alert.show();
            }
        } else {
            message = "Please, inform a valid e-mail address!";
            alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
            alert.show();
        }
    }

    private boolean validateEmail(String email) {
        try {
            InternetAddress address = new InternetAddress(email);
            address.validate();
            return true;
        } catch (AddressException e) {
            //e.printStackTrace();
            return false;
        }
    }

    private User getUserToRegister() {
        String username = edtUsername.getText();
        String email = edtEmail.getText();
        String password = edtHiddenPassword.getText();
        String imgProfilePath = imgViewProfile.getImage().getUrl();
        boolean keepConnected = ckbKeepMe.isSelected();

        return new User(username, password, imgProfilePath, email, keepConnected, Calendar.getInstance().getTime());
    }

    private void createUserDirectories(String username) {
        String dirPath = System.getProperty("user.home")
                .concat(File.separator)
                .concat("ShowArt-Movies-and-TVSeries")
                .concat(File.separator)
                .concat(username);
        File dir = new File(dirPath);
        if (dir.mkdirs()) {
            new File(dirPath.concat(File.separator).concat("Series")).mkdir();
            new File(dirPath.concat(File.separator).concat("Movies")).mkdir();
        } else {
            System.out.println("There was an error creating directories...");
            System.out.println("Please try again!");
        }
    }

    private String saveImageProfile(String username) throws IOException {
        File imgFile = new File(URI.create(imgViewProfile.getImage().getUrl()));
        String extension = imgFile.getName().substring(imgFile.getName().lastIndexOf("."));
        String url = System.getProperty("user.home")
                .concat(File.separator)
                .concat("ShowArt-Movies-and-TVSeries")
                .concat(File.separator)
                .concat(username)
                .concat(File.separator)
                .concat("Profile")
                .concat(extension);
        File userImageProfile = new File(url);
        Files.copy(imgFile.toPath(), new FileOutputStream(userImageProfile));
        return userImageProfile.toURI().toURL().toExternalForm();
    }
}
