package at.fhtw.sampleapp.persistence.repository;

import at.fhtw.sampleapp.model.Card;

import java.util.List;

public interface PackageRepository  {
    public boolean cardExists(String cardId);

    void addCards(List<Card> cards);



}
