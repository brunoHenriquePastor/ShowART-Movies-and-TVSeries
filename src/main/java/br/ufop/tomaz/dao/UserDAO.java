package br.ufop.tomaz.dao;

import br.ufop.tomaz.model.User;

import java.util.List;

public interface UserDAO {

    boolean addUser(User user);

    User retrieveUser(String id);

    boolean removeUser(User user);

    boolean updateUser(User user);

    List<User> retrieveRememberedUsers();
}
