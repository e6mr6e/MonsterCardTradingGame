package at.fhtw.sampleapp.controller;

import at.fhtw.httpserver.http.ContentType;
import at.fhtw.httpserver.http.HttpStatus;
import at.fhtw.httpserver.http.Method;
import at.fhtw.httpserver.server.Request;
import at.fhtw.httpserver.server.Response;
import at.fhtw.httpserver.server.RestController;
import at.fhtw.sampleapp.service.DeckService;

public class DeckController implements RestController {
    private final DeckService deckService;

    public DeckController(DeckService deckService) {
        this.deckService = deckService;
    }

    @Override
    public Response handleRequest(Request request) {
        String params = request.getParams();
        System.out.println(params);
        int szie = request.getPathParts().size();
        System.out.println(szie);


        if (request.getMethod() == Method.GET && request.getParams() == null) {
            return deckService.getDeck(request);
        }
         else  if (request.getMethod() == Method.GET && params.equals("format=plain")) {
            return deckService.getDeckDif(request);
        }

        else if (request.getMethod() == Method.PUT) {
            return deckService.changeDeckforUser(request);
        } else {
            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "Invalid Request");
        }
    }
}
