package br.ufop.tomaz.dao;

import br.ufop.tomaz.model.User;
import br.ufop.tomaz.util.CustomImage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;

public class UserSQLiteDAO extends UserDAO {

    public UserSQLiteDAO(Connection connection) {
        super(connection);
    }

    @Override
    public User retrieveUser(String username) {
        //language=SQLite
        String sql = "SELECT* FROM USER WHERE Username = ?";

        try {
            //Execute the search in DB
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, username);
            ResultSet result = pst.executeQuery();
            if (result.next()) {
                //Retrieve the values
                return getUser(result);
            }

        } catch (SQLException | IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User lastLogged() {
        String sql = "SELECT * FROM USER WHERE LastLogin = (SELECT MAX(LastLogin)FROM USER)";
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()) {
                //Retrieve the values
                return getUser(resultSet);
            }
        } catch (SQLException | IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private User getUser(ResultSet resultSet) throws SQLException, IOException, ParseException {
        String username = resultSet.getString("username");
        String email = resultSet.getString("email");
        String password = resultSet.getString("password");
        boolean isRememberMe = resultSet.getBoolean("isRememberMe");
        CustomImage img = loadImageFromDB(resultSet);
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY HH:mm", Locale.getDefault());
        Date lastLogin = dateFormat.parse(resultSet.getString("LastLogin"));
        return new User(username, password, img, email, isRememberMe, lastLogin);
    }

    @Override
    public boolean addUserOnDB(User user) {
        //language=SQLite
        String sql = "INSERT INTO USER VALUES (?,?,?,?,?,?)";
        try {

            String username = user.getUsername();
            String password = user.getPassword();
            String email = user.getEmail();
            boolean isRememberMe = user.getIsRememberMyPassword();
            String imgUrl = user.getImgProfile().getUrl();
            String lastLogin = new SimpleDateFormat("dd-MM-YYYY HH:mm", Locale.getDefault())
                    .format(Date.from(Instant.now()));

            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, email);
            pst.setString(3, password);
            pst.setString(4, imgUrl);
            pst.setBoolean(5, isRememberMe);
            pst.setString(6, lastLogin);
            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean deleteUserOnDB(String username) {
        String sql = "DELETE FROM USER WHERE Username = ?";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1, username);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateUserOnDB(User user) {

        String sql = "UPDATE USER SET Email = ?, Password = ?,ImageProfile = ?, IsRememberMe = ?,LastLogin = ? WHERE Username = ?";

        try {
            String username = user.getUsername();
            String password = user.getPassword();
            String email = user.getEmail();
            boolean isRememberMe = user.getIsRememberMyPassword();
            String urlImage = user.getImgProfile().getUrl();
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY HH:mm", Locale.getDefault());
            String lastLogin = dateFormat.format(user.getLastLogin());

            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, email);
            pst.setString(2, password);
            pst.setString(3, urlImage);
            pst.setBoolean(4, isRememberMe);
            pst.setString(6, username);
            pst.setString(5, lastLogin);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public LinkedList<User> retrieveKeepConnected() {

        String sql = "SELECT * FROM USER WHERE IsRememberMe = TRUE ORDER BY LastLogin DESC";
        LinkedList<User> keepConnectedUsers = new LinkedList<>();

        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                User user = getUser(resultSet);
                keepConnectedUsers.add(user);
            }
            return keepConnectedUsers;
        } catch (SQLException | IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void closeConnection() {
        try {
            this.connection.close();
            System.out.println("Connection closed.");
        } catch (SQLException e) {
            System.out.println("Occurred some error.The connection continue open !");
            e.printStackTrace();
        }
    }

    private CustomImage loadImageFromDB(ResultSet resultSet) throws SQLException, IOException {
        String urlImage = resultSet.getString("ImageProfile");
        InputStream inputStream = Files.newInputStream(new File(urlImage).toPath(), StandardOpenOption.READ);
        return new CustomImage(inputStream, urlImage);
    }


}


