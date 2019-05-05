package br.ufop.tomaz.dao;

import br.ufop.tomaz.model.Movie;
import br.ufop.tomaz.model.User;

import java.sql.Connection;
import java.util.List;

public abstract class MovieDAO {

    protected Connection connection;

    public MovieDAO(Connection connection) {
        this.connection = connection;
    }

    public abstract boolean addMovieOnDB(Movie movie, User user);

    public abstract boolean deleteMovieOnDB(Movie movie, User user);

    public abstract List<Movie> retrieveMoviesFromDB(User user);

    public abstract boolean updateMovieOnDB(Movie movie, User user);

}
