package ec.edu.utpl.cou;

import ec.edu.utpl.sec.Sequence;

public class CounterIncRunnable implements Runnable{

    private final Sequence mySequence;

    public CounterIncRunnable(Sequence mySequence){
        this.mySequence = mySequence;
    }

    public void run(){
        for( var i = 0; i < 100; i ++) {
            mySequence.increment();
        }
    }
}
