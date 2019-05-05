package br.ufop.tomaz.controller;

import br.ufop.tomaz.dao.FactoryDAOCreator;
import br.ufop.tomaz.dao.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import br.ufop.tomaz.model.User;
import br.ufop.tomaz.model.UserSession;
import br.ufop.tomaz.util.CustomImage;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;

public class FXMLRegisterController implements Initializable {

    @FXML
    private Pane imgProfilePane;

    @FXML
    private ImageView imgViewProfile;

    @FXML
    private TextField edtUsername;

    @FXML
    private TextField edtPassword;

    @FXML
    private TextField edtConfirmPassword;

    @FXML
    private TextField edtEmail;

    @FXML
    private Button btnChangeImage;

    @FXML
    private Button btnSignUp;

    @FXML
    private Button btnSignUpWithFacebook;

    @FXML
    private Button btnSignUpWithGoogle;

    @FXML
    private Label lblError;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.initComponents();
    }

    private void initComponents() {
        //Initialize the rounded Profile's image
        double deltaX = imgViewProfile.getFitWidth()/2;
        double deltaY = imgViewProfile.getFitHeight()/2;
        Circle clip = new Circle(deltaX,deltaY,75);
        InputStream defaultProfileImage = getClass().getResourceAsStream("/icons/defaultProfileImage.png");
        String defaultProfileImageUrl = getClass().getResource("/icons/defaultProfileImage.png").getPath()
                                                  .replaceFirst("/","").replace("%20"," ")
                                                  .replace("\\",System.getProperty("file.separator"));
        Image profileImage = new CustomImage(defaultProfileImage,defaultProfileImageUrl);
        imgViewProfile.setImage(profileImage);
        imgProfilePane.setClip(clip);
    }

    @FXML
    private void changeImage(ActionEvent event){
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Pick your image's profile");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image File","*.png","*.bmp","*.jpg","*.jpeg"));
        File imgFile = chooser.showOpenDialog(((Node)event.getSource()).getScene().getWindow());
        if(imgFile != null){
            try {
                Image newProfileImage = new CustomImage(new FileInputStream(imgFile),imgFile.getPath());
                imgViewProfile.setImage(newProfileImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void signUp(ActionEvent event){
        String username = edtUsername.getText();
        String email = edtEmail.getText();
        String password = edtPassword.getText();
        String confirmPassword = edtConfirmPassword.getText();
        CustomImage imgProfile = (CustomImage) imgViewProfile.getImage();

        UserDAO userDAO = FactoryDAOCreator.createFactoryDAO().getUserDAO();
        try {
            validateInput(username,email,password,confirmPassword);
            User user = new User(username,password,imgProfile,email,false);
            createDirectories(user);
            if(userDAO.addUserOnDB(user)){
                Stage oldStage = (Stage) ((Node)(event.getSource())).getScene().getWindow();
                oldStage.close();
                UserSession.getInstance().setUser(user);
                System.out.println("User registered with success!");
            } else{
                throw new InvalidInputException("User is already register in our DB!");
            }

        } catch (InvalidInputException | IOException e) {
            lblError.setText(e.getMessage());
            e.printStackTrace();
        }
        finally {
            if(userDAO != null)
                userDAO.closeConnection();
        }

    }

    private void validateInput(String username, String email, String password, String confirmPassword) throws InvalidInputException{
        if(username.isEmpty())
            throw new InvalidInputException("Username must not be empty.");
        if(email.isEmpty())
            throw new InvalidInputException("Email must not be empty.");
        if(!password.equals(confirmPassword))
            throw new InvalidInputException("Password and Confirm Password do not match.");

        //To validate email was used the javax API Internet Address
        try {
            InternetAddress internetAddress = new InternetAddress(email);
            internetAddress.validate();
        } catch (AddressException e) {
            throw new InvalidInputException("Email not valid.");
        }

    }

    private void createDirectories(User user) throws IOException {
        // Make a resource directory to the new User and save the profile image in it
        String imgProfileUrl = user.getImgProfile().getUrl();
        String dirPath = user.getResourcesPath();
        String separator = System.getProperty("file.separator");
        File dir = new File(dirPath);
        if(dir.mkdirs()){
            String imgExtension = imgProfileUrl.substring(imgProfileUrl.lastIndexOf("."));
            String imgUrl = dirPath.concat(separator).concat("Profile Image").concat(imgExtension);
            File source = new File(imgProfileUrl);
            File target = new File(imgUrl);
            Files.copy(source.toPath(),target.toPath(), StandardCopyOption.REPLACE_EXISTING);

            //Sets the path of user's profile image
            user.getImgProfile().setUrl(imgUrl);
        }


    }

    //Exception thrown if informed input is invalid
    private class InvalidInputException extends Exception{
        public InvalidInputException(String error){
            super(error);
        }
    }


}
