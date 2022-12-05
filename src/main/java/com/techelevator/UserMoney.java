package com.techelevator;

public class UserMoney {

    private double balance;

    public double getBalance() {
        return balance;
    }

    public void setBalance(double moneyFed) {
        balance += moneyFed;
    }
}
