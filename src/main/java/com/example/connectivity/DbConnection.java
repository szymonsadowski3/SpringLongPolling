package com.example.connectivity;

import org.sql2o.Sql2o;

public class DbConnection {
    private static Sql2o ourInstance = new Sql2o("jdbc:mysql://mysql.agh.edu.pl:3306/sadowski", "sadowski", "xPjFWAM00pYfJAP6");

    public static Sql2o getInstance() {
        return ourInstance;
    }

    private DbConnection() {
    }
}
