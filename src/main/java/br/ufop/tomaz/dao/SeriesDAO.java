package br.ufop.tomaz.dao;

import br.ufop.tomaz.model.Series;
import br.ufop.tomaz.model.User;

import java.sql.Connection;
import java.util.List;

public abstract class SeriesDAO {

    protected Connection connection;

    SeriesDAO(Connection connection) {
        this.connection = connection;
    }

    public abstract List<Series> retrieveSeries(String title, User user);

    public abstract List<Series> retrieveSeries(User user);

    public abstract boolean addSeriesToDB(Series series, User user);

    public abstract boolean deleteSeriesFromDB(Series series, User user);

    public abstract boolean updateSeriesOnDB(Series series, User user);

    public abstract void closeConnection();
}
