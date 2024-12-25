package ru.itis.crud.repository;

import lombok.RequiredArgsConstructor;
import ru.itis.config.DBConnection;
import ru.itis.crud.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@RequiredArgsConstructor
public class UserRepository{
    //language=sql
    private final static String SQL_GET_NEXT_VAL_USER = "select nextval('users_sequence')";
    //language=sql
    private final static String SQL_INSERT_NEW_USER = "INSERT INTO users(id,name,gender,age,phone,address,login,password) VALUES (?,?,?,?,?,?,?,?)";
    //language=sql
    private final static String SQL_INSERT_NEW_SPORTSMAN = "INSERT INTO sportsman(user_id) VALUES (?)";
    //language=sql
    private final static String SQL_INSERT_NEW_WORKER = "INSERT INTO worker(user_id) VALUES (?)";
    //language=sql
    private final static String SQL_INSERT_NEW_COACH = "INSERT INTO coach(user_id) VALUES (?)";
    //language=sql
    private final static String SQL_GET_BY_LOGIN = "SELECT * FROM users WHERE (login = ?)";
    //language=sql
    private final static String SQL_FIND_ROLE_GET_BY_ID = """
                                         SELECT 'sportsman' as role FROM sportsman where (user_id = ?) UNION ALL 
                                         SELECT 'coach' as role FROM coach where (user_id = ?) union all
                                         SELECT 'worker' as role FROM worker where (user_id = ?) 
                                                            """;

    //language=sql
    private final static String SQL_UPDATE_ENTITY = "UPDATE users SET name = ?, age = ?, phone = ?, address = ? where id = ?";

    private final SportsmanRepository sportsmanRepository = new SportsmanRepository();
    private final CoachRepository coachRepository = new CoachRepository();
    private final WorkerRepository workerRepository = new WorkerRepository();


    public Optional<User> findByLogin(String login) {
        DBConnection dbConnection = DBConnection.getDB();
        User user = null;
        try{
            Connection connection = dbConnection.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_BY_LOGIN);
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setGender(resultSet.getString("gender").charAt(0));
                user.setAge(Integer.parseInt(resultSet.getString("age")));
                user.setPhone(resultSet.getString("phone"));
                user.setAddress(resultSet.getString("address"));

                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
            }
            if (user != null) {
                preparedStatement = connection.prepareStatement(SQL_FIND_ROLE_GET_BY_ID);
                preparedStatement.setLong(1, user.getId());
                preparedStatement.setLong(2, user.getId());
                preparedStatement.setLong(3, user.getId());

                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    if (resultSet.getString("role").equals("sportsman")) {
                        user.setSportsman(sportsmanRepository.findByUserId(user.getId()));
                    }
                    if (resultSet.getString("role").equals("coach")) {
                        user.setCoach(coachRepository.findByUserId(user.getId()));
                    }
                    if (resultSet.getString("role").equals("worker")) {
                        user.setWorker(workerRepository.findByUserId(user.getId()));
                    }
                }
            }

            connection.commit();
            resultSet.close();
            preparedStatement.close();
            dbConnection.releaseConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(user);
    }

    public Long create(User user) {
        DBConnection dbConnection = DBConnection.getDB();
        Long id = null;

        try {
            Connection connection = dbConnection.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_NEXT_VAL_USER);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {id = resultSet.getLong("nextval"); }
            resultSet.close();

            if (id != null){
                preparedStatement = connection.prepareStatement(SQL_INSERT_NEW_USER);
                preparedStatement.setLong(1, id);
                preparedStatement.setString(2, user.getName());
                preparedStatement.setString(3, String.valueOf(user.getGender()));
                preparedStatement.setInt(4, user.getAge());
                preparedStatement.setString(5, user.getPhone());
                preparedStatement.setString(6, user.getAddress());
                preparedStatement.setString(7, user.getLogin());
                preparedStatement.setString(8, user.getPassword());
                preparedStatement.executeUpdate();

                if (user.getSportsman() != null){
                    preparedStatement = connection.prepareStatement(SQL_INSERT_NEW_SPORTSMAN);}
                else if (user.getWorker() != null){
                    preparedStatement = connection.prepareStatement(SQL_INSERT_NEW_WORKER);}
                else if (user.getCoach() != null){
                    preparedStatement = connection.prepareStatement(SQL_INSERT_NEW_COACH);}
                preparedStatement.setLong(1,id);
                preparedStatement.executeUpdate();
            }
            connection.commit();
            preparedStatement.close();
            dbConnection.releaseConnection(connection);

            user.setId(id);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    public void updateById(User user) {
        DBConnection dbConnection = DBConnection.getDB();
        try{
            Connection connection = dbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_ENTITY);
            statement.setString(1, user.getName());
            statement.setLong(2, user.getAge());
            statement.setString(3, user.getPhone());
            statement.setString(4, user.getAddress());
            statement.setLong(5, user.getId());
            statement.executeUpdate();

            statement.close();
            dbConnection.releaseConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
