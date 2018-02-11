package pl.edu.agh.kis.application.connectivity;

import org.sql2o.Sql2o;

/**
 * Singleton used to obtain instance of connection to database
 */
public class DbConnection {
    private static Sql2o ourInstance = new Sql2o("jdbc:mysql://mysql.agh.edu.pl:3306/sadowski", "sadowski", "xPjFWAM00pYfJAP6");

    /**
     * @return obtained instance of connection to database
     */
    public static Sql2o getInstance() {
        return ourInstance;
    }

    private DbConnection() {
    }
}
