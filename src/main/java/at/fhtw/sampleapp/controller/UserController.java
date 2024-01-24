package at.fhtw.sampleapp.controller;


import at.fhtw.httpserver.http.ContentType;
import at.fhtw.httpserver.http.HttpStatus;
import at.fhtw.httpserver.http.Method;
import at.fhtw.httpserver.server.Request;
import at.fhtw.httpserver.server.Response;
import at.fhtw.httpserver.server.RestController;
import at.fhtw.sampleapp.service.UserService;
import com.sun.net.httpserver.HttpExchange;

import javax.management.StringValueExp;
import java.io.OutputStream;

public class UserController implements RestController {

    private final UserService userService;

    public UserController() { this.userService = new UserService(); }

    @Override
    public Response handleRequest(Request request){
        String token = request.getHeaderMap().getHeader("Authorization");
        String usernameFromToken = extractUsernameFromToken(token);

        System.out.println(request.getBody());
        if (request.getMethod() == Method.GET && request.getPathParts().size() > 1 && request.getPathParts().get(0).equals("users")) {
            String username = request.getPathParts().get(1);
            if (usernameFromToken.equals(username)) {
                return this.userService.getUserByUsername(usernameFromToken);
            }
            else{
                return new Response(
                        HttpStatus.BAD_REQUEST,
                        ContentType.JSON,
                        "[Token Stimmt nicht mit Username überein"
                );
            }


        } else if (request.getMethod() == Method.GET && request.getPathParts().size() > 1) {
            String userId = request.getPathParts().get(1);
            return this.userService.getUser(1);
        } else if (request.getMethod() == Method.GET) {
            //userService.getUser();
            //return this.userService.getUserPerRepository();
        } else if (request.getMethod() == Method.POST) {

            return this.userService.addUser(request);

        } else if (request.getMethod() == Method.PUT && request.getPathParts().size() > 1 && request.getPathParts().get(0).equals("users")) {
            String username = request.getPathParts().get(1);
            if (usernameFromToken.equals(username)) {
                return this.userService.changeUser(usernameFromToken, request);
            } else {
                return new Response(
                        HttpStatus.BAD_REQUEST,
                        ContentType.JSON,
                        "Token Stimmt nicht mit Username überein"
                );
            }
        }



        return new Response(
                HttpStatus.BAD_REQUEST,
                ContentType.JSON,
                "[]"
        );


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
