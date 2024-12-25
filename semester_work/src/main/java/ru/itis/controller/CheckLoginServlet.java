package ru.itis.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.itis.crud.model.User;
import ru.itis.crud.repository.UserRepository;
import ru.itis.crud.service.UserService;


import java.io.IOException;
import java.util.Optional;

@WebServlet("/check-login")
public class CheckLoginServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(CheckLoginServlet.class);

    private static final UserService userService = new UserService(new UserRepository());

    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    //language=

    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            Optional<User> user = userService.getByLogin(request.getParameter(LOGIN));
            logger.info(user);
            BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
            if (user.isPresent()) {
                if (!bCrypt.matches(request.getParameter(PASSWORD), user.get().getPassword())) {
                    request.setAttribute("errorMessage", "Неверный логин или пароль");
                    request.getRequestDispatcher("/login").forward(request, response);
                }
                else{
                    HttpSession session = request.getSession();
                    session.setAttribute("user", user.get());
                    logger.info(user.get());
                    logger.info(session);
                    if (user.get().getSportsman() != null){
                        request.getRequestDispatcher("/sportsman").forward(request, response);
                    }
                    else if (user.get().getCoach() != null){
                        request.getRequestDispatcher("/coach").forward(request, response);
                    }
                    else if (user.get().getWorker() != null){
                        request.getRequestDispatcher("/worker").forward(request, response);
                    }
                }
            }
            else {
                request.setAttribute("errorMessage", "Неверный логин или пароль");
                request.getRequestDispatcher("/login").forward(request, response);
            }
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
