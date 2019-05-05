package br.ufop.tomaz.dao;

import org.sqlite.SQLiteConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FactorySQLiteDAO implements FactoryDAO {

    private String databaseName;
    public FactorySQLiteDAO() {
        //TODO -- Read the name of database from a configuration file
        this.databaseName = "ShowartDB.db";
    }

    @Override
    public UserDAO getUserDAO() {
        SQLiteConnection connection = openConnection();
        return  new UserSQLiteDAO(connection);
    }

    @Override
    public MovieDAO getMovieDAO() {
        SQLiteConnection connection = openConnection();
        return new MovieSQLiteDAO(connection);
    }

    @Override
    public SeriesDAO getSeriesDAO() {
        SQLiteConnection connection = openConnection();
        return new SeriesSQLiteDAO(connection);
    }

    @Override
    public SQLiteConnection openConnection(){
        try {
            SQLiteConnection connection = (SQLiteConnection) DriverManager.getConnection("jdbc:sqlite:/home/tomaz/IdeaProjects/ShowART---Movies-and-Series/src/main/resources/ShowartDB.db");
            connection.getDatabase().exec("PRAGMA foreign_keys = ON;",true);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
