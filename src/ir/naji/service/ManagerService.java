package ir.naji.service;

import ir.naji.repository.CustomerRepo;
import ir.naji.repository.AccountRepo;
import ir.naji.repository.ManagerRepo;
import ir.naji.entity.user.employee.Manager;

public class ManagerService {
    private final CustomerRepo customerRepo = CustomerRepo.getInstance();
    private final AccountRepo accountRepo = AccountRepo.getInstance();
    private final ManagerRepo managerRepo = ManagerRepo.getInstance();

    public void showAllCustomers() {
        customerRepo.getAll().forEach(System.out::println);
    }

    public double getTotalBankBalance() {
        return accountRepo.getAccounts().stream().mapToDouble(a -> a.getBalance()).sum();
    }

    public void approveCustomer(String id) {
        customerRepo.getAll().stream().filter(c -> c.getId().equals(id)).findFirst().ifPresentOrElse(c -> {
                    c.setApproved(true);
                    System.out.println("Customer approved: " + c.getUsername());
                    }, () -> System.out.println("Customer not found."));
    }

    public void linkManagerToCustomer(String managerId, String customerId) {
        Manager m = managerRepo.getAll().stream().filter(x -> x.getId().equals(managerId)).findFirst().orElse(null);
        if (m != null) {
            m.setLinkedCustomerId(customerId);
            managerRepo.updateManager(m);
        }
    }
}
