package com.revature.repository;

import com.revature.entity.Account;
import com.revature.entity.AccountType;
import com.revature.entity.User;

import java.util.List;

public interface AccountDAO {
    Account createAccount(AccountType accountType, User user);
    Account createAccount(AccountType accountType, User user, double balance);
    List<Account> getAllAccountsForUser(User user);
    double getBalance(Account account);
    void deleteAccountById(int account_id);
    Account updateAccountBalance(Account account, double newBalance);

}