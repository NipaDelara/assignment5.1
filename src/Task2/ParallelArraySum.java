package Task2;
import java.util.Random;

public class ParallelArraySum {

    static class SumWorker extends Thread {
        private final int[] array;
        private final int start;
        private final int end;
        private long partialSum;

        public SumWorker(int[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }

        @Override
        public void run() {
            for (int i = start; i < end; i++) {
                partialSum += array[i];
            }
        }

        public long getPartialSum() {
            return partialSum;
        }
    }

    public static void main(String[] args) {
        int size = 100000;
        int[] numbers = new int[size];
        Random random = new Random();

        for (int i = 0; i < size; i++) {
            numbers[i] = random.nextInt(100) + 1;
        }

        for (int i = 0; i < 10; i++) {
            System.out.print(numbers[i] + " ");
        }
        System.out.println();

        int cores = Runtime.getRuntime().availableProcessors();
        System.out.println("Available processor cores: " + cores);

        SumWorker[] workers = new SumWorker[cores];
        int chunkSize = size / cores;

        int start = 0;
        for (int i = 0; i < cores; i++) {
            int end = (i == cores - 1) ? size : start + chunkSize;
            workers[i] = new SumWorker(numbers, start, end);
            workers[i].start();
            start = end;
        }

        long totalSum = 0;
        for (int i = 0; i < cores; i++) {
            try {
                workers[i].join();
                totalSum += workers[i].getPartialSum();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Total sum: " + totalSum);
    }
}