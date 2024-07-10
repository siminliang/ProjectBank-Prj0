package com.revature.controller;

import com.revature.entity.User;
import com.revature.exception.InvalidNewUserCredentials;
import com.revature.exception.LoginFail;
import com.revature.service.UserService;
import com.revature.service.UserStatus;

import java.util.Scanner;

public class UserController {
    private Scanner scanner;
    private UserService userService;
    private UserStatus userStatus;

    public UserController(Scanner scanner, UserService userService, UserStatus userStatus){
        this.scanner = scanner;
        this.userService = userService;
        this.userStatus = userStatus;
    }

    public void promptForServices(){
        System.out.println("What would you like to do today?");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("q. Exit");

        try{
            String userInput = scanner.nextLine();
            switch (userInput){
                case "1":
                    registerNewUser();
                    break;
                case "2":
                    userStatus.setUser(login());
                    break;
                case "q":
                    userStatus.setUser(null);
                    userStatus.setContinueLoop(false);
                    break;
            }
        } catch(LoginFail | InvalidNewUserCredentials exception){
            System.out.println(exception.getMessage());
        }
    }

    public void promptUserOptions(){
        System.out.println("What would you like to do today?");
        System.out.println("1. Account Summary");
        System.out.println("2. Open Account");
        System.out.println("3. Close Account");
        System.out.println("4. Withdraw");
        System.out.println("5. Deposit");
        System.out.println("q. Logout");

        try{
            String input = scanner.nextLine();
            switch (input){
                case "1":
                    break;
                case "2":
                    break;
                case "3":
                    break;
                case "4":
                    break;
                case "5":
                    break;
                case "q":
                    userStatus.setUser(null);
                    break;
            }
        } catch (RuntimeException exception){
            System.out.println(exception.getMessage());
        }
    }

    public User getCredentials(){
        String username;
        String password;
        System.out.println("Please enter a username: ");
        username = scanner.nextLine();
        System.out.println("Please enter a password: ");
        password = scanner.nextLine();

        return new User(username,password);
    }

    public void registerNewUser(){

        User newUserCredentials = getCredentials();
        try {
            User newUser = userService.validateNewUserCredentials(newUserCredentials);
            System.out.println("New account created: " + newUser.toString());
            System.out.println();
        }catch (InvalidNewUserCredentials exception){
            System.out.println(exception.getMessage());
            System.out.println("Press any key to continue or 'q' to quit registration");
            String input = scanner.nextLine();
            if(input.equals("q")) {
                System.out.println("Leaving Registrations... \n");
            } else
                registerNewUser();
        }
    }

    public User login(){
        return userService.checkLoginCredentials(getCredentials());
    }
}
