package com.revature;

import com.revature.controller.UserController;
import com.revature.service.UserStatus;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        try(Scanner scanner = new Scanner(System.in)){
            UserStatus userStatus = new UserStatus();
            UserController userController = new UserController(scanner, userStatus);

            while(userStatus.getContinueLoop()){
                if(userStatus.getUser() == null)
                    userController.promptForCredentials();

                if(userStatus.getUser() != null) {
                    System.out.println("Welcome to Bank! User: " + userStatus.getUser().getUsername() + "!");
                    userController.promptAccountOptions();
                }
            }
        }
    }
}
