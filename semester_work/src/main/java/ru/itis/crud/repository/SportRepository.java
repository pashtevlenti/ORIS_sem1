package ru.itis.crud.repository;

import lombok.RequiredArgsConstructor;
import ru.itis.config.DBConnection;
import ru.itis.crud.model.Sport;
import ru.itis.crud.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@RequiredArgsConstructor
public class SportRepository {
    //language=sql
    private final static String SQL_GET_ALL = "SELECT * FROM sport";
    //language=sql
    private final static String SQL_FIND_SPORT_ID_BY_NAME = "SELECT * FROM sport where name = ?";
    //language=sql
    private final static String SQL_FIND_SPORT_BY_ID = "SELECT * FROM sport where id = ?";

    public List<Optional<Sport>> findAll() {
        DBConnection dbConnection = DBConnection.getDB();
        List<Optional<Sport>> sports = new ArrayList<>();
        try{
            Connection connection = dbConnection.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Sport sport = new Sport();
                sport.setId(resultSet.getLong("id"));
                sport.setName(resultSet.getString("name"));
                sports.add(Optional.of(sport));
            }

            connection.commit();
            resultSet.close();
            preparedStatement.close();
            dbConnection.releaseConnection(connection);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return sports;
    }

    public Sport findByName(String name) {
        DBConnection dbConnection = DBConnection.getDB();
        Sport sport = null;
        try {
            Connection connection = dbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_SPORT_ID_BY_NAME);
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                sport = new Sport();
                sport.setId(resultSet.getLong("id"));
                sport.setName(resultSet.getString("name"));
            }

            statement.close();
            resultSet.close();
            dbConnection.releaseConnection(connection);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return sport;
    }

    public Sport findById(Long id) {
        DBConnection dbConnection = DBConnection.getDB();
        Sport sport = null;
        try {
            Connection connection = dbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_SPORT_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                sport = new Sport();
                sport.setId(resultSet.getLong("id"));
                sport.setName(resultSet.getString("name"));
            }

            statement.close();
            resultSet.close();
            dbConnection.releaseConnection(connection);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return sport;
    }
}
