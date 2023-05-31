package ec.edu.utpl.sec;

public class Sequence {

    private int c = 0;

    public synchronized void increment(){
        c++;

    }

    /*public void increment() {
        //...
        synchronized{this} {
        c++;
        }
        //...
    }*/

    public void decrement(){
        c--;
    }

    public int value(){
        return c;
    }
}
