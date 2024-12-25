package ru.itis.dis301.lab10.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.flywaydb.core.Flyway;
import ru.itis.dis301.lab10.repository.DbWork;

@WebListener
public class Lab10ContextListener implements ServletContextListener {

    final static Logger logger = LogManager.getLogger(Lab10ContextListener.class);

    public void contextInitialized(ServletContextEvent sce) {
        logger.info("contextInitialized");
        DbWork.getInstance();
    }

    public void contextDestroyed(ServletContextEvent sce) {
        // Закрываем подключение
        DbWork.getInstance().destroy();
    }
}
