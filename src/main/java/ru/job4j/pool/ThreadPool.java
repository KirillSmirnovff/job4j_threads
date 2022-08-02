package ru.job4j.pool;

import ru.job4j.produceconsume.SimpleBlockingQueue;
import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>();

    public ThreadPool() {
        int size = Runtime.getRuntime().availableProcessors();
        for (int i = 0; i < size; i++) {
            Thread thread = new Thread(
                    () -> {
                        try {
                            while (!Thread.currentThread().isInterrupted()) {
                                tasks.poll().run();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
            );
            thread.start();
            threads.add(thread);
        }
    }

    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    public void shutdown() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }

    public static class SimpleJob implements Runnable {

        private final int count;

        public SimpleJob(int count) {
            this.count = count;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName());
            System.out.println(count);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPool pool = new ThreadPool();
        for (int i = 0; i < 50; i++) {
            pool.work(new SimpleJob(i));
        }
        Thread.sleep(5000);
        pool.shutdown();
    }
}