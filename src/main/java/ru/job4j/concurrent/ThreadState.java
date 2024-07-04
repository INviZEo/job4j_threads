package ru.job4j.concurrent;

public class ThreadState {
    public static void main(String[] args) {
        Thread first = new Thread(
                () -> {
                }
        );
        System.out.println(first.getState());
        first.start();
        System.out.println(first.getName());
        Thread second = new Thread(
                () -> {
                }
        );
        System.out.println(second.getName());
        while (first.getState() != Thread.State.TERMINATED) {
            System.out.println(first.getState());
        }
        try {
            first.join();
            second.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(first.getState());
        System.out.println("Process is finish");
    }
}