package com.revature.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnector {
    //handle exception in service or some other layer
    public static Connection createConnection() throws SQLException {
        //copy resources/path/filepath/filename
        return DriverManager.getConnection("jdbc:sqlite:src/main/resources/bank.db");
    }

    public static void main(String[] args) {
        //best practice is to use try with resources block
        try{
            try(Connection connection = createConnection()){
                System.out.println();
            }

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
