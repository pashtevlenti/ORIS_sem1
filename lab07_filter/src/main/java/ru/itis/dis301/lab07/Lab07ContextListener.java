package ru.itis.dis301.lab07;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@WebListener
public class Lab07ContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent event) {
        DBConnection.getDB();
        Map<UUID, String> userSessions = new HashMap<>();
        event.getServletContext().setAttribute("USER_SESSIONS", userSessions);
    }

    public void contextDestroyed(ServletContextEvent event) {
        DBConnection.getDB().closeAllConnections();
        Map<UUID, String> userSessions = (Map<UUID, String>) event.getServletContext().getAttribute("USER_SESSIONS");
        userSessions.clear();
    }



}
