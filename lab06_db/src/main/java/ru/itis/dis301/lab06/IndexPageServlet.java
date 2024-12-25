package ru.itis.dis301.lab06;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.dis301.lab06.model.User;

import java.io.IOException;
import java.io.Writer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("")
public class IndexPageServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) {

        try {

            DBConnection dbConnection = DBConnection.getDB();
            Connection connection = dbConnection.getConnection();

            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM КоммерческиеОрганизации");

            ResultSet resultSet = preparedStatement.executeQuery();

            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(
                        new User(resultSet.getLong("id_Организации"),
                            resultSet.getString("Название")));
            }

            request.setAttribute("users", users);
            request.setAttribute("title_page", "Пользователи БД");

            resultSet.close();
            preparedStatement.close();
            dbConnection.releaseConnection(connection);



            request.getRequestDispatcher("index.ftl").forward(request, response);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }


    }

}
