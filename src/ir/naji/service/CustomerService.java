package ir.naji.service;

import ir.naji.dto.CustomerDto;
import ir.naji.entity.user.Customer;
import ir.naji.repository.CustomerRepo;
import ir.naji.util.ConverterUtil;

import java.util.List;

public class CustomerService {
    private final CustomerRepo customerRepo = CustomerRepo.getInstance();

    public void saveCustomer(CustomerDto dto) {
        Customer customer = ConverterUtil.toCustomerEntity(dto);
        customerRepo.addCustomer(customer);
    }

    public CustomerDto findById(String id) {
        return customerRepo.getAll().stream().filter(c -> c.getId().equals(id)).findFirst().map(ConverterUtil::toCustomerDto).orElse(null);
    }

    public Customer findEntityById(String id) {
        return customerRepo.getAll().stream().filter(c -> c.getId().equals(id)).findFirst().orElse(null);
    }

    public List<Customer> getAllCustomers() {
        return customerRepo.getAll();
    }
}
