package karnaukhova;

public class Hangman implements Runnable {
    private int milliseconds;
    private Thread[] threads;

    public Hangman(int milliseconds, Thread ... threads) {
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
