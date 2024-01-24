package at.fhtw.sampleapp.persistence.repository;

import java.util.List;

public interface DeckRepository {
    boolean changeDeck(String username, List<String> cardIds);
    public String getDeck(String username);
     String showDeck(String username, String format);
}
