package ru.job4j.async;

import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.*;

public class RolColSumTest {

    @Test
    public void whenSyncSum() {
        int[][] matrix = {{2, 2, 2, 2, 2},
                {3, 3, 3, 3, 3},
                {1, 1, 1, 1, 1},
                {4, 4, 4, 4, 4},
                {5, 5, 5, 5, 5},
        };
        RolColSum.Sums[] sums = RolColSum.sum(matrix);
        assertThat(sums[0].getRowSum()).isEqualTo(10);
        assertThat(sums[0].getColSum()).isEqualTo(15);
    }

    @Test
    public void whenAsyncAndLengthOdd() throws ExecutionException, InterruptedException {
        int[][] matrix = {{2, 2, 2, 2, 2},
                {3, 3, 3, 3, 3},
                {1, 1, 1, 1, 1},
                {4, 4, 4, 4, 4},
                {5, 6, 7, 8, 9},
        };
        RolColSum.Sums[] sums = RolColSum.asyncSum(matrix);
        assertThat(sums[1].getRowSum()).isEqualTo(15);
        assertThat(sums[1].getColSum()).isEqualTo(16);
    }

    @Test
    public void whenAsyncAndLengthEven() throws ExecutionException, InterruptedException {
        int[][] matrix = {{2, 2, 2, 2, 2, 2},
                {3, 3, 3, 3, 3, 3},
                {1, 1, 1, 1, 1, 1},
                {4, 4, 4, 4, 4, 4},
                {5, 5, 5, 5, 5, 5},
                {7, 8, 9, 10, 11, 12},
        };
        RolColSum.Sums[] sums = RolColSum.asyncSum(matrix);
        assertThat(sums[2].getRowSum()).isEqualTo(6);
        assertThat(sums[2].getColSum()).isEqualTo(24);
    }
}