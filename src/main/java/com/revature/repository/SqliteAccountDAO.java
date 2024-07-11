package com.revature.repository;

import com.revature.entity.Account;
import com.revature.entity.AccountType;
import com.revature.entity.User;
import com.revature.utility.DataBaseConnector;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqliteAccountDAO implements AccountDAO{

    @Override
    public Account createAccount(AccountType accountType, User user) {

        try(Connection connection = DataBaseConnector.createConnection()){
            String createAccountSql = "INSERT INTO bank (account_type) VALUES (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(createAccountSql);
            preparedStatement.setString(1, accountType.toString());
            int result = preparedStatement.executeUpdate();
            int account_id = preparedStatement.getGeneratedKeys().getInt(1);

            if(result == 1){
                String linkToUserSql = "INSERT INTO user_account_joint (user_id, account_id) VALUES (?,?)";
                preparedStatement = connection.prepareStatement(linkToUserSql);
                preparedStatement.setInt(1,user.getUserId());
                preparedStatement.setInt(2, account_id);

                result = preparedStatement.executeUpdate();
                if(result == 1){
                    return new Account(accountType.toString(), user);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Account createAccount(AccountType accountType, User user, double balance) {
        try(Connection connection = DataBaseConnector.createConnection()){
            String createAccountSql = "INSERT INTO bank (account_type, balance) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(createAccountSql);
            preparedStatement.setString(1, accountType.toString());
            preparedStatement.setDouble(2, balance);
            int result = preparedStatement.executeUpdate();
            int account_id = preparedStatement.getGeneratedKeys().getInt("account_id");

            if(result == 1){
                String linkToUserSql = "INSERT INTO user_account_joint (user_id, account_id) VALUES (?,?)";
                preparedStatement = connection.prepareStatement(linkToUserSql);
                preparedStatement.setInt(1,user.getUserId());
                preparedStatement.setInt(2, account_id);

                result = preparedStatement.executeUpdate();
                if(result == 1){
                    return new Account(accountType.toString(), user, balance);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Account> getAllAccountsForUser(User user) {
  //public List<Account> getAllAccountsForUser(int user_id){
        List<Account> accountList = new ArrayList<>();
        try(Connection connection = DataBaseConnector.createConnection()){
            /*
            String sql = "SELECT b.account_type, b.balance FROM bank b " +
                         "JOIN user_account_joint uaj ON b.account_id = uaj.account_id " +
                         "WHERE uaj.user_id = ?";
             */
            String sql = "SELECT b.account_type, b.balance FROM bank b " +
                         "JOIN user_account_joint uaj ON b.account_id = uaj.account_id " +
                         "JOIN users u ON u.user_id = uaj.user_id " +
                         "WHERE u.username = ? AND u.password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
          //preparedStatement.setInt(1, user_id);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                String accountType = rs.getString(1);
                double balance = rs.getDouble(2);
                Account account = new Account(accountType, user, balance);
                accountList.add(account);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return accountList;
    }

    @Override
    public double getBalance() {
        return 0;
    }

    @Override
    public void deleteAccount() {

    }
}
