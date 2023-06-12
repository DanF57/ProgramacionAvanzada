package org.solution.par;

import java.util.concurrent.Callable;

public class TaskSumCol implements Callable<Integer> {

    private int[] col;

    public TaskSumCol(int[] col) {
        this.col = col;
    }

    @Override
    public Integer call() throws Exception {
        var sum = 0;
        for (int i = 0; i < col.length; i++) {
            sum += col[i];
        }
        return sum;
    }
}
