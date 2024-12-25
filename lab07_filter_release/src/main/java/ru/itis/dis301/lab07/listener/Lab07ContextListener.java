package ru.itis.dis301.lab07.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.Cookie;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.flywaydb.core.Flyway;
import ru.itis.dis301.lab07.db.DBConnection;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@WebListener
public class Lab07ContextListener implements ServletContextListener {
    final static Logger logger = LogManager.getLogger(Lab07ContextListener.class);

    public void contextInitialized(ServletContextEvent event) {
        DBConnection.getDB();
        logger.info("start migration config");

        Flyway flyway = Flyway.configure()//.baselineOnMigrate(true)
                .dataSource("jdbc:postgresql://localhost:5432/lab06", "postgres", "Solod20.03").load();
        logger.info("start migration");

        // Start the migration
        flyway.migrate();
        logger.info("migration done");

        Map<UUID, String> userSessions = new HashMap<>();
        event.getServletContext().setAttribute("USER_SESSIONS", userSessions);
    }

    public void contextDestroyed(ServletContextEvent event) {
        DBConnection.getDB().closeAllConnections();
        Map<UUID, String> userSessions = (Map<UUID, String>) event.getServletContext().getAttribute("USER_SESSIONS");
        userSessions.clear();
    }



}
