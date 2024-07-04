package ru.job4j.concurrent;

import java.util.concurrent.atomic.AtomicInteger;

public class Wget {
    public static void main(String[] args) {
        AtomicInteger i = new AtomicInteger();
        Thread first = new Thread(
                () -> {
                    try {
                        while (i.get() <= 100) {
                            System.out.println("Loading: " + i.getAndIncrement() + "%");
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
