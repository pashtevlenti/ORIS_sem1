package ru.itis.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import ru.itis.config.DBConnection;
import ru.itis.crud.repository.*;
import ru.itis.crud.service.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@WebListener
public class SemesterWorkContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent event) {
        DBConnection.getDB();
        event.getServletContext().setAttribute("userService",new UserService(new UserRepository()));
        event.getServletContext().setAttribute("sportService",new SportService(new SportRepository()));
        event.getServletContext().setAttribute("coachService",new CoachService(new CoachRepository()));
        event.getServletContext().setAttribute("sportsmanService",new SportsmanService(new SportsmanRepository()));
        event.getServletContext().setAttribute("workerService",new WorkerService(new WorkerRepository()));
        event.getServletContext().setAttribute("groupSportsmanService",new GroupSportsmanService(new GroupSportsmanRepository()));
        event.getServletContext().setAttribute("trainingService",new ScheduleTrainingService(new ScheduleTrainingRepository()));

        Map<UUID, String> userSessions = new HashMap<>();
        event.getServletContext().setAttribute("USER_SESSIONS", userSessions);
    }

    public void contextDestroyed(ServletContextEvent event) {
        DBConnection.getDB().destroy();
        Map<UUID, String> userSessions = (Map<UUID, String>) event.getServletContext().getAttribute("USER_SESSIONS");
        userSessions.clear();
    }



}
