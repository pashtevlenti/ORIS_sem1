package ru.itis.crud.repository;

import lombok.RequiredArgsConstructor;
import ru.itis.config.DBConnection;
import ru.itis.crud.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
public class CoachRepository {
    //language=sql
    private static final String SQL_GET_BY_USER_ID = "SELECT * FROM coach WHERE (user_id = ?)";
    //language=sql
    private static final String SQL_GET_BY_ID = "SELECT * FROM coach join users on coach.user_id = users.id  WHERE (coach.id = ?)";
    //language=sql
    private static final String SQL_GET_GROUP_BY_COACH_ID = "SELECT * FROM group_sportsman WHERE (coach_id = ?)";
    //language=sql
    private final static String SQL_UPDATE_ENTITY = "UPDATE coach SET rank = ?, sport_id = ? where (user_id = ?)";
    //language=sql
    private final static String SQL_GET_AVAILABLE_COACHES = """
        SELECT c.id, c.user_id, c.sport_id, c.rank, s.name AS sport_name, u.name AS coach_name
        FROM coach c
        JOIN sport s ON c.sport_id = s.id
        JOIN users u ON c.user_id = u.id
        WHERE c.sport_id NOT IN (
            SELECT sport.id
            FROM sportsman_coach sc
            JOIN coach ON sc.coach_id = coach.id
            JOIN sport ON coach.sport_id = sport.id
            WHERE sc.sportsman_id = ?)
    """;
    //language=sql
    private final static String SQL_GET_SPORTSMAN_COACHES = """
        SELECT c.id, c.user_id, c.sport_id, c.rank, s.name AS sport_name, u.name AS coach_name
        FROM coach c
        JOIN sport s ON c.sport_id = s.id
        JOIN users u ON c.user_id = u.id
        WHERE c.id IN (
            SELECT sc.coach_id
            FROM sportsman_coach sc
            WHERE sc.sportsman_id = ?)
    """;
    //language=sql
    private final static String SQL_ASSIGN_COACH_TO_SPORTSMAN = "INSERT INTO sportsman_coach(sportsman_id, coach_id) VALUES (?, ?)";
    //language=sql
    private final static String SQL_DELETE_COACH_TO_SPORTSMAN = "delete from sportsman_coach where sportsman_id = ? and coach_id = ?";

    SportRepository sportRepository = new SportRepository();

    public Coach findByUserId(Long id) {
        DBConnection dbConnection = DBConnection.getDB();
        Coach coach = new Coach();
        try{
            Connection connection = dbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_GET_BY_USER_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                coach.setId(resultSet.getLong("id"));
                coach.setRank(resultSet.getString("rank"));
                coach.setUserId(resultSet.getLong("user_id"));
                coach.setSportId(resultSet.getLong("sport_id"));
            }
            resultSet.close();
            statement.close();
            dbConnection.releaseConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return coach;
    }
    public List<User> findCoaches(Long sportsmanId) {
        DBConnection dbConnection = DBConnection.getDB();
        List<User> users = new ArrayList<>();
        try{
            Connection connection = dbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_GET_SPORTSMAN_COACHES);
            statement.setLong(1, sportsmanId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Coach coach = new Coach();
                coach.setId(resultSet.getLong("id"));
                coach.setRank(resultSet.getString("rank"));
                coach.setUserId(resultSet.getLong("user_id"));
                coach.setSportId(resultSet.getLong("sport_id"));

                Sport sport = new Sport();
                sport.setId(resultSet.getLong("sport_id"));
                sport.setName(resultSet.getString("sport_name"));

                User user = new User();
                user.setName(resultSet.getString("coach_name"));

                coach.setSport(sport);
                user.setCoach(coach);

                users.add(user);
            }

            resultSet.close();
            statement.close();
            dbConnection.releaseConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public List<User> findAvailableCoaches(Long sportsmanId) {
        DBConnection dbConnection = DBConnection.getDB();
        List<User> users = new ArrayList<>();
        try{
            Connection connection = dbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_GET_AVAILABLE_COACHES);
            statement.setLong(1, sportsmanId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Coach coach = new Coach();
                coach.setId(resultSet.getLong("id"));
                coach.setRank(resultSet.getString("rank"));
                coach.setUserId(resultSet.getLong("user_id"));
                coach.setSportId(resultSet.getLong("sport_id"));

                Sport sport = new Sport();
                sport.setId(resultSet.getLong("sport_id"));
                sport.setName(resultSet.getString("sport_name"));

                User user = new User();
                user.setName(resultSet.getString("coach_name"));

                coach.setSport(sport);
                user.setCoach(coach);

                users.add(user);
            }

            resultSet.close();
            statement.close();
            dbConnection.releaseConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }


    public void updateByUserId(Long id, Coach coach, String sportName) {
        DBConnection dbConnection = DBConnection.getDB();
        try{
            Connection connection = dbConnection.getConnection();
            Sport sport = sportRepository.findByName(sportName);
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_ENTITY);
            statement.setString(1, coach.getRank());
            statement.setLong(2, sport.getId());
            statement.setLong(3, id);

            statement.executeUpdate();

            statement.close();
            dbConnection.releaseConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteCoachToSportsman(Long sportsmanId, Long i) {
        DBConnection dbConnection = DBConnection.getDB();

        try {
            Connection connection = dbConnection.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_COACH_TO_SPORTSMAN);
            preparedStatement.setLong(1, sportsmanId);
            preparedStatement.setLong(2, i);
            preparedStatement.executeUpdate();

            connection.commit();
            preparedStatement.close();
            dbConnection.releaseConnection(connection);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void assignCoachToSportsman(Long sportsmanId, Long i) {
        DBConnection dbConnection = DBConnection.getDB();

        try {
            Connection connection = dbConnection.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_ASSIGN_COACH_TO_SPORTSMAN);
            preparedStatement.setLong(1, sportsmanId);
            preparedStatement.setLong(2, i);
            preparedStatement.executeUpdate();

            connection.commit();
            preparedStatement.close();
            dbConnection.releaseConnection(connection);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public User findById(Long coachId) {
        DBConnection dbConnection = DBConnection.getDB();
        User user = new User();
        try{
            Connection connection = dbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_GET_BY_ID);
            statement.setLong(1, coachId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Coach coach = new Coach();
                coach.setId(resultSet.getLong("id"));
                coach.setRank(resultSet.getString("rank"));
                coach.setUserId(resultSet.getLong("user_id"));
                coach.setSportId(resultSet.getLong("sport_id"));

                user.setName(resultSet.getString("name"));
                user.setCoach(coach);
            }
            resultSet.close();
            statement.close();
            dbConnection.releaseConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public List<GroupSportsman> findGroupByCoachId(Long coachId) {
        DBConnection dbConnection = DBConnection.getDB();
        List<GroupSportsman> groupSportsmen = new ArrayList<>();
        try{
            Connection connection = dbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_GET_GROUP_BY_COACH_ID);
            statement.setLong(1, coachId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                GroupSportsman groupSportsman = new GroupSportsman();
                groupSportsman.setId(resultSet.getLong("id"));
                groupSportsman.setCoach(findById(resultSet.getLong("coach_id")));
                groupSportsman.setGroupName(resultSet.getString("group_name"));

                groupSportsmen.add(groupSportsman);
            }
            resultSet.close();
            statement.close();
            dbConnection.releaseConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return groupSportsmen;
    }
}
