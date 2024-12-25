package ru.itis.crud.repository;

import ru.itis.config.DBConnection;
import ru.itis.crud.model.ScheduleTraining;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ScheduleTrainingRepository {
    //language=sql
    private static final String SQL_GET_NEXT_VAL = "SELECT nextval('schedule_training_sequence')";
    //language=sql
    private static final String SQL_CREATE_SCHEDULE_TRAINING = "Insert into schedule_training(id,day_of_week,time,coach_id) values (?,?,?,?)";
    //language=sql
    private final static String SQL_CREATE_SCHEDULE_TRAINING_TO_GROUP_SPORTSMAN = "INSERT INTO schedule_training_group_sportsman(schedule_training_id, group_sportsman_id) VALUES (?, ?)";
    //language=sql
    private final static String SQL_DELETE_SCHEDULE_TRAINING_TO_GROUP_SPORTSMAN = "DELETE FROM schedule_training_group_sportsman WHERE schedule_training_id = ? and group_sportsman_id = ?";
    //language=sql
    private final static String SQL_GET_FROM_SCHEDULE_TRAINING_GROUP_SPORTSMAN = "select * FROM schedule_training_group_sportsman WHERE schedule_training_id = ? and group_sportsman_id = ?";
    //language=sql
    private final static String SQL_GET_ID_BY_DAY_AND_TIME_AND_COACH_ID = "select * FROM schedule_training WHERE day_of_week = ? and schedule_training.time = ? and coach_id = ?";
    //language=sql
    private static final String SQL_GET_TRAININGS_BY_GROUP_ID = """
                    select st.id, st.day_of_week, st.time, st.coach_id 
                    from schedule_training st 
                    INNER JOIN schedule_training_group_sportsman stgs 
                        on st.id = stgs.schedule_training_id
                    INNER JOIN group_sportsman grs
                        on grs.id = stgs.group_sportsman_id
                    where grs.id = ?
""";
    public List<ScheduleTraining> findByGroupId(Long id) {
        DBConnection dbConnection = DBConnection.getDB();
        List<ScheduleTraining> trainings = new ArrayList<>();
        try{
            Connection connection = dbConnection.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_TRAININGS_BY_GROUP_ID);
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ScheduleTraining training = new ScheduleTraining();
                training.setId(resultSet.getLong("id"));
                training.setDayOfWeek(resultSet.getString("day_of_week"));
                training.setTime(resultSet.getString("time"));
                training.setCoachId(resultSet.getLong("coach_id"));

                trainings.add(training);
            }

            connection.commit();
            resultSet.close();
            preparedStatement.close();
            dbConnection.releaseConnection(connection);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return trainings;
    }

    public Long create(ScheduleTraining scheduleTraining) {
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
            scheduleTraining.setId(id);

            preparedStatement = connection.prepareStatement(SQL_CREATE_SCHEDULE_TRAINING);
            preparedStatement.setLong(1, scheduleTraining.getId());
            preparedStatement.setString(2, scheduleTraining.getDayOfWeek());
            preparedStatement.setString(3, scheduleTraining.getTime());
            preparedStatement.setLong(4, scheduleTraining.getCoachId());
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

    public void createScheduleTrainingGroupSportsman(Long schedule_training_id, Long group_sportsman_id) {
        DBConnection dbConnection = DBConnection.getDB();
        try {
            Connection connection = dbConnection.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREATE_SCHEDULE_TRAINING_TO_GROUP_SPORTSMAN);
            preparedStatement.setLong(1, schedule_training_id);
            preparedStatement.setLong(2, group_sportsman_id);
            preparedStatement.executeUpdate();

            connection.commit();
            preparedStatement.close();
            dbConnection.releaseConnection(connection);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void deleteTrainingFromGroup(Long trainingId, Long sportsmanId) {
        DBConnection dbConnection = DBConnection.getDB();
        try {
            Connection connection = dbConnection.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_SCHEDULE_TRAINING_TO_GROUP_SPORTSMAN);
            preparedStatement.setLong(1, trainingId);
            preparedStatement.setLong(2, sportsmanId);
            preparedStatement.executeUpdate();

            connection.commit();
            preparedStatement.close();
            dbConnection.releaseConnection(connection);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Long findByDayOfWeekAndTimeAndCoachId(String day, String time, Long id) {
        Long result = -1L;
        DBConnection dbConnection = DBConnection.getDB();
        try {
            Connection connection = dbConnection.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ID_BY_DAY_AND_TIME_AND_COACH_ID);
            preparedStatement.setString(1, day);
            preparedStatement.setString(2, time);
            preparedStatement.setLong(3, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result = resultSet.getLong("id");
            }
            connection.commit();
            preparedStatement.close();
            dbConnection.releaseConnection(connection);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public boolean findByTrainingIdAndGroupId(Long trainingId, Long groupId) {
        boolean flag = false;
        DBConnection dbConnection = DBConnection.getDB();
        try {
            Connection connection = dbConnection.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_FROM_SCHEDULE_TRAINING_GROUP_SPORTSMAN);
            preparedStatement.setLong(1, trainingId);
            preparedStatement.setLong(2, groupId);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                flag = true;
            }
            connection.commit();
            preparedStatement.close();
            dbConnection.releaseConnection(connection);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return flag;
    }
}
