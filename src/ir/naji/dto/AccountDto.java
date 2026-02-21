package ir.naji.dto;

import ir.naji.entity.account.AccountType;

public record AccountDto(String accountNumber, AccountType accountType) {}
