import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class DemoV1 {
    private static AtomicInteger counterAtomic;

    public static void main(String[] args) {
        counterAtomic = new AtomicInteger();
        IntStream values = IntStream.rangeClosed(1, 1_000_000);

        values.forEach(DemoV1::process);

        System.out.println(counterAtomic.get());
        values.close();

    }

    private static void process(int number) {
        Thread t = new Thread(new PrimeTask(number, counterAtomic));
        t.start();
    }
}