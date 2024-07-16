package com.revature.repository;

import com.revature.entity.Account;
import com.revature.entity.AccountType;
import com.revature.entity.User;

import java.util.List;

public interface AccountDAO {
    List<Account> getAllAccountsForUser(User user);
    Account createAccount(AccountType accountType, User user);
    void deleteAccountById(int account_id);
    Account updateAccountBalance(Account account, double newBalance);
    void jointAccounts(int account_id, int user_id);
}