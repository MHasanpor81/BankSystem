package ir.naji.util;

import ir.naji.entity.account.AccountType;
import ir.naji.repository.AccountRepo;

public class ValidationUtil {

    public static boolean isValidName(String name) {
        return name != null && name.matches("^[A-Za-z]{2,}$");
    }

    public static boolean isValidId(String id) {
        return id != null && id.matches("\\d+");
    }

    public static boolean isValidAccountType(String type) {
        try {
            AccountType.fromString(type);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public static boolean isValidAmount(double amount) {
        return amount > 0;
    }

    public static boolean doesAccountExist(String accNo) {
        return AccountRepo.getInstance().getAccounts().stream()
                .anyMatch(a -> a.getAccountNumber().equals(accNo));
    }
}
