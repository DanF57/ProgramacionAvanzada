import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class DemoV2 {
    private static AtomicInteger counterAtomic;
    private static ExecutorService executor;
    private static CountDownLatch countDownLatch;

    public static void main(String[] args) throws InterruptedException {
        counterAtomic = new AtomicInteger();
        IntStream values = IntStream.rangeClosed(1, 1_000_000);
        int nroCores = Runtime.getRuntime().availableProcessors();
        System.out.println(nroCores);
        executor = Executors.newFixedThreadPool(nroCores);
        countDownLatch = new CountDownLatch(1_000_000);

        values.forEach(DemoV2::process);

        values.close();

        countDownLatch.await();
        System.out.println(counterAtomic.get());
        executor.shutdown();
    }

    private static void process(int number) {
        executor.execute(new Task(number, counterAtomic, countDownLatch));
    }
}
