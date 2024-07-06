package ru.job4j.concurrent;

import java.util.concurrent.atomic.AtomicInteger;

public class Wget {
    public static void main(String[] args) {
        final int[] i = {0};
        Thread first = new Thread(
                () -> {
                    try {
                        while (i[0] <= 100) {
                            System.out.println("Loading: " + i[0] + "%");
                            i[0] = Math.incrementExact(i[0]);
                            Thread.sleep(2000);
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("File is download");
                }
        );
        first.start();
    }
}
