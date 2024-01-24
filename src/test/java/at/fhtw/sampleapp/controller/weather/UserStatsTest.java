package at.fhtw.sampleapp.controller.weather;

import at.fhtw.sampleapp.model.UserStats;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserStatsTest {

    @Test
    void testGetAndSetName() {
        // Arrange
        UserStats userStats = new UserStats();
        String expectedName = "testUser";

        // Act
        userStats.setName(expectedName);
        String actualName = userStats.getName();

        // Assert
        assertEquals(expectedName, actualName);
    }

    @Test
    void testGetAndSetWins() {
        // Arrange
        UserStats userStats = new UserStats();
        Integer expectedWins = 10;

        // Act
        userStats.setWins(expectedWins);
        Integer actualWins = userStats.getWins();

        // Assert
        assertEquals(expectedWins, actualWins);
    }

    @Test
    void testGetAndSetLosses() {
        // Arrange
        UserStats userStats = new UserStats();
        Integer expectedLosses = 5;

        // Act
        userStats.setLosses(expectedLosses);
        Integer actualLosses = userStats.getLosses();

        // Assert
        assertEquals(expectedLosses, actualLosses);
    }

    @Test
    void testGetAndSetMmr() {
        // Arrange
        UserStats userStats = new UserStats();
        Integer expectedMmr = 1500;

        // Act
        userStats.setMmr(expectedMmr);
        Integer actualMmr = userStats.getMmr();

        // Assert
        assertEquals(expectedMmr, actualMmr);
    }
}