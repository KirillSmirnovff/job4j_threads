package ru.job4j.cas;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>();

    public void increment() {
        Integer ref;
        do {
            ref = count.get();
        } while (!count.compareAndSet(ref++, ref));
    }

    public int get() {
        Integer ref;
        do {
            ref = count.get();
        } while (count.get().compareTo(ref) != 0);
        return ref;
    }
}