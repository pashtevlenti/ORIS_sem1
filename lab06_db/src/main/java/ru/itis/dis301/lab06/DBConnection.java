package ru.itis.dis301.lab06;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Stack;

public class DBConnection {

    private Stack<Connection> stackConnections;
    private static volatile DBConnection dbConnection;

    private DBConnection() {
        if (stackConnections == null) {
            initDb();
        }
    }

    public static DBConnection getDB() {
        if (dbConnection == null) {
            synchronized (DBConnection.class) {
                if (dbConnection == null) {
                    dbConnection = new DBConnection();
                }
            }
        }
        return dbConnection;
    }
    private void initDb() {
        try {
            stackConnections = new Stack<>();
            Class.forName("org.postgresql.Driver");
            for (int i = 0; i < 5; i++){
                Connection connection =
                        DriverManager.getConnection("jdbc:postgresql://localhost:5432/cloud","postgres","Solod20.03");
                stackConnections.add(connection);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(System.out);
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        return stackConnections.pop();
    }

    public void releaseConnection(Connection connection) {
        stackConnections.add(connection);
    }

    public void closeAllConnections() {
        try {
            while (!stackConnections.isEmpty()) {
                stackConnections.pop().close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
