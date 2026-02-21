package ir.naji.util;

import ir.naji.dto.AccountDto;
import ir.naji.dto.CustomerDto;
import ir.naji.entity.account.Account;
import ir.naji.entity.user.Customer;
import java.util.ArrayList;
import java.util.List;

public class ConverterUtil {
    public static Customer toCustomerEntity(CustomerDto dto) {
        if (dto == null) return null;
        return new Customer(dto.id(), dto.username(), "", dto.firstName(), dto.lastName(), new ArrayList<>());
    }

    public static CustomerDto toCustomerDto(Customer c) {
        return new CustomerDto(c.getId(), c.getUsername(), c.getFirstName(), c.getLastName(), toAccountDtoList(c.getAccounts()));
    }

    public static Account toAccountEntity(AccountDto dto, Customer owner) {
        return new Account(dto.accountNumber(), dto.accountType(), owner);
    }

    public static AccountDto toAccountDto(Account a) {
        return new AccountDto(a.getAccountNumber(), a.getAccountType());
    }

    public static List<AccountDto> toAccountDtoList(List<Account> accounts) {
        List<AccountDto> dtos = new ArrayList<>();
        if (accounts != null) for (Account a : accounts) dtos.add(toAccountDto(a));
        return dtos;
    }
}
