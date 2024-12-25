package ru.itis.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.crud.model.User;

import java.io.IOException;

@WebServlet("/login")
public class LoginPageServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        try {
            if (user != null) {
                if (user.getSportsman() != null){
                    request.getRequestDispatcher("/sportsman").forward(request, response);
                }
                else if (user.getCoach() != null){
                    request.getRequestDispatcher("/coach").forward(request, response);
                }
                else if (user.getWorker() != null){
                    request.getRequestDispatcher("/worker").forward(request, response);
                }
            }
            request.getRequestDispatcher("jsps/login_page.jsp").forward(request, response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }
}
