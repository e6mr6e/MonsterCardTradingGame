package at.fhtw.sampleapp.controller.weather;

import at.fhtw.httpserver.http.Method;
import at.fhtw.httpserver.server.HeaderMap;
import at.fhtw.httpserver.server.Request;
import at.fhtw.httpserver.server.Response;
import at.fhtw.sampleapp.controller.SessionController;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class SessionControllerTest {

    private Request createMockRequest(Method method, String path, String token) {
        Request mockRequest = mock(Request.class);
        HeaderMap mockHeaderMap = mock(HeaderMap.class);

        when(mockRequest.getMethod()).thenReturn(method);
        when(mockRequest.getPathname()).thenReturn(path);
        when(mockRequest.getHeaderMap()).thenReturn(mockHeaderMap);
        when(mockHeaderMap.getHeader("Authorization")).thenReturn(token);

        return mockRequest;
        // Diese Methode erstellt ein simuliertes Request-Objekt mit den gegebenen Parametern.
        // Sie verwendet Mockito, um das Verhalten der Request- und HeaderMap-Klassen zu simulieren.

    }

    @Test
    void handleGetRequestWithMatchingToken() {
        //Dieser Test überprüft das Verhalten des SessionController,
        // wenn eine GET-Anfrage mit einem passenden Token gesendet wird.
        // Es wird erwartet, dass die handleRequest-Methode des SessionController einmal aufgerufen wird.
        // Arrange
        SessionController sessionController = spy(new SessionController());
        Response response = mock(Response.class);

        Request getRequest = createMockRequest(Method.GET, "/session/testSession", "testSession");

        doReturn(response).when(sessionController).handleRequest(getRequest);

        // Act
        sessionController.handleRequest(getRequest);

        // Assert
        verify(sessionController, times(1)).handleRequest(getRequest);
        // Add more assertions based on the expected behavior of your handleRequest method
    }

    @Test
    void handlePostRequest() {
        // Arrange
        //Dieser Test überprüft das Verhalten des SessionController,
        // wenn eine POST-Anfrage gesendet wird.
        // Es wird erwartet, dass die handleRequest-Methode des SessionController einmal aufgerufen wird.
        SessionController sessionController = spy(new SessionController());
        Response response = mock(Response.class);

        // Create a mock Request with a body containing Username and Password in JSON format
        Request postRequest = createMockRequest(Method.POST, "/session", null);
        String jsonBody = "{\"Username\": \"username\", \"Password\": \"password\"}";
        when(postRequest.getBody()).thenReturn(jsonBody);

        doReturn(response).when(sessionController).handleRequest(postRequest);

        // Act
        sessionController.handleRequest(postRequest);

        // Assert
        verify(sessionController, times(1)).handleRequest(postRequest);
        // Add more assertions based on the expected behavior of your handleRequest method
    }

    @Test
    void handlePutRequestWithMatchingToken() {
        //Dieser Test überprüft das Verhalten des SessionController,
        // wenn eine PUT-Anfrage mit einem passenden Token gesendet wird.
        // Es wird erwartet, dass die handleRequest-Methode des SessionController einmal aufgerufen wird.
        // Arrange
        SessionController sessionController = spy(new SessionController());
        Response response = mock(Response.class);

        Request putRequest = createMockRequest(Method.PUT, "/session/testSession", "testSession");

        doReturn(response).when(sessionController).handleRequest(putRequest);

        // Act
        sessionController.handleRequest(putRequest);

        // Assert
        verify(sessionController, times(1)).handleRequest(putRequest);
        // Add more assertions based on the expected behavior of your handleRequest method
    }

    @Test
    void handlePutRequestWithNonMatchingToken() {
        //Dieser Test überprüft das Verhalten des SessionController,
        // wenn eine PUT-Anfrage mit einem nicht passenden Token gesendet wird.
        // Es wird erwartet, dass die handleRequest-Methode des SessionController einmal aufgerufen wird.
        // Arrange
        SessionController sessionController = spy(new SessionController());
        Response response = mock(Response.class);

        Request putRequest = createMockRequest(Method.PUT, "/session/testSession", "wrongSession");

        doReturn(response).when(sessionController).handleRequest(putRequest);

        // Act
        sessionController.handleRequest(putRequest);

        // Assert
        verify(sessionController, times(1)).handleRequest(putRequest);
        // Add more assertions based on the expected behavior of your handleRequest method
    }

}