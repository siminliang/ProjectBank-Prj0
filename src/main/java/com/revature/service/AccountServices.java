package com.revature.service;

import com.revature.entity.Account;
import com.revature.entity.AccountType;
import com.revature.entity.User;
import com.revature.exception.InvalidSelection;
import com.revature.repository.AccountDAO;

import java.util.List;

public class AccountServices {
    private AccountDAO accountDAO;

    public AccountServices(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    public List<Account> getAccountSummary(User user){
        return accountDAO.getAllAccountsForUser(user);
    }

    public void createAccount(AccountType accountType, User user){
        if(user != null){
            Account account = accountDAO.createAccount(accountType,user);
            System.out.println("Account Created! \n" + account.toString() + "\n");
        }else
            throw new RuntimeException("No User Logged in");
    }

    public void deleteAccount(Account account){
        if(account != null) {
            System.out.println("you have chosen to delete account: " + account);
            accountDAO.deleteAccountById(account.getAccount_id());
            System.out.println("Account Successfully Deleted!");
        }else
            throw new InvalidSelection("No such account.");
    }

    public boolean validBalance(double balance){
        return balance >= 0;
    }

    //savings account withdraw under 500 gets addition
    public Account withdraw(Account account, double amount){
        double newBalance = account.getBalance() - amount;
        if("CHECKING".equals(account.getAccountType())){
            if(validBalance(newBalance))
                accountDAO.updateAccountBalance(account, newBalance);
            else{
                System.out.println("Unable to withdraw, balance cannot be lower than 0");
                System.out.println("Current account balance: " + account.getBalance());
            }
        }else if("SAVING".equals(account.getAccountType())){
            //every withdraw from savings account has a 5 dollar fee
            newBalance = newBalance - 5;
            if(validBalance(newBalance)){
                accountDAO.updateAccountBalance(account, newBalance);
            }else{
                System.out.println("Unable to withdraw, balance cannot be lower than 0");
                System.out.println("Current account balance: " + account.getBalance());
                System.out.println("Withdrawing from savings accounts incurs a $5 fee.");
            }
        }
        return account;
    }
}
