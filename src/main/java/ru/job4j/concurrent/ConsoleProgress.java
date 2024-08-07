package ru.job4j.concurrent;

import java.lang.Runnable;

public class ConsoleProgress implements Runnable {

    @Override
    public void run() {
        var process = new char[]{'-', '\\', '|', '/'};
        int i = 0;
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.print("\r Loading" + process[i++]);
            if (i == process.length) {
                i = 0;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(5000);
        progress.interrupt();
    }
}