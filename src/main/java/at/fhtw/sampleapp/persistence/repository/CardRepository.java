package at.fhtw.sampleapp.persistence.repository;

import at.fhtw.sampleapp.model.Card;

import java.util.List;
import java.util.Map;

public interface CardRepository {
     List<Map<String, Object>> getCards(String username);

    }
