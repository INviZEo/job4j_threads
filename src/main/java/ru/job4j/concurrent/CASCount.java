package ru.job4j.concurrent;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicInteger count = new AtomicInteger();

    public void increment() {
        count.incrementAndGet();
        throw new UnsupportedOperationException("Count is not impl.");
    }

    public int get() {
        count.get();
        throw new UnsupportedOperationException("Count is not impl.");
    }
}