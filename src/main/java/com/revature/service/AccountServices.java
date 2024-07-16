package com.revature.service;

import com.revature.entity.Account;
import com.revature.entity.AccountType;
import com.revature.entity.User;
import com.revature.exception.InvalidBalanceException;
import com.revature.exception.InvalidSelection;
import com.revature.repository.AccountDAO;

import java.util.List;

public class AccountServices {
    private AccountDAO accountDAO;

    private static final double DEPOSIT_BONUS_THRESHOLD = 1000;
    private static final double BONUS_AMOUNT = 5;
    private static final double WITHDRAWAL_FEE = 500;

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
            System.out.println("you have chosen to close account: " + account);
            accountDAO.deleteAccountById(account.getAccount_id());
            System.out.println("\nAccount Successfully Closed! \n");
        }else
            throw new InvalidSelection("No such account.");
    }

    public Account withdraw(Account account, double amount){
        double newBalance = account.getBalance() - amount;
        double initBalance = account.getBalance();
        if("CHECKING".equals(account.getAccountType())){
            if(validBalance(newBalance))
                account = accountDAO.updateAccountBalance(account, newBalance);
            else{
                System.out.println("Unable to withdraw, balance cannot be lower than 0");
                System.out.println("Current account balance: " + account.getBalanceAsString());
                throw new InvalidBalanceException("Invalid end balance");
            }
        }else if("SAVING".equals(account.getAccountType())){
            //withdraws incurs a withdrawal fee
            if(amount > 0){
                newBalance = newBalance - WITHDRAWAL_FEE;
            }
            if(validBalance(newBalance)){
                account = accountDAO.updateAccountBalance(account, newBalance);
                double sum = amount + WITHDRAWAL_FEE;
                System.out.println("Initial Balance: $" + initBalance);
                System.out.println("Amount requested + Withdrawal Fee: $" + amount + " + $" + WITHDRAWAL_FEE + " = $" + sum);
            }else{
                System.out.println("Unable to withdraw, insufficient balance.");
                System.out.println("Current account balance: " + account.getBalanceAsString());
                System.out.println("\nNOTE: Withdrawing from savings accounts incurs a $"+ WITHDRAWAL_FEE +"0 fee.\n");
            }
        }
        return account;
    }

    public Account deposit(Account account, double amount){
        double newBalance = account.getBalance() + amount;
        if("CHECKING".equals(account.getAccountType())){
            if(validBalance(newBalance))
                account = accountDAO.updateAccountBalance(account, newBalance);
            else{
                throw new InvalidBalanceException("Invalid balance entered.");
            }
        }else if("SAVING".equals(account.getAccountType())){
            //deposits over $1000 gets a bonus $5 (BONUS_AMOUNT)
            if(amount >= DEPOSIT_BONUS_THRESHOLD){
                newBalance = newBalance + BONUS_AMOUNT;
                System.out.println("Congratulations you have reached the bonus threshold! \nA $" + BONUS_AMOUNT +
                        " bonus has been added to your account");
            }
            if(validBalance(newBalance)){
                account = accountDAO.updateAccountBalance(account, newBalance);
            }
        }
        return account;
    }

    public void createJointAccount(Account account, User newUser){
        int account_id = account.getAccount_id();
        int user_id = newUser.getUserId();
        accountDAO.jointAccounts(account_id, user_id);
    }

    //helper method, for readability
    private boolean validBalance(double balance){
        return balance >= 0;
    }
}

