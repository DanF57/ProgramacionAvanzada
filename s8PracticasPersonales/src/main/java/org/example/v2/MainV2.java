package org.example.v2;

import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class MainV2 {
    public static void main(String[] args) throws IOException {
        // org.openjdk.jmh.Main.main(args);
    }


    /*@Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Warmup(iterations = 2, time =100, timeUnit = TimeUnit.MILLISECONDS)
    @Measurement(iterations = 3, time = 100, timeUnit = TimeUnit.MILLISECONDS)
    @Fork(value = 2)*/
    public void process() {

        TaskSet task1 = new TaskSet(1, 500_000);
        TaskSet task2 = new TaskSet(500_001, 1_000_000);

        Thread th1 = new Thread(task1);
        Thread th2 = new Thread(task2);

        th1.start();
        th2.start();

        try {
            th1.join();
            th2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
