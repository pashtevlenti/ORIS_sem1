package ru.itis.crud.repository;

import lombok.RequiredArgsConstructor;
import ru.itis.config.DBConnection;
import ru.itis.crud.model.Sportsman;
import ru.itis.crud.model.Worker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;


@RequiredArgsConstructor
public class WorkerRepository {
    //language=sql
    private static final String SQL_GET_BY_USER_ID = "SELECT * FROM worker WHERE (user_id= ?)";
    //language=sql
    private final static String SQL_UPDATE_ENTITY = "UPDATE worker SET post = ? where (user_id = ?)";

    public Worker findByUserId(Long id) {
        DBConnection dbConnection = DBConnection.getDB();
        Worker worker = new Worker();
        try{
            Connection connection = dbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_GET_BY_USER_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                worker.setPost(resultSet.getString("post"));
                worker.setUserId(resultSet.getLong("user_id"));
                worker.setId(resultSet.getLong("id"));
            }
            resultSet.close();
            statement.close();
            dbConnection.releaseConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return worker;
    }


    public Set<Optional<Worker>> findAll() {
        return Set.of();
    }


    public boolean deleteById(Long id) {
        return false;
    }

    public void updateByUserId(Long id, Worker worker) {
        DBConnection dbConnection = DBConnection.getDB();
        try{
            Connection connection = dbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_ENTITY);
            statement.setString(1,  worker.getPost());
            statement.setLong(2, id);
            statement.executeUpdate();

            statement.close();
            dbConnection.releaseConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
