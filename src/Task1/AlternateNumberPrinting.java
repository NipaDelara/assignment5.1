package Task1;

public class AlternateNumberPrinting {

    static class NumberPrinter extends Thread {
        private final int start;
        private final int end;
        private final String threadName;

        public NumberPrinter(int start, int end, String threadName) {
            this.start = start;
            this.end = end;
            this.threadName = threadName;
        }

        @Override
        public void run() {
            for (int i = start; i <= end; i += 2) {
                System.out.println(threadName + ": " + i);
            }
        }
    }

    public static void main(String[] args) {
        int from = 1;
        int to = 20;

        Thread oddThread = new NumberPrinter(from, to, "Odd Thread");
        Thread evenThread = new NumberPrinter(from + 1, to, "Even Thread");

        oddThread.start();
        evenThread.start();

        try {
            oddThread.join();
            evenThread.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted.");
        }

        System.out.println("Printing complete.");
    }
}
