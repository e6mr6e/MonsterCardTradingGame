package at.fhtw.sampleapp.persistence.repository;

import at.fhtw.sampleapp.persistence.DataAccessException;
import at.fhtw.sampleapp.persistence.UnitOfWork;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScoreboardRepositoryImpl implements ScoreboardRepository {
    private UnitOfWork unitOfWork;

    public ScoreboardRepositoryImpl(UnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    public List<Map<String, Object>> getScoreBoard() {
        List<Map<String, Object>> scoreboard = new ArrayList<>();
        try {
            String sql = "SELECT * FROM stats ORDER BY ello DESC";
            PreparedStatement preparedStatement = this.unitOfWork.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Map<String, Object> userStats = new HashMap<>();

                int losses = resultSet.getInt("loses");
                int wins = resultSet.getInt("wins");
                int mmr = resultSet.getInt("ello");
                String name = resultSet.getString("username");

                userStats.put("name", name);
                userStats.put("mmr", mmr);
                userStats.put("losses", losses);
                userStats.put("wins", wins);

                scoreboard.add(userStats);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Unable to retrieve scoreboard", e);
        }
        return scoreboard;
    }

    public static void main(String[] args) {
        UnitOfWork unitOfWork = new UnitOfWork();
        ScoreboardRepositoryImpl scoreboardRepository = new ScoreboardRepositoryImpl(unitOfWork);

        List<Map<String, Object>> scoreboard = scoreboardRepository.getScoreBoard();

        for (Map<String, Object> userStats : scoreboard) {
            System.out.println(userStats);
        }
    }
}