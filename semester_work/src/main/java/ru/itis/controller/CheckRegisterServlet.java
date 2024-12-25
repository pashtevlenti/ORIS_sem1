package ru.itis.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.itis.crud.model.Coach;
import ru.itis.crud.model.Sportsman;
import ru.itis.crud.model.User;
import ru.itis.crud.model.Worker;
import ru.itis.crud.repository.UserRepository;
import ru.itis.crud.service.UserService;

import java.io.IOException;
import java.util.Optional;


@WebServlet("/check-register")
public class CheckRegisterServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(CheckRegisterServlet.class);

    //language=sql
    private static final String SQL_GET_USERS = "SELECT * FROM users WHERE (login = ?)";
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String CONFIRM_PASSWORD = "confirm_password";

    private final UserService userService = new UserService(new UserRepository());

    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        try{
            Optional<User> userCheck = userService.getByLogin(request.getParameter(LOGIN));
            BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
            logger.info(userCheck);
            if (userCheck.isPresent()) {
                request.setAttribute("errorMessage", "Логин уже существует. Попробуйте другой.");
                request.getRequestDispatcher("/register").forward(request, response);
            }
            else if (!bCrypt.matches(request.getParameter(CONFIRM_PASSWORD),
                    bCrypt.encode(request.getParameter(PASSWORD)))){
                request.setAttribute("errorMessage", "Пароли не совпадают");
                request.getRequestDispatcher("/register").forward(request, response);
            }
            else {

                String role = request.getParameter("role");
                Sportsman sportsman = null;
                Worker worker = null;
                Coach coach = null;

                if (role.equals("sportsman")) {
                    sportsman = new Sportsman();
                }
                if (role.equals("worker")) {
                    worker = new Worker();
                }
                if (role.equals("coach")) {
                    coach = new Coach();
                }

                User user = new User();

                user.setLogin(request.getParameter("login"));
                user.setPassword(bCrypt.encode(request.getParameter("password")));
                user.setName(request.getParameter("name"));
                user.setAddress(request.getParameter("address"));
                user.setPhone(request.getParameter("phone"));
                user.setGender(request.getParameter("gender").charAt(0));
                user.setAge(Integer.parseInt(request.getParameter("age")));

                user.setSportsman(sportsman);
                user.setWorker(worker);
                user.setCoach(coach);

                userService.create(user);
                response.sendRedirect(request.getContextPath() + "/login");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }


    }

}
