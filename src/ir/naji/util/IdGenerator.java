package ir.naji.util;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class IdGenerator {
    private static final HashMap<String, AtomicInteger> prefixCounters = new HashMap<>();

    public static String generate(String prefix) {
        prefixCounters.putIfAbsent(prefix, new AtomicInteger(1000));
        int next = prefixCounters.get(prefix).getAndIncrement();
        return prefix + next;
    }
}
