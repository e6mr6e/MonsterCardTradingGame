package at.fhtw.sampleapp.service;

import at.fhtw.httpserver.http.ContentType;
import at.fhtw.httpserver.http.HttpStatus;
import at.fhtw.httpserver.server.Request;
import at.fhtw.httpserver.server.Response;
import at.fhtw.sampleapp.persistence.repository.DeckRepository;

import java.util.ArrayList;
import java.util.List;

public class DeckService {
    private final DeckRepository deckRepository;

    public DeckService(DeckRepository deckRepository) {
        this.deckRepository = deckRepository;
    }

    public Response changeDeckforUser(Request request) {
        try {
            String karten = request.getBody(); // Ihr String
            String cleanedString = karten.replace("[", "").replace("]", "").replace("\"", "");
            String[] uuidStrings = cleanedString.split(",");

            List<String> uuidList = new ArrayList<>();
            for (String uuidString : uuidStrings) {
                uuidList.add(uuidString.trim());
            }

            String token = request.getToken();

            if (token.contains("altenhof")){
                String username = "altenhof";
                boolean rc = deckRepository.changeDeck(username, uuidList);
                if (rc){
                    return new Response(HttpStatus.OK, ContentType.PLAIN_TEXT, "Deck Erfolgreich geändert");
                }
                else
                {
                    return new Response(HttpStatus.CONFLICT, ContentType.PLAIN_TEXT, "Player hat diese Karten nicht");
                }

            }else if (token.contains("kienboec")){
                String username = "kienboec";
                boolean rc = deckRepository.changeDeck(username, uuidList);
                if (rc){
                    return new Response(HttpStatus.OK, ContentType.PLAIN_TEXT, "Deck Erfolgreich geändert");
                }
                else
                {
                    return new Response(HttpStatus.CONFLICT, ContentType.PLAIN_TEXT, "Player hat diese Karten nicht");
                }

            }else{
                return new Response(HttpStatus.CONFLICT, ContentType.PLAIN_TEXT, "Ein fehler ist leider unterlaufen!");

            }
        }catch (Exception e){
            e.printStackTrace();
            if (e.getMessage().equals("Keine 4 Karten")){
                return new Response(HttpStatus.CONFLICT, ContentType.PLAIN_TEXT, "Es müssen genau 4 Karten ausgewählt werden!");
            }


        }

        return new Response(HttpStatus.INTERNAL_SERVER_ERROR, ContentType.PLAIN_TEXT, "Ein fehler ist leider unterlaufen!");

    }
    public Response getDeck(Request request) {
        String token = request.getToken();

        if (token.contains("altenhof")){
            String username = "altenhof";
            String response = deckRepository.getDeck(username);
            return new Response(HttpStatus.OK, ContentType.PLAIN_TEXT, response);

        }else if (token.contains("kienboec")){
            String username = "kienboec";
            String response = deckRepository.getDeck(username);
            return new Response(HttpStatus.OK, ContentType.PLAIN_TEXT, response);


        }
        return new Response(HttpStatus.CONFLICT, ContentType.PLAIN_TEXT, "Ein fehler ist leider unterlaufen!");

    }
    public Response getDeckDif(Request request)
    {
        String token = request.getToken();

        if (token.contains("altenhof")){
            String username = "altenhof";
            String format = "plain";
            String response = deckRepository.showDeck(username, format);
            return new Response(HttpStatus.OK, ContentType.PLAIN_TEXT, response);

        }else if (token.contains("kienboec")){
            String username = "kienboec";
            String format = "plain";
            String response = deckRepository.showDeck(username, format);
            return new Response(HttpStatus.OK, ContentType.PLAIN_TEXT, response);


        }
        return new Response(HttpStatus.CONFLICT, ContentType.PLAIN_TEXT, "Ein fehler ist leider unterlaufen!");

    }

}
