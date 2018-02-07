package com.example.poll.core.implementations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.sql.RowSetEvent;
import javax.sql.RowSetListener;
import javax.sql.rowset.JdbcRowSet;

import com.sun.rowset.JdbcRowSetImpl;

public class Something {
    public static void main(String[] args) throws Exception {
        Connection conn = getMySqlConnection();
        System.out.println("Got Connection.");
        Statement st = conn.createStatement();
        st.executeUpdate("create table survey (id INTEGER,name VARCHAR(255));");
        st.executeUpdate("insert into survey (id,name ) values (1,'nameValue')");
        st.executeUpdate("insert into survey (id,name ) values (2,'anotherValue')");


        JdbcRowSet jdbcRS;
        jdbcRS = new JdbcRowSetImpl(conn);
        jdbcRS.setType(ResultSet.TYPE_SCROLL_INSENSITIVE);
        String sql = "SELECT * FROM survey";
        jdbcRS.setCommand(sql);
        jdbcRS.execute();
        jdbcRS.addRowSetListener(new ExampleListener());

        while (jdbcRS.next()) {
            // each call to next, generates a cursorMoved event
            System.out.println("id=" + jdbcRS.getString(1));
            System.out.println("name=" + jdbcRS.getString(2));
        }
        conn.close();
    }

    private static Connection getHSQLConnection() throws Exception {
        Class.forName("org.hsqldb.jdbcDriver");
        System.out.println("Driver Loaded.");
        String url = "jdbc:hsqldb:data/tutorial";
        return DriverManager.getConnection(url, "sa", "");
    }

    public static Connection getMySqlConnection() throws Exception {
        String driver = "org.gjt.mm.mysql.Driver";
        String url = "jdbc:mysql://mysql.agh.edu.pl:3306/sadowski";
        String username = "sadowski";
        String password = "xPjFWAM00pYfJAP6";

        Class.forName(driver);
        Connection conn = DriverManager.getConnection(url, username, password);
        return conn;
    }

    public static Connection getOracleConnection() throws Exception {
        String driver = "oracle.jdbc.driver.OracleDriver";
        String url = "jdbc:oracle:thin:@localhost:1521:databaseName";
        String username = "userName";
        String password = "password";

        Class.forName(driver); // load Oracle driver
        Connection conn = DriverManager.getConnection(url, username, password);
        return conn;
    }

}

class ExampleListener implements RowSetListener {

    public void cursorMoved(RowSetEvent event) {
        System.out.println("ExampleListener notified of cursorMoved event");
        System.out.println(event.toString());
    }

    public void rowChanged(RowSetEvent event) {
        System.out.println("ExampleListener notified of rowChanged event");
        System.out.println(event.toString());
    }

    public void rowSetChanged(RowSetEvent event) {
        System.out.println("ExampleListener notified of rowSetChanged event");
        System.out.println(event.toString());
    }
}
