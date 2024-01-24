package at.fhtw.sampleapp.service;

import at.fhtw.httpserver.http.ContentType;
import at.fhtw.httpserver.http.HttpStatus;
import at.fhtw.httpserver.server.Request;
import at.fhtw.httpserver.server.Response;
import at.fhtw.sampleapp.persistence.UnitOfWork;
import at.fhtw.sampleapp.persistence.repository.ScoreboardRepository;
import at.fhtw.sampleapp.persistence.repository.ScoreboardRepositoryImpl;

import java.util.List;
import java.util.Map;

public class ScoreboardService {
    private ScoreboardRepository scoreboardRepository;
    public ScoreboardService() {
        scoreboardRepository = new ScoreboardRepositoryImpl(new UnitOfWork());
    }

    public Response handleGetReq(Request request) {

        List<Map<String, Object>> allStats = scoreboardRepository.getScoreBoard();
        String response = createTextFromStats(allStats);
        return new Response(HttpStatus.OK, ContentType.PLAIN_TEXT, response);
    }

    public static String createTextFromStats(List<Map<String, Object>> allStats){
        String UserStatsText = "{\n";
        for (Map<String, Object> userStats:allStats) {
            if (userStats != null) {
                UserStatsText = UserStatsText +"[" + "\"name\": \"" + userStats.get("name") + "\", "
                        + "\"loses\": " + userStats.get("losses") + ", "
                        + "\"wins\": " + userStats.get("wins") + "],\n"
                        + "\"ello\": " + userStats.get("mmr") + ", ";
            }
        }
        UserStatsText = UserStatsText + " }";
        return UserStatsText;
    }
}