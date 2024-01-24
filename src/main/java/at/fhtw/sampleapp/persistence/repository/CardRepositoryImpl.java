package at.fhtw.sampleapp.persistence.repository;

import at.fhtw.sampleapp.model.Card;
import at.fhtw.sampleapp.persistence.DataAccessException;
import at.fhtw.sampleapp.persistence.UnitOfWork;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardRepositoryImpl implements CardRepository{


    public CardRepositoryImpl(UnitOfWork unitOfWork)
    {
        this.unitOfWork = unitOfWork;
    }

    private UnitOfWork unitOfWork;
    @Override
    public List<Map<String, Object>> getCards(String username) {
        System.out.println("username: " + username);
        List<Map<String, Object>> cards = new ArrayList<>();
        String sql = "SELECT card_id FROM gekauftekarten WHERE username = ?";
        try (PreparedStatement preparedStatement = this.unitOfWork.prepareStatement(sql))
        {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String cardId = resultSet.getString("card_id");

                String cardSql = "SELECT id, name, damage FROM cards WHERE id = ?";
                PreparedStatement cardStatement = this.unitOfWork.prepareStatement(cardSql);
                cardStatement.setString(1, cardId);
                ResultSet cardResultSet = cardStatement.executeQuery();

                while (cardResultSet.next()) {
                    Map<String, Object> card = new HashMap<>();
                    card.put("id", cardResultSet.getString("id"));
                    card.put("name", cardResultSet.getString("name"));
                    card.put("damage", cardResultSet.getInt("damage"));
                    cards.add(card);
                }
            }
            // Check if cards is still empty after processing the ResultSet
            if (cards.isEmpty()) {
                //System.out.println("No cards found");
                throw new DataAccessException("No cards found");
            }
            return cards;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Failed to get cards", e);
        }
    }
    public static void main(String[] args) {
        UnitOfWork unitOfWork = new UnitOfWork(); // Erstellen Sie eine Instanz von UnitOfWork
        CardRepositoryImpl cardRepository = new CardRepositoryImpl(unitOfWork); // Erstellen Sie eine Instanz von CardRepositoryImpl

        String username = "kienboec"; // Ersetzen Sie "testUser" durch den tatsächlichen Benutzernamen
        List<Map<String, Object>> cards = cardRepository.getCards(username); // Rufen Sie die getCards Methode auf

        // Drucken Sie die Karteninformationen
        for (Map<String, Object> card : cards) {
            System.out.println("ID: " + card.get("ID") + ", Name: " + card.get("Name") + ", Damage: " + card.get("Damage"));
        }
    }


}
