package com.page5of4.codon.utils;

public class ThreadUtils {
    private ThreadUtils() {

    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
        }
    }
}
