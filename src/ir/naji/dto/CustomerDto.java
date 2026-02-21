package ir.naji.dto;

import java.util.List;

public record CustomerDto(String id, String username, String firstName, String lastName, List<AccountDto> accounts) {}
