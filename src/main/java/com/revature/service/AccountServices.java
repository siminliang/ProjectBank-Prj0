package com.revature.service;

import com.revature.entity.Account;
import com.revature.entity.AccountType;
import com.revature.entity.User;
import com.revature.exception.InvalidNewUserCredentials;
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

    public Account createAccount(AccountType accountType, User user){
        if(user != null){
            Account account = accountDAO.createAccount(accountType,user);
            System.out.println("Account Created! \n" + account.toString());
            return account;
        }
        throw new RuntimeException("No User Logged in");
    }

    public void deleteAccount(Account account){
        if(account != null) {
            accountDAO.deleteAccountById(account.getAccount_id());
            System.out.println("Account Successfully Deleted!");
        }else
            throw new InvalidSelection("No such account.");
    }

    public boolean validBalance(double balance){
        return balance >= 0;
    }
}
