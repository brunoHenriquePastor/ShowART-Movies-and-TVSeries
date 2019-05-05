package br.ufop.tomaz.dao;

import br.ufop.tomaz.model.Movie;
import br.ufop.tomaz.model.User;

import java.sql.Connection;
import java.util.List;

public class MovieSQLiteDAO extends MovieDAO {

    public MovieSQLiteDAO(Connection connection) {
        super(connection);
    }

    @Override
    public boolean addMovieOnDB(Movie movie, User user) {
        return false;
    }

    @Override
    public boolean deleteMovieOnDB(Movie movie, User user) {
        return false;
    }

    @Override
    public List<Movie> retrieveMoviesFromDB(User user) {
        return null;
    }

    @Override
    public boolean updateMovieOnDB(Movie movie, User user) {
        return false;
    }
}
