package at.fhtw.sampleapp.persistence.repository;

import at.fhtw.sampleapp.model.UserStats;

import java.util.List;
import java.util.Map;
import java.util.Vector;

public interface ScoreboardRepository {
    List<Map<String, Object>> getScoreBoard();
}
