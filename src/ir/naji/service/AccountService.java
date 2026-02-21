package ir.naji.service;

import ir.naji.dto.AccountDto;
import ir.naji.dto.CustomerDto;
import ir.naji.entity.account.Account;
import ir.naji.entity.user.Customer;
import ir.naji.repository.AccountRepo;
import ir.naji.util.ConverterUtil;

import java.util.List;

public class AccountService {
    private final AccountRepo accountRepo = AccountRepo.getInstance();

    public void saveAccount(AccountDto dto, CustomerDto customerDto) {

        CustomerService customerService = new CustomerService();
        Customer existing = customerService.findEntityById(customerDto.id());
        if (existing == null) {
            System.out.println("Customer not found, cannot open account.");
            return;
        }
        Account account = ConverterUtil.toAccountEntity(dto, existing);
        accountRepo.addAccount(account);
        existing.getAccounts().add(account);
    }

    public List<Account> getAccountsByCustomer(Customer customer) {
        return accountRepo.getAccountsByCustomer(customer);
    }

    public boolean deposit(CustomerDto customerDto, String accNo, int amount) {
        Customer c = ConverterUtil.toCustomerEntity(customerDto);
        for (Account a : accountRepo.getAccountsByCustomer(c)) {
            if (a.getAccountNumber().equals(accNo)) {
                a.deposit(amount);
                return true;
            }
        }
        return false;
    }

    public boolean withdraw(CustomerDto customerDto, String accNo, int amount) {
        Customer c = ConverterUtil.toCustomerEntity(customerDto);
        for (Account a : accountRepo.getAccountsByCustomer(c)) {
            if (a.getAccountNumber().equals(accNo)) {
                return a.withdraw(amount);
            }
        }
        return false;
    }

    public boolean transfer(CustomerDto customerDto, String srcAcc, String destAcc,int amount) {
        Customer c = ConverterUtil.toCustomerEntity(customerDto);
        Account source = null;
        for (Account a : accountRepo.getAccountsByCustomer(c)) {
            if (a.getAccountNumber().equals(srcAcc)) source = a;
        }
        if (source == null || source.getBalance() < amount) return false;
        Account destination = accountRepo.getAccounts().stream().filter(a -> a.getAccountNumber().equals(destAcc)).findFirst().orElse(null);
        if (destination == null) return false;

        source.withdraw(amount);
        destination.deposit(amount);
        return true;
    }

    public Double getBalance(CustomerDto customerDto, String accNo) {
        Customer c = ConverterUtil.toCustomerEntity(customerDto);
        for (Account a : accountRepo.getAccountsByCustomer(c)) {
            if (a.getAccountNumber().equals(accNo)) {
                return a.getBalance();
            }
        }
        return null;
    }
}
