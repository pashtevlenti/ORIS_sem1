package ru.itis.log;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.itis.dis301.lab08.controller.IndexPageServlet;

import java.io.IOException;

@WebServlet("/login")
public class LoginPageServlet extends HttpServlet {

    final static Logger logger = LogManager.getLogger(LoginPageServlet.class);

    public void doGet(HttpServletRequest request, HttpServletResponse response) {

        logger.info("/login");

        try {
            request.getRequestDispatcher("login.ftl").forward(request, response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }
}
