package org.solution.par;

import org.openjdk.jmh.annotations.*;
import org.solution.util.Matrix;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 1)
@Measurement(iterations = 1)
@Fork(value = 2)
public class SumMatrix {
    @Benchmark
    public void sumSec() {
        Matrix matrix = new Matrix();
        int[][] mat = matrix.generate();

        var sum = 0;
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[i].length; j++) {
                sum += mat[i][j];
            }
        }
        System.out.println(sum);
    }

    @Benchmark
    public void sumPar() throws ExecutionException, InterruptedException {
        Matrix matrix = new Matrix();
        int[][] mat = matrix.generate();
        var sum = 0;

        List<Future<Integer>> futures = new ArrayList<>();
        var coreNum = Runtime.getRuntime().availableProcessors();

        ExecutorService executor = Executors.newFixedThreadPool(coreNum);
        for (var indexCol = 0; indexCol < mat.length; indexCol++) {
            TaskSumCol taskSumCol = new TaskSumCol(getColumn(mat, indexCol));
            futures.add(executor.submit(taskSumCol));
        }

        for (var future : futures) { //por cada future que hay en futures
            sum += future.get(); //COUNTDOWNLATCH SOLO PARA CUANDO VAYA A EXISTIR ERRORES
        }

        executor.shutdown();
        System.out.println(sum);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {
        org.openjdk.jmh.Main.main(args);
    }

    private static int[] getColumn(int[][] mat, int indexCol) {
        int[] output = new int[mat.length];
        for (var i = 0; i < mat.length; i++) {
            output[i] = mat[i][indexCol];
        }

        return output;
    }
}
