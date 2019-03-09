package karnaukhova;

public class Hangman implements Runnable {
    private long milliseconds;
    private Thread[] threads;

    public Hangman(long milliseconds, Thread ... threads) {
        this.milliseconds = milliseconds;
        this.threads = threads;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            for (Thread thread : threads)
                thread.interrupt();
        }
    }
}
