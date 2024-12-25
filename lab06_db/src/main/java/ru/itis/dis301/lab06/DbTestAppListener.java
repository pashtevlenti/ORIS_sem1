package ru.itis.dis301.lab06;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import java.sql.Connection;
import java.sql.SQLException;

@WebListener
public class DbTestAppListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent sce) {
        // Создаем подключение
        DBConnection.getDB();

    }

    public void contextDestroyed(ServletContextEvent sce) {
        // Закрываем подключение
        DBConnection.getDB().closeAllConnections();

    }
}