package at.fhtw.sampleapp.service;

import at.fhtw.httpserver.http.ContentType;
import at.fhtw.httpserver.http.HttpStatus;
import at.fhtw.httpserver.server.Request;
import at.fhtw.httpserver.server.Response;
import at.fhtw.sampleapp.model.User;
import at.fhtw.sampleapp.persistence.UnitOfWork;
import at.fhtw.sampleapp.persistence.repository.UserRepository;
import at.fhtw.sampleapp.persistence.repository.UserRepositoryImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class UserService extends AbstractService{

        private UserRepository userRepository;

        public UserService() { userRepository = new UserRepositoryImpl(new UnitOfWork()); }
        public Response getUser(int id)
        {
            System.out.println("get user for id: " + id);
            User user = userRepository.findById(id);
            String json = null;
            try {
                json = this.getObjectMapper().writeValueAsString(user);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            return new Response(HttpStatus.OK, ContentType.JSON, json);
        }

        public Response getUserByUsername(String username)
        {
            System.out.println("get user for username: " + username);
            User user = userRepository.findByUsername(username);
            String json = null;
            try {
                json = this.getObjectMapper().writeValueAsString(user);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            return new Response(HttpStatus.OK, ContentType.JSON, json);
        }

        public Response addUser(Request request) {
            try {
                String body = request.getBody();


                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(body);


                String username = jsonNode.get("Username").asText();
                String password = jsonNode.get("Password").asText();
                UUID uuid = UUID.randomUUID();
                int id = uuid.hashCode();
                User user = new User();
                user.setUsername(username);
                user.setPassword(password);
                user.setId(id);
                user.setCoins(20);
                userRepository.addUser(user);
                userRepository.insertUserNameInDeck(username);
                userRepository.insertUsernameInStats(username);

                return new Response(HttpStatus.OK, ContentType.PLAIN_TEXT, "User created!");
            } catch (Exception e) {
                e.printStackTrace();
                if (e.getMessage().equals("Username already taken"))
                {
                    return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON,"User with same username already registered\n"
                             );
                }
                else {

                    return new Response(HttpStatus.BAD_REQUEST, ContentType.PLAIN_TEXT, "Ein fehler ist unterlaufen!");
                }
            }
        }
    public Response changeUser(String username,Request request) {
        try {
            String body = request.getBody();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(body);

            String name = jsonNode.get("Name").asText();
            String bio = jsonNode.get("Bio").asText();
            String image = jsonNode.get("Image").asText();

            User user = userRepository.findByUsername(username);
            if (user == null) {
                return new Response(HttpStatus.BAD_REQUEST, ContentType.PLAIN_TEXT, "User not found!");
            }

            user.setName(name);
            user.setBio(bio);
            user.setImage(image);

            userRepository.changeUser(username, user);

            return new Response(HttpStatus.OK, ContentType.PLAIN_TEXT, "User updated successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(HttpStatus.BAD_REQUEST, ContentType.PLAIN_TEXT, "An error occurred!");
        }
    }
}

