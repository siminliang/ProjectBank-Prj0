package com.revature;

import com.revature.controller.UserController;
import com.revature.service.UserStatus;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        try(Scanner scanner = new Scanner(System.in)){
            UserStatus userStatus = new UserStatus();
            //main class only needs access to Controller layer
            //service layer initialized inside controller layer
            UserController userController = new UserController(scanner, userStatus);

            while(userStatus.getContinueLoop()){
                if(userStatus.getUser() == null)
                    userController.promptForCredentials();

                if(userStatus.getUser() != null) {
                    System.out.println("Welcome to Imaginary Bank! User: " + userStatus.getUser().getUsername() + "!");
                    userController.promptAccountOptions();
                }
            }
        }
    }
}
