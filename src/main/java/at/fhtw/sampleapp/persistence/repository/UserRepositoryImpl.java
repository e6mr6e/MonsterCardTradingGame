package at.fhtw.sampleapp.persistence.repository;

import at.fhtw.sampleapp.persistence.DataAccessException;
import at.fhtw.sampleapp.persistence.DatabaseManager;
import at.fhtw.sampleapp.persistence.UnitOfWork;
import at.fhtw.sampleapp.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class UserRepositoryImpl implements UserRepository {

    private UnitOfWork unitOfWork;

    public UserRepositoryImpl(UnitOfWork unitOfWork)
    {
        this.unitOfWork = unitOfWork;
    }

    @Override
    public User findById(int id) {
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("SELECT * FROM public.benutzer WHERE user_id = ?"))
        {
            preparedStatement.setInt(1, id);

            // Print the SQL query
            //System.out.println("Executing SQL query: " + preparedStatement.toString());

            ResultSet resultSet = preparedStatement.executeQuery();
            User user = null;
            while(resultSet.next())
            {
                user = new User(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3));
            }
            return user;
        } catch (SQLException e) {
            throw new DataAccessException("Select nicht erfolgreich", e);
        }
    }

    public User findByUsername(String username) {
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("SELECT * FROM public.benutzer WHERE username = ?"))
        {
            preparedStatement.setString(1, username);

            // Print the SQL query
            System.out.println("Executing SQL query: " + preparedStatement.toString());

            ResultSet resultSet = preparedStatement.executeQuery();
            User user = null;
            while(resultSet.next())
            {
                user = new User(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3)
                );
                user.setCoins(resultSet.getInt(4));
                user.setName(resultSet.getString(5));
                user.setBio(resultSet.getString(6));
                user.setImage(resultSet.getString(7));
            }
            return user;
        } catch (SQLException e) {
            throw new DataAccessException("Select nicht erfolgreich", e);
        }
    }

//    @Override
//    public Collection<User> findAllUsers() {
//        Connection conn  = DatabaseManager.INSTANCE.getConnection();
//        unitOfWork.setConnection(conn);
//        try (PreparedStatement preparedStatement =
//                     this.unitOfWork.prepareStatement("SELECT * FROM public.users"))
//        {
//            ResultSet resultSet = preparedStatement.executeQuery();
//            Collection<User> userRows = new ArrayList<>();
//            while(resultSet.next())
//            {
//                User user = new User(
//                        resultSet.getInt(1),
//                        resultSet.getString(2),
//                        resultSet.getString(3));
//                userRows.add(user);
//            }
//            return userRows;
//        } catch (SQLException e) {
//            throw new DataAccessException("Select nicht erfolgreich", e);
//        }
//    }

    public void addUser(User user) {

        try {
            // Check if a user with the same username already exists
            User existingUser = findByUsername(user.getUsername());
            if (existingUser != null) {
                throw new DataAccessException("Username already taken");
            }
            String sql = "INSERT INTO \"benutzer\" (user_id, username, password, coins) VALUES (?, ?, ?, ?)";

            PreparedStatement preparedStatement = this.unitOfWork.prepareStatement(sql);
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setInt(4, user.getCoins());
            preparedStatement.executeUpdate();
            unitOfWork.commitTransaction();
        } catch (SQLException e) {
            throw new DataAccessException("Insert nicht erfolgreich", e);
        }

    }

    public void insertUserNameInDeck(String username) {
        try {
            String sql = "INSERT INTO \"deck\" (username) VALUES (?)";

            PreparedStatement preparedStatement = this.unitOfWork.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.executeUpdate();
            unitOfWork.commitTransaction();
        } catch (SQLException e) {
            throw new DataAccessException("Insert nicht erfolgreich", e);
        }
    }
    public void changeUser(String username, User user) {

        try {
            String sql = "UPDATE benutzer SET name = ?, bio = ?, image = ? WHERE username = ?";

            PreparedStatement preparedStatement = this.unitOfWork.prepareStatement(sql);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getBio());
            preparedStatement.setString(3, user.getImage());
            preparedStatement.setString(4, username);
            preparedStatement.executeUpdate();
            unitOfWork.commitTransaction();
        } catch (SQLException e) {
            throw new DataAccessException("Update nicht erfolgreich", e);
        }
    }
    public void insertUsernameInStats(String username) {
        try {
            String sql = "INSERT INTO \"stats\" (username) VALUES (?)";

            PreparedStatement preparedStatement = this.unitOfWork.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.executeUpdate();
            unitOfWork.commitTransaction();
        } catch (SQLException e) {
            throw new DataAccessException("Insert nicht erfolgreich", e);
        }
    }

}