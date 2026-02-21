package ir.naji.repository;

import ir.naji.entity.account.Account;
import ir.naji.entity.user.Customer;
import java.util.ArrayList;
import java.util.List;

public class AccountRepo {
    private static final AccountRepo INSTANCE = new AccountRepo();
    private AccountRepo() {}
    public static AccountRepo getInstance() { return INSTANCE; }
    private final List<Account> accounts = new ArrayList<>();
    public void addAccount(Account a) { accounts.add(a); }
    public List<Account> getAccounts() { return accounts; }
    public List<Account> getAccountsByCustomer(Customer c) {
        List<Account> result = new ArrayList<>();
        for (Account a : accounts) {
            if (a.getOwner() != null && a.getOwner().getId().equals(c.getId())){
                result.add(a);
            }
        }
        return result;
    }
}
