package com.revature.entity;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class Account implements Serializable {
    private String accountType;
    private User owner;
    private List<User> users;
    private double balance;
    private int account_id;

    public Account(String accountType, User owner, double balance){
        this.accountType = accountType;
        this.owner = owner;
        this.balance = balance;
    }

    public Account(String accountType, User owner){
        this.accountType =accountType;
        this.owner = owner;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public String getBalanceAsString(){
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
        return currencyFormatter.format(getBalance());
    }

    @Override
    public String toString() {
        String output = "Account{" +
                "account_id=" + account_id +
                ", accountType='" + accountType + '\'' +
                ", balance=" + getBalanceAsString();
        return  output +
                '}';
    }
}

