package ir.naji.util;

public class AccountNumberGenerator {
    private static int counter = 100;

    public static synchronized String generate() {
        return "AC" + (counter++);
    }
}
