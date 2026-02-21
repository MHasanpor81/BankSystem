package ir.naji.repository;

import ir.naji.entity.user.Customer;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepo {
    private static final CustomerRepo INSTANCE = new CustomerRepo();
    private final List<Customer> customers = new ArrayList<>();
    private CustomerRepo() {}
    public static CustomerRepo getInstance() { return INSTANCE; }
    public void addCustomer(Customer c) { customers.add(c); }
    public List<Customer> getAll() { return customers; }
}
