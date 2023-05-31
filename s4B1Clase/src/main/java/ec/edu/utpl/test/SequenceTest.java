package ec.edu.utpl.test;

import ec.edu.utpl.cou.CounterIncRunnable;
import ec.edu.utpl.sec.Sequence;

public class SequenceTest {
    public static void main(String[] args) throws InterruptedException{
        Sequence mySequence = new Sequence();

        Thread thread1 = new Thread(new CounterIncRunnable(mySequence), "A");
        //thread1.setName("add thread 1");
        thread1.start();

        Thread thread2 = new Thread(new CounterIncRunnable(mySequence), "B");
        //thread2.setName("add thread 2");
        thread2.start();

        thread1.join();
        thread2.join();
        System.out.println(mySequence.value());
    }
}
