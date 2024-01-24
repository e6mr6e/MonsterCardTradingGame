package at.fhtw.sampleapp.persistence.repository;

import at.fhtw.sampleapp.model.User;

import java.util.Collection;

public interface UserRepository {

    User findById(int id);

    User findByUsername(String username);

    //Collection<User> findAllUsers();

    void addUser(User user);

    void insertUserNameInDeck(String username);
    void changeUser(String username, User user);
    void insertUsernameInStats(String username);
}
