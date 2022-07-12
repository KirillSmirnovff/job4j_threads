package ru.job4j.produceconsume;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class SimpleBlockingQueueTest {

    @Test
    public void whenLimit2AndProducerOnly() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(2);
        Thread producer = new Thread(
                () -> {
                    for (int i = 1; i < 5; i++) {
                        try {
                            queue.offer(i);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        Thread interrupter = new Thread(
                () -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    producer.interrupt();
                }
        );
        producer.start();
        interrupter.start();
        interrupter.join();
        assertThat(queue.getQueue(), is(List.of(1, 2)));
    }

    @Test
    public void whenLimit2AndConsumerAndProducer() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(2);
        List<Integer> result = new ArrayList<>();
        Thread producer = new Thread(
                () -> {
                    for (int i = 1; i < 5; i++) {
                        try {
                            queue.offer(i);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        Thread consumer = new Thread(
                () -> {
                    for (int i = 1; i < 5; i++) {
                        try {
                            result.add(queue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertThat(result, is(List.of(1, 2, 3, 4)));
    }
}