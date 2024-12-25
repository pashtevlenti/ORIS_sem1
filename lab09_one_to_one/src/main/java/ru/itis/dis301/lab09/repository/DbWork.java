package ru.itis.dis301.lab09.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.EmptyStackException;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class DbWork {

    private static DbWork instance;

    private Stack<Connection> connections;

    private Set<Connection> usedConnections;

    private static final int MAX_CONNECTIONS = 5;

    private DbWork() {
        connections = new Stack<>();
        usedConnections = new HashSet<>();

        try {
            Class.forName("org.postgresql.Driver");

            for (int i = 0; i < MAX_CONNECTIONS; ++i) {
                Connection connection =
                        DriverManager.getConnection(
                                // адрес БД , имя пользователя, пароль
                                "jdbc:postgresql://localhost:5432/lab09", "postgres", "passwd");
                connections.push(connection);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static DbWork getInstance() {
        if (instance == null) {
            synchronized (DbWork.class) {
                if (instance == null) {
                    instance = new DbWork();
                }
            }
        }
        return instance;
    }

    public synchronized Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            connection = connections.pop();
            usedConnections.add(connection);
        } catch (EmptyStackException e) {
            connection =
                    DriverManager.getConnection(
                            "jdbc:postgresql://localhost:5432/lab07", "postgres", "passwd");
        }
        return connection;
    }

    public synchronized void releasConnection(Connection connection) {
        usedConnections.remove(connection);
        connections.push(connection);
    }

    public void destroy() {
        for (Connection connection : usedConnections ) {
            try {
                connection.rollback();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        for (Connection connection : connections) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
