package com.revature;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        try(Scanner scanner = new Scanner(System.in)){
            //UserDAO handle access persisting User data


            //UserService handle validating User data and follow software + business rules
            //UserService needs access to DAO to transfer data to repo layer


            //UserController handle receiving and returning data to the user
            //UserController needs access to service to transfer data to service layer


        }
    }
}
