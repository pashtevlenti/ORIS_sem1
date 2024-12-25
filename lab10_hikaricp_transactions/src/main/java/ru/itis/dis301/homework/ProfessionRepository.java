package ru.itis.dis301.homework;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProfessionRepository {
    final static Logger logger = LogManager.getLogger(ProfessionRepository.class);

    private DbWork db = DbWork.getInstance();

    public List<Profession> find25(String search, int count) {
        List<Profession> professions = new ArrayList<>();

        try (Connection connection = db.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    """
                        select id, name from dict_profession
                        where lower(name) like ? ORDER BY id LIMIT 25 OFFSET ?
                        """);

            String str = "%" + search + "%";
            statement.setString(1, str.toLowerCase());
            statement.setInt(2, count);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Profession profession = new Profession(
                        resultSet.getLong("id"),
                        resultSet.getString("name")
                );
                professions.add(profession);
            }

            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return professions;
    }
}
