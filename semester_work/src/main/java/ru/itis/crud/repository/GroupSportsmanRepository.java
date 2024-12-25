package ru.itis.crud.repository;

import lombok.RequiredArgsConstructor;
import ru.itis.config.DBConnection;
import ru.itis.crud.model.GroupSportsman;
import ru.itis.crud.model.ScheduleTraining;
import ru.itis.crud.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class GroupSportsmanRepository {
    //language=sql
    private static final String SQL_GET_ALL_BY_COACH_ID = "SELECT * FROM group_sportsman where coach_id = ?";
    private static final String SQL_GET_ALL_BY_SPORTSMAN_ID = """
                                        select gs.id,gs.group_name,gs.coach_id from sportsman s
                                        join group_sportsman_sportsman gss on s.id = gss.sportsman_id
                                        join group_sportsman gs on gss.group_sportsman_id = gs.id
                                        where s.id = ?
                                        """;

    //language=sql
    private static final String SQL_CREATE_GROUP_SPORTSMAN = "Insert into group_sportsman(id,group_name,coach_id) values (?,?,?)";
    //language=sql

    //language=sql
    private static final String SQL_GET_NEXT_VAL = "SELECT nextval('group_sportsman_sequence')";
    //language=sql
    private final static String SQL_CREATE_GROUP_SPORTSMAN_TO_SPORTSMAN = "INSERT INTO group_sportsman_sportsman(sportsman_id, group_sportsman_id) VALUES (?, ?)";
    //language=sql
    private final static String SQL_DELETE_GROUP_SPORTSMAN_TO_SPORTSMAN = "DELETE FROM group_sportsman_sportsman WHERE group_sportsman_id = ? and sportsman_id = ?";
    //language=sql
    private final static String SQL_DELETE_GROUP_BY_ID = "DELETE FROM group_sportsman WHERE id = ?";

    private final SportsmanRepository sportsmanRepository = new SportsmanRepository();
    private final CoachRepository coachRepository = new CoachRepository();
    private final ScheduleTrainingRepository trainingRepository = new ScheduleTrainingRepository();

    public List<Optional<GroupSportsman>> findByCoachId(Long coachId) {
        DBConnection dbConnection = DBConnection.getDB();
        List<Optional<GroupSportsman>> groups = new ArrayList<>();
        try{
            Connection connection = dbConnection.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ALL_BY_COACH_ID);
            preparedStatement.setLong(1, coachId);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                GroupSportsman groupSportsman = new GroupSportsman();
                groupSportsman.setGroupName(resultSet.getString("group_name"));
                groupSportsman.setId(resultSet.getLong("id"));

                List<User> sportsmanList = sportsmanRepository.findByGroupId(groupSportsman.getId());
                List<ScheduleTraining> trainings = trainingRepository.findByGroupId(groupSportsman.getId());
                groupSportsman.setSportsmen(sportsmanList);
                groupSportsman.setTrainings(trainings);

                groups.add(Optional.of(groupSportsman));
            }

            connection.commit();
            resultSet.close();
            preparedStatement.close();
            dbConnection.releaseConnection(connection);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return groups;
    }
    public Long create(GroupSportsman groupSportsman) {
        DBConnection dbConnection = DBConnection.getDB();
        Long id = null;
        try{
            Connection connection = dbConnection.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_NEXT_VAL);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                id = resultSet.getLong("nextval");
            }
            groupSportsman.setId(id);

            preparedStatement = connection.prepareStatement(SQL_CREATE_GROUP_SPORTSMAN);
            preparedStatement.setLong(1, groupSportsman.getId());
            preparedStatement.setString(2, groupSportsman.getGroupName());
            preparedStatement.setLong(3, groupSportsman.getCoachId());
            preparedStatement.executeUpdate();

            connection.commit();
            resultSet.close();
            preparedStatement.close();
            dbConnection.releaseConnection(connection);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    public void createGroupSportsmanToSportsman(Long l, Long id) {
        DBConnection dbConnection = DBConnection.getDB();
        try {
            Connection connection = dbConnection.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREATE_GROUP_SPORTSMAN_TO_SPORTSMAN);
            preparedStatement.setLong(1, l);
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();

            connection.commit();
            preparedStatement.close();
            dbConnection.releaseConnection(connection);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Optional<GroupSportsman>> findBySportsmanId(Long sportsmanId) {
        DBConnection dbConnection = DBConnection.getDB();
        List<Optional<GroupSportsman>> groups = new ArrayList<>();
        try{
            Connection connection = dbConnection.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ALL_BY_SPORTSMAN_ID);
            preparedStatement.setLong(1, sportsmanId);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                GroupSportsman groupSportsman = new GroupSportsman();
                groupSportsman.setGroupName(resultSet.getString("group_name"));
                groupSportsman.setId(resultSet.getLong("id"));
                groupSportsman.setCoachId(resultSet.getLong("coach_id"));

                List<User> sportsmanList = sportsmanRepository.findByGroupId(groupSportsman.getId());
                List<ScheduleTraining> trainings = trainingRepository.findByGroupId(groupSportsman.getId());
                User coach = coachRepository.findById(groupSportsman.getCoachId());
                groupSportsman.setSportsmen(sportsmanList);
                groupSportsman.setTrainings(trainings);
                groupSportsman.setCoach(coach);

                groups.add(Optional.of(groupSportsman));
            }

            connection.commit();
            resultSet.close();
            preparedStatement.close();
            dbConnection.releaseConnection(connection);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return groups;
    }

    public void deleteSportsmanFromGroup(Long groupId, Long sportsmanId) {
        DBConnection dbConnection = DBConnection.getDB();
        try {
            Connection connection = dbConnection.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_GROUP_SPORTSMAN_TO_SPORTSMAN);
            preparedStatement.setLong(1, groupId);
            preparedStatement.setLong(2, sportsmanId);
            preparedStatement.executeUpdate();

            connection.commit();
            preparedStatement.close();
            dbConnection.releaseConnection(connection);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteGroupById(Long groupId) {
        DBConnection dbConnection = DBConnection.getDB();
        try {
            Connection connection = dbConnection.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_GROUP_BY_ID);
            preparedStatement.setLong(1, groupId);
            preparedStatement.executeUpdate();

            connection.commit();
            preparedStatement.close();
            dbConnection.releaseConnection(connection);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
