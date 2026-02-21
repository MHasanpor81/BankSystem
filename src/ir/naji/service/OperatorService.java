package ir.naji.service;

import ir.naji.dto.AccountDto;
import ir.naji.dto.CustomerDto;
import ir.naji.entity.user.employee.Operator;
import ir.naji.repository.OperatorRepo;

public class OperatorService {
    private final CustomerService customerService;
    private final AccountService accountService;
    private final OperatorRepo operatorRepo = OperatorRepo.getInstance();

    public OperatorService(CustomerService cs, AccountService as) {
        this.customerService = cs;
        this.accountService = as;
    }

    public void createCustomer(CustomerDto dto) {
        customerService.saveCustomer(dto);
    }

    public void openAccountForCustomer(AccountDto accountDto, CustomerDto customerDto) {
        accountService.saveAccount(accountDto, customerDto);
    }

    public void linkOperatorToCustomer(String operatorId, String customerId) {
        Operator op = operatorRepo.getAll().stream().filter(o -> o.getId().equals(operatorId)).findFirst().orElse(null);
        if (op != null) {
            op.setLinkedCustomerId(customerId);
            operatorRepo.updateOperator(op);
        }
    }
}
