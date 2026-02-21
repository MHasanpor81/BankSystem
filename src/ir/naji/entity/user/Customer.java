package ir.naji.entity.user;

import ir.naji.entity.account.Account;
import java.util.List;

public class Customer extends User {
    private String id;
    private String firstName;
    private String lastName;
    private List<Account> accounts;
    private boolean approved = false;
    private Integer age;
    private String address;

    public Customer(String id, String username, String password, String firstName, String lastName, List<Account> accounts) {
        super(username, password);
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.accounts = accounts;
    }

    public String getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public List<Account> getAccounts() { return accounts; }

    public boolean isApproved() { return approved; }
    public void setApproved(boolean approved) { this.approved = approved; }

    @Override
    public String toString() {
        return "Customer{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", approved=" + approved +
                ", accounts=" + accounts +
                '}';
    }
}
