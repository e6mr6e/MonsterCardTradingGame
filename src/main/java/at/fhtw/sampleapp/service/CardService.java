package at.fhtw.sampleapp.service;

import at.fhtw.sampleapp.model.Card;
import at.fhtw.sampleapp.persistence.UnitOfWork;
import at.fhtw.sampleapp.persistence.repository.CardRepository;

import java.util.List;
import java.util.Map;

public class CardService extends AbstractService{



    private final CardRepository cardRepository;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public List<Map<String, Object>> getCards(String username) {
        // Validate the token and handle the business logic
        // Then, call the repository to get the cards
        return cardRepository.getCards(username);
    }
}
