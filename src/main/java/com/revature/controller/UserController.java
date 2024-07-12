package com.revature.controller;

import com.revature.entity.Account;
import com.revature.entity.AccountType;
import com.revature.entity.User;
import com.revature.exception.InvalidNewUserCredentials;
import com.revature.exception.InvalidSelection;
import com.revature.exception.LoginFail;
import com.revature.repository.AccountDAO;
import com.revature.repository.SqliteAccountDAO;
import com.revature.repository.SqliteUserDAO;
import com.revature.repository.UserDAO;
import com.revature.service.AccountServices;
import com.revature.service.UserService;
import com.revature.service.UserStatus;

import java.util.List;
import java.util.Scanner;

public class UserController {

    private UserStatus userStatus;
    private AccountServices accountServices;
    private Scanner scanner;
    private UserService userService;

    public UserController(Scanner scanner, UserStatus userStatus){
        UserDAO userDAO = new SqliteUserDAO();
        AccountDAO accountDAO = new SqliteAccountDAO();
        this.userService = new UserService(userDAO);
        this.accountServices = new AccountServices(accountDAO);
        this.scanner = scanner;
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
                    login();
                    break;
                case "q":
                    quit();
                    break;
                case "viewUsers":
                    getAllUsers();

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
                    printSummary();
                    break;
                case "2":
                    createAccount();
                    break;
                case "3":
                    closeAccount();
                    break;
                case "4":
                    withdraw();
                    break;
                case "5":
                    //deposite();
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

    public void login(){
        userStatus.setUser(userService.checkLoginCredentials(getCredentials()));
    }

    private void printSummary(){
        List<Account> accountList = accountServices.getAccountSummary(userStatus.getUser());
        for(Account account : accountList){
            System.out.println(account);
        }
        System.out.println();
    }

    private void createAccount(){
        System.out.println("What type of Account would you like to create?");
        System.out.println("1. CHECKING");
        System.out.println("2. SAVING");
        System.out.println("4. JOINT");
        try{
            String input = scanner.nextLine();
            switch (input){
                case "1":
                    accountServices.createAccount(AccountType.CHECKING, userStatus.getUser());
                    break;
                case "2":
                    accountServices.createAccount(AccountType.SAVING, userStatus.getUser());
                    break;
                case "q":
                    break;
            }
        }catch (RuntimeException exception){
            System.out.println(exception.getMessage());
        }
    }

    private void closeAccount(){
        Account account = promptAccountSelection();
        if(account != null)
            accountServices.deleteAccount(account);
    }

    private void withdraw(){

    }

    private void deposit(){

    }

    private Account promptAccountSelection(){
        List<Account> accountList = accountServices.getAccountSummary(userStatus.getUser());
        System.out.println("Which account would you like to close");
        for(int i = 0; i < accountList.size(); i++) {
            System.out.println((i+1) + ". " + accountList.get(i));
        }
        System.out.println("q. quit");
        try {
            String str = scanner.nextLine();
            if(str.equals("q")){
                return null;
            }

            int input = Integer.parseInt(str);
            if (input < 1 || input > accountList.size()) {
                System.out.println("Invalid selection. Please enter a number between 1 and " + accountList.size() + ", or 'q' to quit:");
                promptAccountSelection();
            } else {
                return accountList.get(input - 1);
            }
        } catch (NumberFormatException exception){
            System.out.println("Invalid input. Please enter a valid option");
        } catch (RuntimeException exception){
            System.out.println(exception.getMessage());
        }
        return null;
    }

    private void getAllUsers(){
        userService.getAllUsers();
    }

    private void quit(){
        userStatus.setUser(null);
        userStatus.setContinueLoop(false);
    }
}
