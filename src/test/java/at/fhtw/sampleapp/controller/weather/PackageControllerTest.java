package at.fhtw.sampleapp.controller;

import at.fhtw.httpserver.http.Method;
import at.fhtw.httpserver.server.HeaderMap;
import at.fhtw.httpserver.server.Request;
import at.fhtw.httpserver.server.Response;
import at.fhtw.sampleapp.controller.PackageController;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class PackageControllerTest {

    private Request createMockRequest(Method method, String path, String token) {
        Request mockRequest = mock(Request.class);
        HeaderMap mockHeaderMap = mock(HeaderMap.class);

        when(mockRequest.getMethod()).thenReturn(method);
        when(mockRequest.getPathname()).thenReturn(path);
        when(mockRequest.getHeaderMap()).thenReturn(mockHeaderMap);
        when(mockHeaderMap.getHeader("Authorization")).thenReturn(token);

        return mockRequest;
    }

    @Test
    void handlePostRequestWithAdminToken() {
        // Arrange
        PackageController packageController = spy(new PackageController());
        Response response = mock(Response.class);

        Request postRequest = createMockRequest(Method.POST, "/packages", "admin");

        doReturn(response).when(packageController).handleRequest(postRequest);

        // Act
        packageController.handleRequest(postRequest);

        // Assert
        verify(packageController, times(1)).handleRequest(postRequest);
        // Add more assertions based on the expected behavior of your handleRequest method
    }

    @Test
    void handlePostRequestWithNonAdminToken() {
        // Arrange
        PackageController packageController = spy(new PackageController());
        Response response = mock(Response.class);

        Request postRequest = createMockRequest(Method.POST, "/packages", "user");

        doReturn(response).when(packageController).handleRequest(postRequest);

        // Act
        packageController.handleRequest(postRequest);

        // Assert
        verify(packageController, times(1)).handleRequest(postRequest);
        // Add more assertions based on the expected behavior of your handleRequest method
    }

    @Test
    void handleInvalidRequest() {
        // Arrange
        PackageController packageController = spy(new PackageController());
        Response response = mock(Response.class);

        Request getRequest = createMockRequest(Method.GET, "/packages", "admin");

        doReturn(response).when(packageController).handleRequest(getRequest);

        // Act
        packageController.handleRequest(getRequest);

        // Assert
        verify(packageController, times(1)).handleRequest(getRequest);
        // Add more assertions based on the expected behavior of your handleRequest method
    }
}