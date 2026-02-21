package ir.naji.entity.account;

public enum AccountType {
    SHORT_TERM,
    LONG_TERM,
    CURRENT,
    QHASAN;

    //Return String to Account Type
    public static AccountType fromString(String input) {
        for (AccountType t : values()) {
            if (t.name().equalsIgnoreCase(input)) {
                return t;
            }
        }
        return null;
    }
}
