package at.fhtw.sampleapp.controller;

import at.fhtw.httpserver.http.ContentType;
import at.fhtw.httpserver.http.HttpStatus;
import at.fhtw.httpserver.http.Method;
import at.fhtw.httpserver.server.Request;
import at.fhtw.httpserver.server.Response;
import at.fhtw.httpserver.server.RestController;
import at.fhtw.sampleapp.service.StatsService;

public class StatsController implements RestController {
    private final StatsService statsService;

    public StatsController() {
        this.statsService = new StatsService();
    }

    @Override
    public Response handleRequest(Request request) {

        String token = request.getHeaderMap().getHeader("Authorization");
        String usernameFromToken = extractUsernameFromToken(token);

        if (request.getMethod() == Method.GET ) {
            System.out.println("HIER");
            return this.statsService.handleGetReq(usernameFromToken);
        }
        return new Response(
                HttpStatus.BAD_REQUEST,
                ContentType.JSON,
                "[]"
        );
    }
    private String extractUsernameFromToken(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            String[] parts = token.substring(7).split("-"); // Teilen Sie den String an dem Bindestrich
            if (parts.length > 0) {
                return parts[0]; // Der Benutzername ist der erste Teil des geteilten Strings
            }
        }
        return null;
    }

}
