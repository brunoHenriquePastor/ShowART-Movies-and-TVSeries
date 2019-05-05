package br.ufop.tomaz.dao;

public class FactoryDAOCreator {

    //TODO
    //Read the configuration file to choose which Factory return
    //In this case, the configuredDB variable will be read from a File

    private static String configuredDB = "SQLite";

    public static FactoryDAO createFactoryDAO(){

        if(configuredDB.equals("SQLite")) {
            return new FactorySQLiteDAO();
        }
        else
            return null;
    }

}
