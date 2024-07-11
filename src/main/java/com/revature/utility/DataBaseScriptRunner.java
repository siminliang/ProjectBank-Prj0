package com.revature.utility;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Stream;

public class DataBaseScriptRunner {
    public static void main(String[] args) {

        //Paths has a get method that lets us return a file path as a Path object
        //so we can then provide to the files class in order to streamline returning a Stream of String data
        //that is the SQL we want to execute
        Path sqlPath = Paths.get("src/main/resources/bank_setup.sql");
        try{
            // create our connection object in our try with resources block
            try(Connection connection = DataBaseConnector.createConnection();
                // create a Stream that has our sql lines saved as String data
                Stream<String> lines = Files.lines(sqlPath)){

                connection.setAutoCommit(false);
                StringBuilder sqlBuilder =  new StringBuilder();
                //this line loops through our steam and appends each line of the file
                //to the StringBuilder object
                lines.forEach(sqlBuilder::append);
                //lines.forEach(line -> sqlBuilder.append(line));
                /*
                    similar to
                    for(String line : lines){}
                 */
                String sql = sqlBuilder.toString();
                // we can now split the sql into individual statements
                // the \\R character is a more robust newline indicator
                // this means new lines, carriage return, and one or two other
                // characters are referenced by it
                String[] statements = sql.split(";\\R");
                for(String statement : statements) {
                    Statement stmt = connection.createStatement();
                    stmt.executeUpdate(statement);
                }
                connection.commit();

            }
        // need to catch potential SQL and IO Exceptions
        } catch (SQLException | IOException exception){
            System.out.println(exception.getMessage());
        }
    }
}
