package at.fhtw.sampleapp.service;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AbstractUser {
    private ObjectMapper objectMapper;

    public void AbstractService() {
        this.objectMapper = new ObjectMapper();
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
