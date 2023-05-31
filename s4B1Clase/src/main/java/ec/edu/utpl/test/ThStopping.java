package ec.edu.utpl.test;

import ec.edu.utpl.sec.TaskEx;

public class ThStopping {
    public static void main(String[] args) throws InterruptedException{
        TaskEx taskEx = new TaskEx();
        Thread thread = new Thread(taskEx);

        thread.start();
        Thread.sleep(1_000);

        System.out.println("Después de sleep");
        taskEx.setKeepRunning(false);

        thread.join();

        System.out.printf("keepRunning = %b%n", taskEx.isKeepRunning());
    }
}
