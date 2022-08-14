package ru.job4j.pool;

import java.util.Objects;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelIndexSearch<T> extends RecursiveTask<Integer> {

    private final T value;
    private final T[] array;
    private final int from;
    private final int to;

    public ParallelIndexSearch(T value, T[] array, int from, int to) {
        this.value = value;
        this.array = array;
        this.from = from;
        this.to = to;
    }

    @Override
    protected Integer compute() {
        if (to - from < 10) {
            int result = -1;
            for (int i = from; i <= to; i++) {
                if (Objects.equals(array[i], value)) {
                    result = i;
                    break;
                }
            }
            return result;
        }
        int mid = (to + from) / 2;
        ParallelIndexSearch<T> leftSearch = new ParallelIndexSearch<>(value, array, from, mid);
        ParallelIndexSearch<T> rightSearch = new ParallelIndexSearch<>(value,  array, mid + 1, to);
        leftSearch.fork();
        rightSearch.fork();
        int left = leftSearch.join();
        int right = rightSearch.join();
        return Math.max(left, right);
    }

    public static <T> int getIndex(T value, T[] array) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelIndexSearch<>(value, array, 0, array.length - 1));
    }
}
