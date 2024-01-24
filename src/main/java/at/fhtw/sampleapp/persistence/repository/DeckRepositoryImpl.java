package at.fhtw.sampleapp.persistence.repository;

import at.fhtw.sampleapp.model.UserStats;
import at.fhtw.sampleapp.persistence.DataAccessException;
import at.fhtw.sampleapp.persistence.UnitOfWork;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Vector;

public class DeckRepositoryImpl implements DeckRepository{
    private final UnitOfWork unitOfWork;

    public DeckRepositoryImpl(UnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    @Override
    public boolean changeDeck(String username, List<String> cardIds) {
        if (cardIds.size() != 4) {
            throw new IllegalArgumentException("Keine 4 Karten");
        }

        // Check if user owns all the cards

        for (String cardId : cardIds) {
            if (!userOwnsCard(username, cardId)) {
                return false;
            }
        }

        String sql = "UPDATE deck SET card_1_id = ?, card_2_id = ?, card_3_id = ?, card_4_id = ? WHERE username = ?";

        try (PreparedStatement preparedStatement = this.unitOfWork.prepareStatement(sql)) {
            preparedStatement.setString(5, username);


            for (int i = 0; i < 4; i++) {
                preparedStatement.setString(i + 1, cardIds.get(i));
            }

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Failed to update deck", e);
        }
    }



    @Override
    public String getDeck(String username) {
        try {
            String sql = "SELECT * FROM \"deck\" WHERE username = ?";
            PreparedStatement preparedStatement = unitOfWork.prepareStatement(sql);
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                StringBuilder deckInfo = new StringBuilder("Deck for user " + username + ":\n");
                for (int i = 1; i <= 4; i++) {
                    String cardId = resultSet.getString("card_" + i + "_id");
                    if (cardId != null) {
                        String cardSql = "SELECT name, damage FROM \"cards\" WHERE id = ?";
                        PreparedStatement cardStatement = unitOfWork.prepareStatement(cardSql);
                        cardStatement.setString(1, cardId);
                        ResultSet cardResultSet = cardStatement.executeQuery();
                        if (cardResultSet.next()) {
                            String cardName = cardResultSet.getString("name");
                            int cardDamage = cardResultSet.getInt("damage");
                            deckInfo.append("card_").append(i).append("_id = ").append(cardId)
                                    .append(", name = ").append(cardName)
                                    .append(", damage = ").append(cardDamage).append("\n");
                        }
                    } else {
                        deckInfo.append("card_").append(i).append("_id = null\n");
                    }
                }
                return deckInfo.toString();
            } else {
                return "No deck found for user " + username;
            }
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public boolean userOwnsCard(String username, String cardId) {
        String sql = "SELECT COUNT(*) FROM gekauftekarten WHERE username = ? AND card_id = ?";
        try (PreparedStatement preparedStatement = this.unitOfWork.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, cardId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error checking if user owns card", e);
        }
    }
    public String showDeck(String username, String format) {
        StringBuilder deckRepresentation = new StringBuilder();
        String sql = "SELECT * FROM deck WHERE username = ?";

        try (PreparedStatement preparedStatement = this.unitOfWork.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                if ("plain".equals(format)) {
                    for (int i = 1; i <= 4; i++) {
                        String cardId = resultSet.getString("card_" + i + "_id");
                        if (cardId != null) {
                            String cardSql = "SELECT name, damage FROM cards WHERE id = ?";
                            PreparedStatement cardStatement = this.unitOfWork.prepareStatement(cardSql);
                            cardStatement.setString(1, cardId);
                            ResultSet cardResultSet = cardStatement.executeQuery();
                            if (cardResultSet.next()) {
                                String cardName = cardResultSet.getString("name");
                                int cardDamage = cardResultSet.getInt("damage");
                                deckRepresentation.append("name = ").append(cardName)
                                        .append(", damage = ").append(cardDamage).append("\n");
                            }
                        } else {
                            deckRepresentation.append("card_").append(i).append("_id = null\n");
                        }
                    }
                }  // Handle other formats...

            } else {
                deckRepresentation.append("No deck found for user ").append(username);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Failed to show deck", e);
        }

        return deckRepresentation.toString();
    }



}
