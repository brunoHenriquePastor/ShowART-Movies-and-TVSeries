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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.URL;
import java.util.*;


public class FXMLLoginController implements Initializable {

    @FXML private ImageView imgProfile;
    @FXML private JFXTextField edtUsername;
    @FXML private JFXPasswordField edtHiddenPassword;
    @FXML private JFXTextField edtVisiblePassword;
    @FXML private JFXCheckBox ckbKeepMe;
    @FXML private JFXButton btnShowPassword;
    @FXML private MenuBar menuBar;
    @FXML private JFXButton btnLogin;

    private Map<String, User> rememberedUsers = new HashMap<>();
    //private JFXAutoCompletePopup<User> autoCompletePopup = new JFXAutoCompletePopup<User>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configureImageViewProfile();
        configureButtons();
        loadRememberedUsers();
    }

    @FXML
    private void signUp() throws IOException {
        Main.setScreen(Screen.REGISTER);
    }

    private void configureButtons(){
        //Controls the show/hide password behavior
        edtVisiblePassword.textProperty().bind(edtHiddenPassword.textProperty());
        edtVisiblePassword.visibleProperty().bind(btnShowPassword.pressedProperty());
        edtHiddenPassword.visibleProperty().bind(btnShowPassword.pressedProperty().not());

        //Controls the login button behavior
        btnLogin.disableProperty()
                .bind(edtUsername.textProperty().isEmpty()
                        .or(edtHiddenPassword.textProperty().isEmpty()));
    }

    private void configureImageViewProfile(){
        //Make a rounded imageView
        double centerX = imgProfile.getX() + imgProfile.getFitWidth() / 2;
        double centerY = imgProfile.getY() + imgProfile.getFitHeight() / 2;
        double radius = imgProfile.getFitHeight() / 2;
        imgProfile.setClip(new Circle(centerX, centerY, radius));
    }

    private void loadRememberedUsers(){
        UserDAO userDAO = UserDAOImpl.getInstance();
        List<User> rememberedUsers = userDAO.retrieveRememberedUsers();
        //initAutoCompletePopup(rememberedUsers);
        rememberedUsers.forEach(user -> this.rememberedUsers.put(user.getUsername(), user));

        //Load the last logged user.
        if(!rememberedUsers.isEmpty()){
            User lastLogged = rememberedUsers
                    .stream()
                    .max(Comparator.comparing(User::getLastLogin))
                    .get();
            loadUser(lastLogged);
        }


    }
/*
    private void initAutoCompletePopup(List<User> users){
        autoCompletePopup.getSuggestions().addAll(users);
        autoCompletePopup.setSuggestionsCellFactory(new Callback<>() {
            @Override
            public ListCell<User> call(ListView<User> param) {
                return new JFXListCell<>() {
                    @Override
                    protected void updateItem(User item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty)
                            setText("");
                        else
                            setText(item.getUsername());
                    }
                };
            }
        });
        autoCompletePopup.setSelectionHandler(event -> loadUser(event.getObject()));
        edtUsername.textProperty().addListener(observable -> {
            autoCompletePopup.filter(user -> user.getUsername().startsWith(edtUsername.getText()));
            if(autoCompletePopup.getFilteredSuggestions().isEmpty())
                autoCompletePopup.show(edtUsername);
            else
                autoCompletePopup.hide();
        });
    }

 */
    @FXML
    private void login() throws IOException {
        String username = edtUsername.getText();
        String password = edtHiddenPassword.getText();
        UserDAO userDAO = UserDAOImpl.getInstance();
        User user = userDAO.retrieveUser(username);

        if(user != null){
            if(user.getPassword().equals(password)){
                user.setLastLogin(Calendar.getInstance().getTime());
                user.setRememberMyPassword(ckbKeepMe.isSelected());
                userDAO.updateUser(user);
                Main.setScreen(Screen.MAIN);
            }
        }
    }

    private void loadUser(User user){
        edtUsername.setText(user.getUsername());
        edtHiddenPassword.setText(user.getPassword());
        ckbKeepMe.setSelected(user.isRememberMyPassword());
        imgProfile.setImage(new Image(user.getImgProfilePath()));
    }

}

