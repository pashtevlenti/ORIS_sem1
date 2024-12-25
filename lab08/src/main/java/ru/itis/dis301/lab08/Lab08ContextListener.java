package ru.itis.dis301.lab08;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.flywaydb.core.Flyway;
import ru.itis.dis301.lab08.controller.IndexPageServlet;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@WebListener
public class Lab08ContextListener implements ServletContextListener {

    final static Logger logger = LogManager.getLogger(Lab08ContextListener.class);

    public void contextInitialized(ServletContextEvent sce) {
        // Create the Flyway instance and point it to the database

        logger.info("start migration config");

        Flyway flyway = Flyway.configure()//.baselineOnMigrate(true)
                .dataSource("jdbc:postgresql://localhost:5432/lab08", "postgres", "Solod20.03").load();
        logger.info("start migration");

        // Start the migration
        flyway.migrate();
        logger.info("migration done");
    }

    public void contextDestroyed(ServletContextEvent sce) {
        // Закрываем подключение
    }
}
