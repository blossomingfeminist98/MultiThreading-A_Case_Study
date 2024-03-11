package SumofArray;

public class SumUsingThreads implements Runnable {
    int start, end,thread_name, array_size, thread_size;
    int array[];
    long sum[];
    public SumUsingThreads(int[] array, long[] sum, int i, int array_size, int thread_size){
        this.array = array;
        this.sum = sum;
        this.thread_name = i;
        this.array_size = array_size;
        this.thread_size = thread_size;
    }
    public void run(){
        // last thread is operating
        if(thread_name == thread_size -1){
            this.start = thread_name * (array_size/thread_size);
            this.end = (array_size);
        }
        else {
            this.start = thread_name * (array_size/thread_size);
            this.end = (thread_name+1) * (array_size/thread_size);
        }
        for(int i=start; i < end; i++)
            sum[thread_name] = sum[thread_name] + array[i];
        //System.out.println("Start - End : " + start + end);
    }
}
