import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.openjdk.jmh.annotations.*;

public class Main {

    public static void main(String[] args) throws IOException {
        org.openjdk.jmh.Main.main(args);
    }
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Warmup(iterations = 2, time =100, timeUnit = TimeUnit.MILLISECONDS)
    @Measurement(iterations = 3, time = 100, timeUnit = TimeUnit.MILLISECONDS)
    @Fork(value = 2)
    public void process(){
        IntStream values = IntStream.range(1, 1_000_000);
        var result = values.filter(Main::isPrime);
        System.out.println("Nro Prios: %d/n");
    }

    private static boolean isPrime(int nro) {
        if (nro < 2) return false;
        if (nro == 2) return true;
        if (nro % 2 == 0) return false;
        for (int i = 2; i <= Math.sqrt(nro); i++) {
            if (nro % i == 0) return false;
        }
        return true;
    }
}
