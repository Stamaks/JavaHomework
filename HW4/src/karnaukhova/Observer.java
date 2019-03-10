package karnaukhova;

public class Observer implements Runnable {

    private int swanAngle, pikeAngle, crawfishAngle;
    private int sBottom, sTop;
    private int sleepBottom, sleepTop;
    private long simulationDuration;
    private Waggon waggon;
    private Animal swan, pike, crawfish;
    private Thread swanThread, crawfishThread, pikeThread, timer;

    @Override
    public void run() {
        // Создаем потоки
        swanThread = new Thread(swan);
        crawfishThread = new Thread(crawfish);
        pikeThread = new Thread(pike);

        // Ставим таймер
        Hangman hangman = new Hangman(simulationDuration, swanThread, crawfishThread, pikeThread);
        timer = new Thread(hangman);

        swanThread.start();
        crawfishThread.start();
        pikeThread.start();
        timer.start();

        try {
            timer.join();
        } catch (InterruptedException e) {}

    }

    public Observer(double[] startWaggonCoordinates, int swanAngle, int pikeAngle,
            int crawfishAngle, int sBottom, int sTop, int sleepBottom, int sleepTop, long simulationDuration)
    {
        this.swanAngle = swanAngle;
        this.pikeAngle = pikeAngle;
        this.crawfishAngle = crawfishAngle;
        this.sBottom = sBottom;
        this.sTop = sTop;
        this.sleepBottom = sleepBottom;
        this.sleepTop = sleepTop;
        this.simulationDuration = simulationDuration;

        waggon = new Waggon();
        waggon.setCoordinates(startWaggonCoordinates[0], startWaggonCoordinates[1]);

        swan = new Animal("swan", swanAngle, waggon);
        pike = new Animal("pike", pikeAngle, waggon);
        crawfish = new Animal("crawfish", crawfishAngle, waggon);
    }

    public double[] getWaggonCoordinates() {
        return waggon.getCoordinates();
    }

    public void killAll () {
        timer.interrupt();
    }

    public String getWaggonHistory() {
        return waggon.getHistory();
    }

    public String getPikeHistory() {
        return pike.getHistory();
    }

    public String getSwanHistory() {
        return swan.getHistory();
    }

    public String getCrawfishHistory() {
        return crawfish.getHistory();
    }
}
