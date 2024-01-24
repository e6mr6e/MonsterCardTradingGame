package at.fhtw.sampleapp.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;

public class User {


    @JsonAlias({"id"})
    @Getter
    @Setter
    private Integer id;

    @JsonAlias({"username"})
    @Getter
    @Setter
    private String username;

    @JsonAlias({"password"})
    @Getter
    @Setter
    private String password;

    @JsonAlias({"coins"})
    @Getter
    @Setter
    private Integer coins;

    @JsonAlias({"name"})
    @Getter
    @Setter
    private String name;

    @JsonAlias({"bio"})
    @Getter
    @Setter
    private String bio;

    @JsonAlias({"image"})
    @Getter
    @Setter
    private String image;

    // Default constructor (no-argument constructor)
    public User() {
    }

    // Constructor with @JsonCreator and @JsonProperty annotations for deserialization
    public User(Integer id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;

    }


}
