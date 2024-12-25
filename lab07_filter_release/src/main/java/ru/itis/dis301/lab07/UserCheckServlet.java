package ru.itis.dis301.lab07;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.dis301.lab07.db.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

@WebServlet("/usercheck")
public class UserCheckServlet extends HttpServlet {

    //language=sql
    private static final String SQL_GET_USERS = "SELECT * FROM users WHERE (login = ?) and (password = ?)";
    private static final String PASSWORD = "password";
    private static final String LOGIN = "login";

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try{

            Map<UUID,Cookie> authentificationData =
                    (Map<UUID,Cookie>) request.getServletContext().getAttribute("USER_SESSIONS");
            DBConnection dbConnection = DBConnection.getDB();
            Connection connection = dbConnection.getConnection();

            String login = request.getParameter(LOGIN);
            String password = request.getParameter(PASSWORD);

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_USERS);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()
                    && login.equals(resultSet.getString(LOGIN))
                    && password.equals((resultSet.getString(PASSWORD)))) {
                request.getSession().setAttribute(LOGIN, login);
                UUID uuid = UUID.randomUUID();
                authentificationData.put(uuid, (Cookie) request.getSession().getAttribute(LOGIN));
                response.sendRedirect("index.ftl");
            } else {
                request.getRequestDispatcher("login.ftl").forward(request, response);
            }

            preparedStatement.close();
            resultSet.close();
            dbConnection.releaseConnection(connection);


        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

}
