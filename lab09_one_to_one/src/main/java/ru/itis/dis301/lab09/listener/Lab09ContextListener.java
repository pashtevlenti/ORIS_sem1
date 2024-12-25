package ru.itis.dis301.lab09.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.flywaydb.core.Flyway;
import ru.itis.dis301.lab09.repository.DbWork;

@WebListener
public class Lab09ContextListener implements ServletContextListener {

    final static Logger logger = LogManager.getLogger(Lab09ContextListener.class);

    public void contextInitialized(ServletContextEvent sce) {
        // Create the Flyway instance and point it to the database

        logger.info("start migration config");

        Flyway flyway = Flyway.configure()//.baselineOnMigrate(true)
                .dataSource("jdbc:postgresql://localhost:5432/lab09", "postgres", "post").load();
        logger.info("start migration");

        // Start the migration
        flyway.migrate();
        logger.info("migration done");

        DbWork.getInstance();
    }

    public void contextDestroyed(ServletContextEvent sce) {
        // Закрываем подключение
        DbWork.getInstance().destroy();
    }
}
