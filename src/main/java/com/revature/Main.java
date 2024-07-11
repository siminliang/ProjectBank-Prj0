package com.revature;

import com.revature.controller.UserController;
import com.revature.repository.AccountDAO;
import com.revature.repository.SqliteAccountDAO;
import com.revature.repository.SqliteUserDAO;
import com.revature.repository.UserDAO;
import com.revature.service.AccountServices;
import com.revature.service.UserStatus;
import com.revature.service.UserService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        try(Scanner scanner = new Scanner(System.in)){
            UserStatus userStatus = new UserStatus();
            UserController userController = new UserController(scanner, userStatus);

            while(userStatus.getContinueLoop()){
                if(userStatus.getUser() == null)
                    userController.promptForServices();

                if(userStatus.getUser() != null) {
                    System.out.println("Welcome to Bank! User: " + userStatus.getUser().getUsername() +
                            "!\nPress any key to continue");
                    scanner.nextLine();
                    userController.promptUserOptions();
                }
            }
        }
    }
}
