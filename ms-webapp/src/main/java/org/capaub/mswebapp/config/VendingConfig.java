package org.capaub.mswebapp.config;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class VendingConfig {
    public static final Map<Integer, String> NUM_TO_ALPHA = new HashMap<>();

    static {
        NUM_TO_ALPHA.put(1, "A");
        NUM_TO_ALPHA.put(2, "B");
        NUM_TO_ALPHA.put(3, "C");
        NUM_TO_ALPHA.put(4, "D");
        NUM_TO_ALPHA.put(5, "E");
        NUM_TO_ALPHA.put(6, "F");
        NUM_TO_ALPHA.put(7, "G");
        NUM_TO_ALPHA.put(8, "H");
        NUM_TO_ALPHA.put(9, "I");
        NUM_TO_ALPHA.put(10, "J");
        NUM_TO_ALPHA.put(11, "K");
        NUM_TO_ALPHA.put(12, "L");
    }
}