package ru.job4j.concurrent;

public class OptimisticException extends Exception {
    public OptimisticException(String message) {
        super(message);
    }
}