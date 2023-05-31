import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class Task implements Runnable {
    private int number;
    private AtomicInteger counter;
    private CountDownLatch latch;

    public Task(int number, AtomicInteger counter, CountDownLatch latch) {
        this.number = number;
        this.counter = counter;
        this.latch = latch;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
        var isPrime = true;

        if (number < 2) {
            isPrime = false;
        } else if (number == 2) {
            isPrime = true;
        } else if (number % 2 == 0) {
            isPrime = false;
        } else {
            for (var i = 3; i <= Math.sqrt(number) && isPrime; i++) {
                if (number % i == 0) {
                    isPrime = false;
                }
            }
        }
        if (isPrime) counter.getAndIncrement();

        latch.countDown();
    }
}
