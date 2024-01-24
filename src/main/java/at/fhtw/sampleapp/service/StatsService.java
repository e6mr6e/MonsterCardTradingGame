package at.fhtw.sampleapp.service;

import at.fhtw.httpserver.http.ContentType;
import at.fhtw.httpserver.http.HttpStatus;
import at.fhtw.httpserver.server.Request;
import at.fhtw.httpserver.server.Response;
import at.fhtw.sampleapp.model.UserStats;
import at.fhtw.sampleapp.persistence.UnitOfWork;
import at.fhtw.sampleapp.persistence.repository.StatsRepository;
import at.fhtw.sampleapp.persistence.repository.StatsRepositoryImpl;

import java.util.Map;
import java.util.Vector;

import static at.fhtw.sampleapp.service.ScoreboardService.createTextFromStats;

public class StatsService{
        private StatsRepository statsRepository;
        public StatsService() {
            statsRepository = new StatsRepositoryImpl(new UnitOfWork());
        }




    public Response handleGetReq(String username) {
        Map<String, Object> stats = statsRepository.getStats(username);

        if (stats.isEmpty()) {
            return new Response(HttpStatus.NOT_FOUND, ContentType.PLAIN_TEXT, "No stats found for user: " + username);
        }

        String statsString = stats.toString();
        return new Response(HttpStatus.OK, ContentType.PLAIN_TEXT, statsString);
    }



}
