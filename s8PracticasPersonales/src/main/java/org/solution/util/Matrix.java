package org.solution.util;

import java.util.Random;

public class Matrix {
    public static final int LIMIT_LOW = 100;
    public static final int LIMIT_HIGH = 500_001;
    private int rows = 32_000;
    private int cols = 32_000;

    public int[][] generate() {
        int[][] output = new int[rows][cols];

        Random random = new Random();
        for (var i = 0; i < rows; i++) {
            for (var j = 0; j < cols; j++) {
                output[i][j] = random.nextInt(LIMIT_LOW, LIMIT_HIGH);
            }
        }
        return output;
    }


}
