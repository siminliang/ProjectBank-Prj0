package com.revature.repository;

import com.revature.entity.User;
import com.revature.exception.UserSQLException;
import com.revature.utility.DataBaseConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqliteUserDAO implements UserDAO{


    @Override
    public User createUser(User newUserCredentials) {
        String sql = "insert into users (username, password) values (?, ?)";
        try(Connection connection = DataBaseConnector.createConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, newUserCredentials.getUsername());
            preparedStatement.setString(2, newUserCredentials.getPassword());
            int result = preparedStatement.executeUpdate();
            if(result == 1){
                int user_id = preparedStatement.getGeneratedKeys().getInt(1);
                newUserCredentials.setUserId(user_id);
                return newUserCredentials;
            }

            throw new UserSQLException("User could not be created: please try again");
        }catch (SQLException exception) {
            throw new UserSQLException(exception.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {

        String sql = "select * from users";
        try(Connection connection = DataBaseConnector.createConnection()){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            List<User> users = new ArrayList<>();
            while(resultSet.next()){
                User userRecord = new User();
                //getString(column number) or (column name)
                userRecord.setUsername(resultSet.getString("username"));
                userRecord.setPassword(resultSet.getString("password"));
                userRecord.setUserId(resultSet.getInt("user_id"));
                users.add(userRecord);
            }
            return users;
        }catch (SQLException exception){
            throw new UserSQLException(exception.getMessage());
        }
    }

    @Override
    public int getUserId(User user) {
        String sql = "SELECT user_id FROM users WHERE username=? AND password=?";
        try(Connection connection = DataBaseConnector.createConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getInt("user_id");
            }
        }catch (SQLException exception){
            throw new UserSQLException(exception.getMessage());
        }
        return 0;
    }
}
