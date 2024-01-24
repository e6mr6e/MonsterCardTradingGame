package at.fhtw.httpserver.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public interface RestController {
    Response handleRequest(Request request) throws JsonProcessingException;
    ObjectMapper objectMapper = new ObjectMapper();

    default ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
