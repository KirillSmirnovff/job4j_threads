package ru.job4j.produceconsume;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();
    private int limit = 10;

    public SimpleBlockingQueue(int limit) {
        this.limit = limit;
    }

    public SimpleBlockingQueue() {

    }

    public synchronized void offer(T value) throws InterruptedException {
        while (queue.size() == limit) {
            wait();
        }
        queue.add(value);
        notify();
    }

    public synchronized T poll() throws InterruptedException {
        while (queue.size() == 0) {
            wait();
        }
        T result = queue.poll();
        notify();
        return result;
    }

    public synchronized Queue<T> getQueue() {
        return new LinkedList<>(queue);
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
}