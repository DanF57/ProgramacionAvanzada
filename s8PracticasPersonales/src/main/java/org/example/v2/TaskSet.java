package org.example.v2;

import java.util.stream.IntStream;

public class TaskSet implements Runnable {
    private final int startValue;
    private final int endValue;
    private long cantidad;

    public TaskSet(int startValue, int endValue) {
        this.startValue = startValue;
        this.endValue = endValue;
    }

    @Override
    public void run() {
        var results = IntStream.rangeClosed(startValue, endValue).filter(TaskSet::isPrime);
        cantidad = results.count();
        System.out.printf("Cantidad primos (%d-%d): %d%n", startValue, endValue, getCantidad());
    }

    public long getCantidad() {
        return cantidad;
    }

    private static boolean isPrime(int nro) {
        if (nro < 2) return false;
        if (nro == 2) return true;
        if (nro % 2 == 0) return false;
        for (int i = 3; i <= Math.sqrt(nro); i += 2) {
            if (nro % i == 0) return false;
        }
        return true;
    }
}