package edu.madisoncollege.enterprisejava.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Ranjit Sekhon
 * @version 1.0 10/20/15.
 */
public class Database {

    private static Database instance = new Database();

    private Connection connection;

    private Database() {

    }

    public static Database getInstance() {
        return instance;
    }


    public Connection getConnection() {
        return connection;
    }

    public void connect() throws Exception {
        if (connection != null)
            return;

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new Exception("Error: MySQL Driver not found");
        }
   // TODO Add your specific database info below
        String url = "jdbc:mysql://localhost:3306/employees";

        connection = DriverManager.getConnection(url, "root", "root");
    }

    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Error: Cannot close connection");
            }
        }

        connection = null;
    }

}
