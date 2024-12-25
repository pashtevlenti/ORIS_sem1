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

@WebServlet("/coach")
public class CoachServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        SportService sportService = (SportService) request.getServletContext().getAttribute("sportService");
        try{
            request.setAttribute("sport", sportService.getById(user.getCoach().getSportId()));

            request.getRequestDispatcher("jsps/coach.jsp").forward(request, response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }
}
