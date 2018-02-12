package pl.edu.agh.kis.application.connectivity;

import org.springframework.beans.factory.annotation.Value;
import org.sql2o.Sql2o;

/**
 * Singleton used to obtain instance of connection to database
 */
public class DbConnection {
//    private static Sql2o ourInstance = new Sql2o("jdbc:mysql://mysql.agh.edu.pl:3306/sadowski", "sadowski", "xPjFWAM00pYfJAP6");
    private static Sql2o ourInstance = new Sql2o("jdbc:sqlite:C:\\Users\\Szymon\\Documents\\SpringLongPolling\\myDb.db", null, null);

    /**
     * @return obtained instance of connection to database
     */
    public static Sql2o getInstance() {
//        Class.forName("org.sqlite.JDBC");
        return ourInstance;
    }

    private DbConnection() {
    }
}
