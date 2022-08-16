package ru.job4j.async;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
    public static class Sums {
        private int rowSum;
        private int colSum;

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }
    }

    public static Sums[] sum(int[][] matrix) {
        int length = matrix.length;
        Sums[] result = new Sums[length];
        for (int i = 0; i < length; i++) {
            int rowSum = 0;
            int colSum = 0;
            Sums sums = new Sums();
            for (int j = 0; j < length; j++) {
                rowSum = rowSum +  matrix[i][j];
                colSum = colSum + matrix[j][i];
            }
            sums.setRowSum(rowSum);
            sums.setColSum(colSum);
            result[i] = sums;
        }
        return result;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        int length = matrix.length;
        Sums[] result = new Sums[length];
        Map<Integer, CompletableFuture<Sums>> futures = new HashMap<>();
        if (length % 2 == 0) {
            for (int i = 0; i < length / 2; i++) {
                futures.put(i, getTask(matrix, i, length));
                futures.put(length - 1 - i, getTask(matrix, length - 1 - i, length));
            }
        } else {
            for (int i = 0; i <= length / 2; i++) {
                futures.put(i, getTask(matrix, i, length));
                if (i < length / 2) {
                    futures.put(length - 1 - i, getTask(matrix, length - 1 - i, length));
                }
            }
        }
        for (Integer key : futures.keySet()) {
            result[key] = futures.get(key).get();
        }
        return result;
    }

    private static CompletableFuture<Sums> getTask(int[][] data, int index, int length) {
        return CompletableFuture.supplyAsync(() -> {
            int rowSum = 0;
            int colSum = 0;
            Sums sums = new Sums();
            for (int i = 0; i < length; i++) {
                rowSum = rowSum + data[index][i];
                colSum = colSum + data[i][index];
            }
            sums.setColSum(colSum);
            sums.setRowSum(rowSum);
            return sums;
        });
    }

}