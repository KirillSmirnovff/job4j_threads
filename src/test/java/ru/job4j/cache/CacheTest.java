package ru.job4j.cache;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class CacheTest {

    @Test
    public void whenAddThenUpdate() {
        Cache cache = new Cache();
        Base one = new Base(1, 1);
        one.setName("One");
        cache.add(one);
        one.setName("One updated");
        cache.update(one);
        assertThat(one.getVersion()).isEqualTo(2);
        assertThat(one.getName()).isEqualTo("One updated");
    }

    @Test (expected = OptimisticException.class)
    public void whenVersionDifferenceThenException() {
        Cache cache = new Cache();
        Base one = new Base(1, 1);
        Base two = new Base(1, 2);
        one.setName("One");
        two.setName("Two");
        cache.add(one);
        cache.update(two);
    }

    @Test
    public void whenDelete() {
        Cache cache = new Cache();
        Base one = new Base(1, 1);
        Base two = new Base(1, 1);
        one.setName("One");
        two.setName("Two");
        cache.add(one);
        cache.delete(one);
        assertThat(cache.add(two)).isEqualTo(true);
    }
}