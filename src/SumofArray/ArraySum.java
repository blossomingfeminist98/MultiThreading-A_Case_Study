package SumofArray;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

public class ArraySum {

    public static Random random = new Random();

    static {
        random.setSeed(20L);
    }
    public static Logger LOG = LogManager.getLogger(ArraySum.class);
    public void computeSum(String strategy, int array_size, int thread_size) {
        int arr[] = new int[array_size];
        MDC.put("userName", UUID.randomUUID());
        LOG.info(String.format("Initializing an Array of size - %d", array_size));
        for (int i = 0; i < array_size; i++)
            arr[i] = random.nextInt(1000)+1;
        MDC.put("userName", UUID.randomUUID());
        if(strategy.equalsIgnoreCase("ForkJoinPool")) {
            calculateSumUsingForkJoin(arr, array_size);
        } else if(strategy.equalsIgnoreCase("ThreadPool")) {
            calculateSumUsingThreadPool(arr,array_size, thread_size );
        } else if(strategy.equalsIgnoreCase("ManualThread")) {
            calculateSumUsingThreads(arr, array_size, thread_size);
        }
        //display(arr, array_size);
    }

    public static void display(int[] arr, int size){
        for(int i=0; i< size ; i++)
            LOG.info(arr[i] + " ");
    }
    public static void calculateSumUsingThreads(int[] arr, int array_size, int thread_size){
        try
        {
            LOG.info("Threads used for performing summation : "+ thread_size);
            Thread[] threads = new Thread[thread_size];
            long[] sum = new long[thread_size];
            long t3 = System.currentTimeMillis();
            for (int i = 0; i < thread_size; i++) {
                threads[i] = new Thread(new SumUsingThreads(arr, sum, i, array_size, thread_size));
                threads[i].start();
            }
            for (int i = 0; i < thread_size; i++)
                threads[i].join();
            long total=0;
            for( int i=0; i< thread_size; i++)
                total = total + sum[i];
            long t4 = System.currentTimeMillis();
            LOG.info("Sum : " + total);
            LOG.info("Time Taken for summation : " + ((t4-t3)/1000f) + "sec");
        }
        catch(InterruptedException ex){
            ex.printStackTrace();
        }
    }
    public static void calculateSumUsingThreadPool(int[] arr, int array_size, int thread_size){
        LOG.info("Using ThreadPool ");
        long t3 = System.currentTimeMillis();
        long[] sum = new long[thread_size];
        ExecutorService thread_pool = Executors.newFixedThreadPool(thread_size);
        for(int i=0;i<thread_size;i++){
            Runnable r1 = new SumUsingThreadpool(arr, sum, i, array_size, thread_size);
            thread_pool.execute(r1);
        }
        thread_pool.shutdown();
        while(!thread_pool.isTerminated()){}
        long total=0;
        for( int j=0; j< thread_size; j++)
            total = total + sum[j];
        long t4 = System.currentTimeMillis();
        LOG.info("Sum : " + total);
        LOG.info("Time Taken for summation : " + ((t4-t3)/1000f) + "sec");
    }
    public static void calculateSumUsingForkJoin(int[] arr, int array_size){
        LOG.info("Using Fork Join");
        long t3 = System.currentTimeMillis();
        ForkJoinPool fjPool = new ForkJoinPool();
        long sum = fjPool.invoke(new SumUsingForkJoin(arr, 0, array_size));
        long t4 = System.currentTimeMillis();
        LOG.info("Sum : " + sum);
        LOG.info("Time Taken for summation : " + ((t4-t3)/1000f) + "sec");
    }
}
