import MatrixMultiplication.MatrixMultiplication;
import SumofArray.ArraySum;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import java.util.UUID;

public class Main {

    public static Logger LOG = LogManager.getLogger(Main.class);
    /*
    * Strategies that user can input :
    * For Matrix Multiplication = HorVerSplit, VerHorSplit, HorSplit
    * For Array sum = ForkJoinPool, ThreadPool, ManualThread */
    public static void main(String[] args) {
        MDC.put("userName", UUID.randomUUID());
        String computationType = args[0];
        String strategy = args[1];
        int array_size = Integer.valueOf(args[2]);
        int thread_size = Integer.valueOf(args[3]);
        LOG.info("\nStrategies that user can input :\nFor Matrix Multiplication = HorVerSplit, VerHorSplit, HorSplit\nFor Array sum = ForkJoinPool, ThreadPool, ManualThread");
        LOG.info(String.format("\nUser Input Details : \nComputation Type : %s | Strategy : %s | Array Size: %d ", computationType, strategy, array_size));
        if(computationType.equalsIgnoreCase("ArraySum")) {
            ArraySum arraySum = new ArraySum();
            arraySum.computeSum(strategy, array_size, thread_size);
        } else if(computationType.equalsIgnoreCase("MatrixMultiplication")) {
            MatrixMultiplication matrixMultiplication = new MatrixMultiplication();
            matrixMultiplication.compute(strategy, array_size, thread_size);
        } else {
            LOG.info("Termination Program - Invalid Arguments.");
        }
    }
}