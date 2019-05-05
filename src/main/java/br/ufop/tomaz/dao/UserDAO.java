package br.ufop.tomaz.dao;

import br.ufop.tomaz.model.User;

import java.sql.Connection;
import java.util.LinkedList;

public abstract class UserDAO {

    protected Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    public abstract User retrieveUser(String username);

    public abstract User lastLogged();

    public abstract boolean addUserOnDB(User user);

    public abstract boolean deleteUserOnDB(String username);

    public abstract boolean updateUserOnDB(User user);

    public abstract LinkedList<User> retrieveKeepConnected();

    public abstract void closeConnection();

}
