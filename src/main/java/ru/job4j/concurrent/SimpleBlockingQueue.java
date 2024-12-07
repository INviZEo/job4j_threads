package ru.job4j.concurrent;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();

    private final Object blockQueue = new SimpleBlockingQueue<Integer>();

    public void offer(T value) {
        synchronized (blockQueue) {
            while (queue.size() == 3)
                try {
                    blockQueue.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            queue.offer(value);
            blockQueue.notifyAll();
        }
    }

    public T poll() throws InterruptedException {
        synchronized (blockQueue) {
            while (queue.isEmpty()) {
                try {
                    blockQueue.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        blockQueue.notifyAll();
        return queue.poll();
    }
}