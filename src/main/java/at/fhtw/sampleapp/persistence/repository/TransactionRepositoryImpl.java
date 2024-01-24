package at.fhtw.sampleapp.persistence.repository;

import at.fhtw.sampleapp.persistence.DataAccessException;
import at.fhtw.sampleapp.persistence.UnitOfWork;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionRepositoryImpl implements TransactionRepository{

    private UnitOfWork unitOfWork;

    public TransactionRepositoryImpl(UnitOfWork unitOfWork)
    {
        this.unitOfWork = unitOfWork;
    }

    public List<Map<String, Object>> selectCards() {
        List<Map<String, Object>> paketListe = new ArrayList<>();
        String query = "SELECT * FROM packages LIMIT 1";
        try (PreparedStatement stmt = this.unitOfWork.prepareStatement(query)) {
            ResultSet resultSet = stmt.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (resultSet.next()) {
                Map<String, Object> paketDaten = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    String spaltenName = metaData.getColumnName(i);
                    Object spaltenWert = resultSet.getObject(i);
                    paketDaten.put(spaltenName, spaltenWert);
                }
                paketListe.add(paketDaten);
            }
            if (paketListe.isEmpty()) {
                throw new DataAccessException("Keine Pakete in der Datenbank gefunden");
            }
        } catch (SQLException e) {
            throw new DataAccessException("Fehler beim Holen des ersten Pakets", e);
        }
        return paketListe;
    }

    public boolean hatGenugMuenzen(String username) {
        String query = "SELECT coins FROM benutzer WHERE username = ?";
        try (PreparedStatement stmt = this.unitOfWork.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                int muenzen = resultSet.getInt("coins");
                return muenzen >= 5;
            }
        } catch (SQLException e) {
            throw new DataAccessException("Fehler beim Abfragen des Münzbestands", e);
        }
        return false;
    }

    public void muenzenAbziehen(String username) {

            String query = "UPDATE benutzer SET coins = coins - 5 WHERE username = ?";
            try (PreparedStatement stmt = this.unitOfWork.prepareStatement(query)) {
                stmt.setString(1, username);
                int betroffeneZeilen = stmt.executeUpdate();
                if (betroffeneZeilen == 0) {
                    throw new DataAccessException("Münzabzug fehlgeschlagen, Benutzer existiert möglicherweise nicht");
                }
                unitOfWork.commitTransaction();
            } catch (SQLException e) {
                unitOfWork.rollbackTransaction();
                throw new DataAccessException("Fehler beim Münzabzug", e);
            }
        
    }
    public void deletePackage() {
        try {
            // Retrieve the first row from the previously selected packages
            List<Map<String, Object>> selectedCards = selectCards();

            // Get the ID of the first row
            int packageId = (int) selectedCards.get(0).get("package_id");

            // Delete the first row from the packages table
            String deleteSql = "DELETE FROM packages WHERE package_id = ?";
            try (PreparedStatement deleteStatement = this.unitOfWork.prepareStatement(deleteSql)) {
                deleteStatement.setInt(1, packageId);
                int rowsAffected = deleteStatement.executeUpdate();

                if (rowsAffected == 0) {
                    throw new DataAccessException("Failed to delete the package");
                }
            }
            unitOfWork.commitTransaction();
        } catch (SQLException e) {
            e.printStackTrace();
            unitOfWork.rollbackTransaction();
            throw new DataAccessException("Failed to delete package", e);
        }
    }
    public void kartenZuordnen(String username) {
        try {
            // Paketdaten abrufen
            List<Map<String, Object>> pakete = selectCards();

            // SQL-Befehl zum Einfügen der Karten in das Benutzerkonto
            String insertQuery = "INSERT INTO gekauftekarten (card_id, username) VALUES (?, ?)";
            for (Map<String, Object> paket : pakete) {
                try (PreparedStatement stmt = this.unitOfWork.prepareStatement(insertQuery)) {
                    for (int i = 1; i <= 5; i++) {
                        String kartenSchluessel = "card_" + i + "_id";
                        Object kartenId = paket.get(kartenSchluessel);
                        if (kartenId == null) {
                            throw new DataAccessException("Karten-ID für Kartenindex " + i + " nicht gefunden");
                        }
                        stmt.setObject(1, kartenId);
                        stmt.setString(2, username);
                        stmt.executeUpdate();
                    }
                }
                unitOfWork.commitTransaction();
            }
        } catch (SQLException e) {
            unitOfWork.rollbackTransaction();
            throw new DataAccessException("Fehler beim Zuordnen der Karten", e);
        }
    }



    public List<String> vierKartenAuswaehlen(String username) {
        List<String> karten = new ArrayList<>();
        try {
            // SQL SELECT-Befehl vorbereiten
            String selectQuery = "SELECT card_id FROM gekauftekarten WHERE username = ? LIMIT 4";
            try (PreparedStatement stmt = this.unitOfWork.prepareStatement(selectQuery)) {
                stmt.setString(1, username);
                ResultSet resultSet = stmt.executeQuery();
                while (resultSet.next()) {
                    karten.add(resultSet.getString("card_id"));
                }
            }
            if (karten.isEmpty()) {
                throw new DataAccessException("Keine Karten gefunden");
            }
        } catch (SQLException e) {
            throw new DataAccessException("Fehler beim Auswählen der Karten für den Benutzer", e);
        }
        return karten;
    }

    public void kartenImDeckGeben(String username) {
        try {
            // Get the list of card IDs
            List<String> cards = vierKartenAuswaehlen(username);

            // Prepare SQL UPDATE statement
            String updateSql = "UPDATE deck SET card_1_id = ?, card_2_id = ?, card_3_id = ?, card_4_id = ? WHERE username = ?";
            try (PreparedStatement updateStatement = this.unitOfWork.prepareStatement(updateSql)) {
                for (int i = 0; i < cards.size(); i++) {
                    updateStatement.setString(i + 1, cards.get(i));
                }
                // Set username parameter in the prepared statement
                updateStatement.setString(5, username);
                // Execute the prepared statement
                updateStatement.executeUpdate();
            }

            unitOfWork.commitTransaction();
        } catch (SQLException e) {
            e.printStackTrace();
            unitOfWork.rollbackTransaction();
            throw new DataAccessException("Failed to update cards into deck for username", e);
        }
    }

    public void paketeErwerben(String username) {
        try {
            // Schritt 1: Überprüfen, ob der Benutzer genug Münzen hat.
            if (!hatGenugMuenzen(username)) {
                throw new DataAccessException("Nicht genug Münzen");
            }

            // Schritt 2: Karten dem gekauften Karten-Table hinzufügen.
            kartenZuordnen(username);

            // Schritt 3: Paket löschen.
            deletePackage();

            // Schritt 4: Münzen vom Benutzerkonto abziehen.
            muenzenAbziehen(username);

            // Schritt 5: Karten im Deck des Benutzers aktualisieren.
            kartenImDeckGeben(username);

            unitOfWork.commitTransaction();
        } catch (DataAccessException e) {
            unitOfWork.rollbackTransaction();
            throw e;  // Wirf die ursprüngliche DataAccessException erneut.
        } catch (Exception e) {
            unitOfWork.rollbackTransaction();
            throw new DataAccessException("Fehler beim Erwerb der Pakete", e);
        }
    }
    public int countAcquiredPackages(String username) {
        String sql = "SELECT COUNT(*) FROM gekauftekarten WHERE username = ?";
        try(PreparedStatement preparedStatement = this.unitOfWork.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Failed to count acquired packages", e);
        }
        return 0;
    }

}
