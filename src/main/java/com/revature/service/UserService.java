package com.revature.service;

import com.revature.entity.User;
import com.revature.exception.InvalidNewUserCredentials;
import com.revature.exception.LoginFail;
import com.revature.repository.UserDAO;

import java.util.List;

public class UserService {
    //handles communications with data access object

    private UserDAO userDAO;

    public UserService(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    public User createNewUser(User user){
        if(validCredentialLength(user)){
            if(validNewUsername(user)){
                return userDAO.createUser(user);
            }
        }
        throw new InvalidNewUserCredentials("Invalid new username or password, makes sure username and password < 30 characters, and username is unique");
    }

    public User checkLoginCredentials(User credentials){
        for(User user : userDAO.getAllUsers()){
            boolean usernameMatches = user.getUsername().equals(credentials.getUsername());
            boolean passwordMatches = user.getPassword().equals(credentials.getPassword());

            if(usernameMatches && passwordMatches) {
                credentials.setUserId(userDAO.getUserId(credentials));
                System.out.println("Login Success!\n");
                return credentials;
            }
        }

        throw new LoginFail("Credentials are invalid: please try again");
    }

    //helper method for debugging
    public void getAllUsers(){
        List<User> userList = userDAO.getAllUsers();
        for(User user : userList){
            System.out.println(user);
        }
    }

    //helper methods for readability
    private boolean validCredentialLength(User newUserCredential){
        boolean validUsername = newUserCredential.getUsername().length() <= 30;
        boolean validPassword = newUserCredential.getPassword().length() <= 30;
        return validUsername && validPassword;
    }
    private boolean validNewUsername(User newUserCredential){
        //check if username already exist in database
        //needs access to userDAO;
        List<User> users = userDAO.getAllUsers();
        for(User user: users ){
            if(newUserCredential.getUsername().equals(user.getUsername())){
                return false;
            }
        }
        return true;
    }
}
