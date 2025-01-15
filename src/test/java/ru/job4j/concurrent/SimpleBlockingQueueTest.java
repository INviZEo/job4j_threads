package ru.job4j.concurrent;

import org.junit.Test;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleBlockingQueueTest {

    @Test
    public void testProducerConsumer() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);

        Thread producer = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    System.out.println("Producing: " + i);
                    queue.offer(i);
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });


        Thread consumer = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    Integer value = queue.poll();
                    System.out.println("Consuming: " + value);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();
    }

        @Test
        public void whenFetchAllThenGetIt() throws InterruptedException {
            final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
            final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
            Thread producer = new Thread(
                    () -> {
                        IntStream.range(0, 5).forEach(
                                value -> {
                                    try {
                                        queue.offer(value);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                        );
                    }
            );
            producer.start();
            Thread consumer = new Thread(
                    () -> {
                        while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                            try {
                                buffer.add(queue.poll());
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                Thread.currentThread().interrupt();
                            }
                        }
                    }
            );
            consumer.start();
            producer.join();
            consumer.interrupt();
            consumer.join();
            assertThat(buffer).containsExactly(0, 1, 2, 3, 4);
        }
}