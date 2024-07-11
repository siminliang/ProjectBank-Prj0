package com.revature.service;

import com.revature.entity.Account;
import com.revature.entity.AccountType;
import com.revature.entity.User;
import com.revature.repository.AccountDAO;

import java.util.List;

public class AccountServices {
    private AccountDAO accountDAO;

    public AccountServices(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    public List<Account> getAccountSummary(User user){
        List<Account> accountList = accountDAO.getAllAccountsForUser(user);
        for(Account account : accountList){
            System.out.println(account);
        }
        return accountList;
    }

    public Account createAccount(AccountType accountType, User user){
        if(user != null){
            return accountDAO.createAccount(accountType, user);
        }
        throw new RuntimeException("No User Logged in");
    }

    public boolean validBalance(double balance){
        return balance >= 0;
    }
}
