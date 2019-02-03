public class Hangman implements Runnable {
    private int seconds;
    private Thread[] threads;

    public Hangman(int seconds, Thread ... threads) {
        this.seconds = seconds;
        this.threads = threads;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            for (Thread thread : threads)
                thread.interrupt();
        }
    }
}
