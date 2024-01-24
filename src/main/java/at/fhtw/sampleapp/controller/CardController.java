package at.fhtw.sampleapp.controller;

import at.fhtw.httpserver.http.ContentType;
import at.fhtw.httpserver.http.HttpStatus;
import at.fhtw.httpserver.http.Method;
import at.fhtw.httpserver.server.Request;
import at.fhtw.httpserver.server.Response;
import at.fhtw.httpserver.server.RestController;
import at.fhtw.sampleapp.model.Card;
import at.fhtw.sampleapp.service.CardService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

public class CardController implements RestController {

    private final CardService cardService;
    private final ObjectMapper objectMapper;
    public CardController(CardService cardService) {
        this.cardService = cardService;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Response handleRequest(Request request) throws JsonProcessingException {
        String token = request.getHeaderMap().getHeader("Authorization");
        System.out.println("usernameFromToken = " + token);
        if (token == null) {
            return new Response(
                    HttpStatus.BAD_REQUEST,
                    ContentType.JSON,
                    "TOKEN IST NICHT VORHANDEN"
            );
        }

        String usernameFromToken = extractUsernameFromToken(token);

        if (request.getMethod() == Method.GET) {
            System.out.println("GET");
            System.out.println("usernameFromToken = " + usernameFromToken);
            List<Map<String, Object>> cards = cardService.getCards(usernameFromToken);
            String card = this.getObjectMapper().writeValueAsString(cards);

            return new Response(HttpStatus.OK, ContentType.JSON, card);

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
