import java.util.concurrent.atomic.AtomicInteger;

public class PrimeTask implements Runnable {
    private int number;
    private AtomicInteger counter;

    public PrimeTask(int number, AtomicInteger counter) {
        this.number = number;
        this.counter = counter;
    }

    @Override
    public void run() {
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
        System.out.println(Thread.currentThread().getName());
        /*try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {}*/
    }
}