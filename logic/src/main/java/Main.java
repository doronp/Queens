

import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Read from the user the desired board size and run one thread solving with the brute force algorithm
 * And the second one with the randomized algorithm.
 * Once any of them is done exit.
 */
public class Main {
    final static Logger logger = Logger.getLogger("Logger");

    private static int getSize() {
        Scanner reader = new Scanner(System.in);
        System.out.println("Please enter board size: ");
        int n = reader.nextInt();
        reader.close();
        return n;
    }

    public static void main(String args[]) {
        int n = getSize();

        Board boardOne = new Board();
        boardOne.init(n);

        Board boardTwo = new Board();
        boardTwo.init(n);

        //I just want one of them to finish
        CountDownLatch latch = new CountDownLatch(1);

        Runnable WorkerOne = () -> {
            Solver bf = new BruteForceAlg();
            bf.init(boardOne);
            if (bf.solve()) {
                boardOne.printBoard();
            }
            logger.log(Level.INFO, "Brute force approach finished!");
            latch.countDown();
        };

        Runnable WorkerTwo = () -> {
            Solver bf = new RandomAlg();
            bf.init(boardTwo);
            if (bf.solve()) {
                boardTwo.printBoard();
            }
            logger.log(Level.INFO, "Random approach finished!");
            latch.countDown();
        };

        ExecutorService executor = Executors.newWorkStealingPool();

        executor.submit(WorkerOne);
        executor.submit(WorkerTwo);

        try {
            latch.await();
            logger.log(Level.INFO, "Exit!");
        } catch (InterruptedException e) {
            logger.log(Level.INFO, "Exit...",e);

        } finally {
            executor.shutdownNow();
        }

    }
}
