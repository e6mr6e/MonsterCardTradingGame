package at.fhtw.sampleapp.persistence.repository;

import at.fhtw.sampleapp.model.User;
import at.fhtw.sampleapp.model.UserStats;
import at.fhtw.sampleapp.persistence.DataAccessException;
import at.fhtw.sampleapp.persistence.UnitOfWork;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class StatsRepositoryImpl implements StatsRepository {    private UnitOfWork unitOfWork;

    public StatsRepositoryImpl(UnitOfWork unitOfWork)
    {
        this.unitOfWork = unitOfWork;
    }

    public Map<String, Object> getStats(String username) {
        Map<String, Object> stats = new HashMap<>();
        try {
            String sql = "SELECT * FROM stats WHERE username = ?";
            PreparedStatement preparedStatement = this.unitOfWork.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();

                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Object columnValue = resultSet.getObject(i);
                    stats.put(columnName, columnValue);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Unable to retrieve stats", e);
        }
        return stats;
    }

    // holt die stats aus der db


}
