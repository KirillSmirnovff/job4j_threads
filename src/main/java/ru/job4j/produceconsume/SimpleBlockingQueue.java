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
    private final int limit;

    public SimpleBlockingQueue(int limit) {
        this.limit = limit;
    }

    public synchronized void offer(T value) {
        while (queue.size() == limit && !Thread.currentThread().isInterrupted()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        queue.add(value);
        notify();
    }

    public synchronized T poll() {
        while (queue.size() == 0 && !Thread.currentThread().isInterrupted()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        T result = queue.poll();
        notify();
        return result;
    }

    public synchronized Queue<T> getQueue() {
        return new LinkedList<>(queue);
    }
}