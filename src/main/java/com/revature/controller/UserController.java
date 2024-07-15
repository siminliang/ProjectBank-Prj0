package com.revature.controller;

import com.revature.entity.Account;
import com.revature.entity.AccountType;
import com.revature.entity.User;
import com.revature.exception.InvalidBalanceException;
import com.revature.exception.InvalidNewUserCredentials;
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

    private final UserStatus userStatus;
    private final AccountServices accountServices;
    private final Scanner scanner;
    private final UserService userService;

    public UserController(Scanner scanner, UserStatus userStatus){
        UserDAO userDAO = new SqliteUserDAO();
        AccountDAO accountDAO = new SqliteAccountDAO();
        this.userService = new UserService(userDAO);
        this.accountServices = new AccountServices(accountDAO);
        this.scanner = scanner;
        this.userStatus = userStatus;
    }

    public void promptForCredentials(){
        System.out.println("What would you like to do today?");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("q. Exit");

        try{
            String userInput = scanner.nextLine();
            switch (userInput){
                case "1":
                    registerNewUser();
                    pause();
                    break;
                case "2":
                    login();
                    pause();
                    break;
                case "q":
                    quit();
                    break;
                case "viewUsers":
                    getAllUsers();
                    break;
                default:
                    System.out.println("Invalid option selected, please try again");
                    promptForCredentials();
                    break;
            }
        } catch(LoginFail | InvalidNewUserCredentials exception){
            System.out.println(exception.getMessage());
        }
    }

    public void promptAccountOptions(){
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
                    pause();
                    break;
                case "2":
                    createAccount();
                    pause();
                    break;
                case "3":
                    closeAccount();
                    pause();
                    break;
                case "4":
                    withdraw();
                    pause();
                    break;
                case "5":
                    deposit();
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
        if(accountList.isEmpty())
            System.out.println("No account associated with this user found");
        System.out.println();
    }

    private void createAccount(){
        System.out.println("What type of Account would you like to create?");
        System.out.println("1. CHECKING");
        System.out.println("2. SAVING");
        System.out.println("3. JOINT");
        System.out.println("4. VIEW ACCOUNT BENEFITS");
        System.out.println("q. QUIT");
        try{
            String input = scanner.nextLine();
            switch (input){
                case "1":
                    accountServices.createAccount(AccountType.CHECKING, userStatus.getUser());
                    break;
                case "2":
                    accountServices.createAccount(AccountType.SAVING, userStatus.getUser());
                    break;
                case "3":
                    jointAccount();
                    break;
                case "4":
                    viewBenefits();
                    break;
                case "q":
                    break;
                default:
                    System.out.println("Invalid Selection, please retry!");
                    createAccount();
            }
        }catch (RuntimeException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void closeAccount(){
        Account account = selectAccount();
        if(account != null)
            accountServices.deleteAccount(account);
    }

    private void withdraw(){
        Account account = selectAccount();
        try{
            if(account != null) {
                if("SAVING".equals(account.getAccountType())){
                    System.out.println("Withdrawing from a savings account incurs a heavy penalty fee are you sure you want to proceed Y/N: ");
                    String yn = scanner.nextLine();
                    if(!yn.equals("y"))
                        return;
                }
                System.out.print("Enter the amount you want to withdraw: $");
                double input = getTransactionAmount();
                account = accountServices.withdraw(account, input);
            }
            else{
                System.out.println("Exiting withdraw... \n");
            }
            if(account != null)
                System.out.println("Your new account balance is: " + account.getBalanceAsString() + "\n");
        }catch (InvalidBalanceException exception){
            System.out.println();
            withdraw();
        }
    }

    private void deposit(){
        Account account = selectAccount();
        try{
            if(account != null) {
                System.out.print("Enter the amount you want to deposit: $");
                double input = getTransactionAmount();
                account = accountServices.deposit(account, input);
            } else {
                System.out.println("Exiting deposit... \n");
            }
            if(account != null)
                System.out.println("Your new account balance is: " + account.getBalanceAsString() + "\n");
        }catch (RuntimeException exception){
            System.out.println(exception.getMessage());
            deposit();;
        }
    }

    private double getTransactionAmount(){
        String input = scanner.nextLine();
        double output = -1;
        try {
            output = Double.parseDouble(input);
        } catch (NumberFormatException exception){
            System.out.println("Invalid transaction amount, please try again");
            return getTransactionAmount();
        }
        if(output < 0) {
            System.out.println("Invalid transaction amount, please try again");
            return getTransactionAmount();
        }
        return output;
    }

    private Account selectAccount(){
        List<Account> accountList = accountServices.getAccountSummary(userStatus.getUser());
        System.out.println("\nPlease select an account");
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
                return selectAccount();
            } else {
                return accountList.get(input - 1);
            }
        } catch (NumberFormatException exception){
            System.out.println("Invalid input. Please enter a valid option");
            return selectAccount();
        } catch (RuntimeException exception){
            System.out.println(exception.getMessage());
            return selectAccount();
        }
    }

    private void getAllUsers(){
        userService.getAllUsers();
    }

    private void quit(){
        userStatus.setUser(null);
        userStatus.setContinueLoop(false);
    }

    private void viewBenefits(){
        String checkingBenefits = "CHECKING ACCOUNT: \n -No benefits, deposit and withdraw has no bonuses or penalties. \n\n";
        String savingBenefits = "SAVING ACCOUNT: \n -For every deposit over $1000 get a $5 bonus. \n" +
                " -Get a $500 penalty fee for every withdraw. \n\n";
        String jointAccount = "JOINT ACCOUNT: \n -Add a user to one of your existing accounts. \n";
        System.out.println(checkingBenefits + savingBenefits + jointAccount);
    }
    private void pause(){
        String green = "\u001B[32m";
        String reset = "\u001B[0m";
        System.out.println(green + "Press any key to continue" + reset);
        scanner.nextLine();
        System.out.println("\n\n\n\n\n");
    }

    private void jointAccount(){
        System.out.println("Alright lets start adding a user to one of your accounts");
        System.out.println("Please select an account in which you want to add a new user to");
        Account accountToBeJoined = this.selectAccount();
        if(accountToBeJoined != null){
            System.out.println("Please enter credentials for an existing user");
            User newUser = userService.checkLoginCredentials(getCredentials());
            if(newUser != null){
                accountServices.createJointAccount(accountToBeJoined, newUser);

            } else
                System.out.println("Invalid user credential provided, makes sure they are a registered user.");
        } else {
            System.out.println("Exiting account linking...");
        }
    }
}
