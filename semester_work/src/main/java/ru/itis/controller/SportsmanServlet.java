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

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/sportsman")
public class SportsmanServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(SportsmanServlet.class);

    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try{
            request.getRequestDispatcher("jsps/sportsman.jsp").forward(request, response);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }
}
