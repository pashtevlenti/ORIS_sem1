package ru.itis.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.itis.crud.model.Coach;
import ru.itis.crud.model.User;
import ru.itis.crud.service.CoachService;
import ru.itis.crud.service.SportService;

import java.io.IOException;
import java.util.List;

@WebServlet("/coaches")
public class CoachesServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        CoachService coachService = (CoachService) getServletContext().getAttribute("coachService");
        try{
            User user = (User) request.getSession().getAttribute("user");
            List<User> usersAvailable = coachService.getAvailableCoach(user.getSportsman().getId());
            List<User> users = coachService.getSportsmanCoaches(user.getSportsman().getId());

            request.setAttribute("availableCoaches", usersAvailable);
            request.setAttribute("coaches", users);

            request.getRequestDispatcher("jsps/coaches.jsp").forward(request, response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }
}
