package at.fhtw.sampleapp.controller;

import at.fhtw.httpserver.http.ContentType;
import at.fhtw.httpserver.http.HttpStatus;
import at.fhtw.httpserver.http.Method;
import at.fhtw.httpserver.server.Request;
import at.fhtw.httpserver.server.Response;
import at.fhtw.httpserver.server.RestController;
import at.fhtw.sampleapp.persistence.DataAccessException;
import at.fhtw.sampleapp.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class TransactionController implements RestController {

    private final TransactionService transactionService;
    private final ObjectMapper objectMapper;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Response handleRequest(Request request) {
        String token = request.getHeaderMap().getHeader("Authorization");
        String usernameFromToken = extractUsernameFromToken(token);

        if(request.getPathname().equals("/transactions/packages")) {
            if (request.getMethod() == Method.POST) {
                try {
                    this.transactionService.acquirePackages(usernameFromToken);
                    return new Response(HttpStatus.OK, ContentType.JSON, "Packages acquired successfully");
                } catch (DataAccessException e) {
                    return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, e.getMessage());
                }
            }
        } else if(request.getPathname().equals("/transactions/packages/count")) {
            if (request.getMethod() == Method.GET) {
                try {
                    int count = this.transactionService.countAcquiredPackages(usernameFromToken);
                    return new Response(HttpStatus.OK, ContentType.JSON, String.valueOf(count));
                } catch (DataAccessException e) {
                    return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, e.getMessage());
                }
            }
        }

        return new Response(HttpStatus.NOT_FOUND, ContentType.JSON, "Endpoint not found");
    }

    private String extractUsernameFromToken(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            String[] parts = token.substring(7).split("-"); // Teilen Sie den String an dem Bindestrich
            if (parts.length > 0) {
                return parts[0]; // Der Benutzername ist der erste Teil des geteilten Strings
            }
        }
        return null;
    }
}