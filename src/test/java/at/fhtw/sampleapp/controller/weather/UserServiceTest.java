package at.fhtw.sampleapp.controller.weather;

import at.fhtw.sampleapp.model.User;
import at.fhtw.sampleapp.persistence.repository.UserRepository;
import at.fhtw.sampleapp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.mockito.Mockito.*;

public class UserServiceTest {
//In diesem Code mocken wir das UserRepository und überprüfen,
// ob die Methode findById mit dem erwarteten Wert aufgerufen wird,
// wenn wir getUser aufrufen.
    private UserService userService;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new UserService();
    }

    @Test
    void testGetUser() {
        // Arrange
        User expectedUser = new User();
        expectedUser.setId(1);
        expectedUser.setUsername("admin");
        expectedUser.setPassword("istrator");
        when(userRepository.findById(1)).thenReturn(expectedUser);

        // Act
        userService.getUser(1);

        // Assert
        verify(userRepository).findById(1);
    }

    // Ähnliche Tests für die anderen Methoden
}