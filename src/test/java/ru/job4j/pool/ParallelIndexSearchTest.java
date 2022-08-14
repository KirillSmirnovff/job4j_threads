package ru.job4j.pool;

import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class ParallelIndexSearchTest {

    @Test
    public void whenIntegerMoreThen10Elements() {
        Integer[] array = new Integer[50];
        for (int i = 0; i < 50; i++) {
            array[i] = i * 2;
        }
        assertThat(ParallelIndexSearch.getIndex(98, array)).isEqualTo(49);
    }

    @Test
    public void whenStringLessThen10Elements() {
        List<String> list = List.of("one", "two", "three", "four", "five", "six", "seven", "eight", "nine");
        String[] array = list.toArray(new String[9]);
        assertThat(ParallelIndexSearch.getIndex("six", array)).isEqualTo(5);
    }

    @Test
    public void whenElementNotFound() {
        Integer[] array = new Integer[50];
        for (int i = 0; i < 50; i++) {
            array[i] = i * 2;
        }
        assertThat(ParallelIndexSearch.getIndex(100, array)).isEqualTo(-1);
    }

}