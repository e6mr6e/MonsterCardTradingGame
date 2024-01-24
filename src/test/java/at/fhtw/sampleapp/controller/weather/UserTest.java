package at.fhtw.sampleapp.controller.weather;

import at.fhtw.sampleapp.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {

    @Test
    void testGetAndSetId() {
        // Arrange
        User user = new User();
        Integer expectedId = 1;

        // Act
        user.setId(expectedId);
        Integer actualId = user.getId();

        // Assert
        assertEquals(expectedId, actualId);
    }

    @Test
    void testGetAndSetUsername() {
        // Arrange
        User user = new User();
        String expectedUsername = "testUser";

        // Act
        user.setUsername(expectedUsername);
        String actualUsername = user.getUsername();

        // Assert
        assertEquals(expectedUsername, actualUsername);
    }

    @Test
    void testGetAndSetPassword() {
        // Arrange
        User user = new User();
        String expectedPassword = "testPassword";

        // Act
        user.setPassword(expectedPassword);
        String actualPassword = user.getPassword();

        // Assert
        assertEquals(expectedPassword, actualPassword);
    }

    @Test
    void testGetAndSetCoins() {
        // Arrange
        User user = new User();
        Integer expectedCoins = 20;

        // Act
        user.setCoins(expectedCoins);
        Integer actualCoins = user.getCoins();

        // Assert
        assertEquals(expectedCoins, actualCoins);
    }

    @Test
    void testGetAndSetName() {
        // Arrange
        User user = new User();
        String expectedName = "testName";

        // Act
        user.setName(expectedName);
        String actualName = user.getName();

        // Assert
        assertEquals(expectedName, actualName);
    }

    @Test
    void testGetAndSetBio() {
        // Arrange
        User user = new User();
        String expectedBio = "testBio";

        // Act
        user.setBio(expectedBio);
        String actualBio = user.getBio();

        // Assert
        assertEquals(expectedBio, actualBio);
    }

    @Test
    void testGetAndSetImage() {
        // Arrange
        User user = new User();
        String expectedImage = "testImage";

        // Act
        user.setImage(expectedImage);
        String actualImage = user.getImage();

        // Assert
        assertEquals(expectedImage, actualImage);
    }

    @Test
    void testConstructor() {
        // Arrange
        Integer expectedId = 1;
        String expectedUsername = "testUser";
        String expectedPassword = "testPassword";

        // Act
        User user = new User(expectedId, expectedUsername, expectedPassword);

        // Assert
        assertEquals(expectedId, user.getId());
        assertEquals(expectedUsername, user.getUsername());
        assertEquals(expectedPassword, user.getPassword());
    }
}