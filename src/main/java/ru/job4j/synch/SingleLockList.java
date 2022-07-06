package ru.job4j.synch;

import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@ThreadSafe
public class SingleLockList<T> implements Iterable<T> {
    private final List<T> list;

     public SingleLockList(List<T> list) {
         this.list = new ArrayList<>(list);
    }

    public SingleLockList() {
         this.list = new ArrayList<>();
    }

    public synchronized void add(T value) {
         list.add(value);
    }

    public synchronized T get(int index) {
        return list.get(index);
    }

    @Override
    public synchronized Iterator<T> iterator() {
        return copy(list).iterator();
    }

    private synchronized List<T> copy(List<T> list) {
         return List.copyOf(list);
    }
}