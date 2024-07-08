package ru.job4j.concurrent;

import java.lang.Runnable;

public class ConsoleProgress {
    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(
                () -> {
                    var process = new char[] {'-', '\\', '|', '/'};
                    int i = 0;
                   while (!Thread.currentThread().isInterrupted()) {
                       System.out.print("\r Loading" + process[i++]);
                       if (i == 4) {
                           i = 0;
                       }
                   }
                }
        );
        progress.start();
        Thread.sleep(5000);
        progress.interrupt();
    }
}