package ru.itis.crud.repository;

import lombok.RequiredArgsConstructor;
import ru.itis.config.DBConnection;
import ru.itis.crud.model.Sportsman;
import ru.itis.crud.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@RequiredArgsConstructor
public class SportsmanRepository{
    //language=sql
    private static final String SQL_GET_BY_USER_ID = "SELECT * FROM sportsman WHERE (user_id= ?)";
    //language=sql
    private final static String SQL_UPDATE_ENTITY = "UPDATE sportsman SET rank = ? where (user_id = ?)";
    //language=sql
    private static final String SQL_GET_BY_GROUP_ID = """
                    SELECT users.name, sportsman.rank, gs.group_name, sportsman.id FROM sportsman
                        JOIN users on sportsman.user_id = users.id
                        JOIN group_sportsman_sportsman gss on sportsman.id = gss.sportsman_id
                        JOIN group_sportsman gs on gss.group_sportsman_id = gs.id 
                             WHERE (gss.group_sportsman_id = ?)
""";
    //language=sql
    private static final String SQL_GET_SPORTSMEN_BY_COACH = """
                        SELECT u.name AS athlete_name, s.id AS sportsman_id
                        FROM users u
                        JOIN sportsman s ON u.id = s.user_id
                        JOIN sportsman_coach sc ON s.id = sc.sportsman_id
                        WHERE sc.coach_id = ?;
    """;
    //language=sql
    private static final String SQL_GET_SPORTSMEN_BY_COACH_AND_GROUP_ID = """
                SELECT DISTINCT s.id AS sportsman_id, u.name AS sportsman_name
                FROM sportsman s
                JOIN users u ON s.user_id = u.id
                JOIN sportsman_coach sc ON s.id = sc.sportsman_id
                LEFT JOIN group_sportsman_sportsman gss ON s.id = gss.sportsman_id AND gss.group_sportsman_id = ?
                WHERE sc.coach_id = ?
                  AND gss.sportsman_id IS NULL;
    """;

    public Sportsman findByUserId(Long id) {
        DBConnection dbConnection = DBConnection.getDB();
        Sportsman sportsman = new Sportsman();
        try{
            Connection connection = dbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_GET_BY_USER_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                sportsman.setId(resultSet.getLong("id"));
                sportsman.setRank(resultSet.getString("rank"));
                sportsman.setUserId(resultSet.getLong("user_id"));
            }
            resultSet.close();
            statement.close();
            dbConnection.releaseConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return sportsman;
    }

    public List<User> findByGroupId(Long groupId) {
        DBConnection dbConnection = DBConnection.getDB();
        List<User> sportsmanList = new ArrayList<>();
        try{
            Connection connection = dbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_GET_BY_GROUP_ID);
            statement.setLong(1, groupId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setName(resultSet.getString("name"));
                Sportsman sportsman = new Sportsman();
                sportsman.setRank(resultSet.getString("rank"));
                sportsman.setId(resultSet.getLong("id"));
                user.setSportsman(sportsman);

                sportsmanList.add(user);
            }
            resultSet.close();
            statement.close();
            dbConnection.releaseConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return sportsmanList;
    }


    public Set<Optional<Sportsman>> findAll() {
        return Set.of();
    }


    public Long create() {
        return 0L;
    }

    public boolean deleteById(Long id) {
        return false;
    }

    public void updateByUserId(Long id, Sportsman sportsman) {
        DBConnection dbConnection = DBConnection.getDB();
        try{
            Connection connection = dbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_ENTITY);
            statement.setString(1, sportsman.getRank());
            statement.setLong(2, id);
            statement.executeUpdate();

            statement.close();
            dbConnection.releaseConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> findSportsmenByCoach(Long coachId) {
        DBConnection dbConnection = DBConnection.getDB();
        List<User> users = new ArrayList<>();
        try{
            Connection connection = dbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_GET_SPORTSMEN_BY_COACH);
            statement.setLong(1, coachId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                User user = new User();
                user.setName(resultSet.getString("athlete_name"));
                Sportsman sportsman = new Sportsman();
                sportsman.setId(resultSet.getLong("sportsman_id"));
                user.setSportsman(sportsman);

                users.add(user);
            }

            statement.close();
            dbConnection.releaseConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public List<User> findSportsmenByCoachAndGroupId(Long coachId, Long groupId) {
        DBConnection dbConnection = DBConnection.getDB();
        List<User> users = new ArrayList<>();
        try{
            Connection connection = dbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_GET_SPORTSMEN_BY_COACH_AND_GROUP_ID);
            statement.setLong(1, groupId);
            statement.setLong(2, coachId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                User user = new User();
                user.setName(resultSet.getString("sportsman_name"));
                Sportsman sportsman = new Sportsman();
                sportsman.setId(resultSet.getLong("sportsman_id"));
                user.setSportsman(sportsman);

                users.add(user);
            }

            statement.close();
            dbConnection.releaseConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }
}
