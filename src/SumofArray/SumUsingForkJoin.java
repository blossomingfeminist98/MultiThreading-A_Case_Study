package SumofArray;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.concurrent.RecursiveTask;

public class SumUsingForkJoin extends RecursiveTask<Integer> {
    public static final int threshold = 10000;
    public static Logger LOG = LogManager.getLogger(SumUsingForkJoin.class);

    private int low, high;
    private int[] arr;

    public SumUsingForkJoin(int[] arr, int low, int high) {
        this.low = low;
        this.high = high;
        this.arr = arr;
    }

    @Override
    public Integer compute() {
        if (high - low <= threshold) {
            int ans = 0;
            for (int i = low; i < high; i++) {
                ans += arr[i];
            }
            return ans;
        } else {
            int mid = (low + high) / 2;
            SumUsingForkJoin left = new SumUsingForkJoin(arr, low, mid);
            SumUsingForkJoin right = new SumUsingForkJoin(arr, mid, high);
            left.fork();
            int rightSum = right.compute();
            int leftSum = left.join();
            return leftSum + rightSum;
        }
    }
}
