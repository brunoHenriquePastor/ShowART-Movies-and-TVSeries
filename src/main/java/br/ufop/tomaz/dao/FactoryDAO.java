package br.ufop.tomaz.dao;

import java.sql.Connection;

public interface FactoryDAO {
    UserDAO getUserDAO();
    MovieDAO getMovieDAO();
    SeriesDAO getSeriesDAO();

    Connection openConnection();
}
