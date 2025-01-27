package ru.itis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

class UserRepository {

        public boolean save(User user) {
            DBConnection dbConnection = DBConnection.getDB();

            try {
                Connection connection = dbConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("insert into account(name,email,age) values(?,?,?)");

                preparedStatement.setString(1, user.getName());
                preparedStatement.setString(2, user.getEmail());
                preparedStatement.setInt(3, user.getAge());
                preparedStatement.executeUpdate();
                return true;

            } catch (SQLException e) {
                e.printStackTrace();
                return false;

            }
        }
    }