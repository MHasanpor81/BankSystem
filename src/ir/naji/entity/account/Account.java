package ir.naji.entity.account;

import ir.naji.entity.user.Customer;

public class Account {
    private String accountNumber;
    private AccountType accountType;
    private Customer owner;
    private int balance = 0;

    public Account(String accountNumber, AccountType accountType, Customer owner) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.owner = owner;
    }

    public String getAccountNumber() { return accountNumber; }
    public AccountType getAccountType() { return accountType; }
    public Customer getOwner() { return owner; }
    public double getBalance() { return balance; }

    public void deposit(int amount) { balance += amount; }
    public boolean withdraw(int amount) {
        if (balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }
    @Override
    public String toString() {
        return "Account{" +
                "accountNumber='" + accountNumber + '\'' +
                ", accountType=" + accountType +
                ", owner=" + (owner != null ? owner.getId() : "null") +
                ", balance=" + balance +
                '}';
    }
}
