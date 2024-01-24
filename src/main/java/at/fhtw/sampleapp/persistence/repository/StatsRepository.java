package at.fhtw.sampleapp.persistence.repository;

import at.fhtw.sampleapp.model.UserStats;

import java.util.Map;

public interface StatsRepository {

    Map<String, Object> getStats(String username);
}
