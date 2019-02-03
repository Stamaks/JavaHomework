public class Main {

    private static final int seconds = 25;
    private static final int delay = 2; // Print every 2 seconds
    private static final double swanAngle = Math.PI / 3;
    private static final double crawfishAngle = Math.PI;
    private static final double pikeAngle = 5 * Math.PI / 3;

    public static void main(String[] args) {
        System.out.println("Program starts!");

        Thread.currentThread().setPriority(Thread.MAX_PRIORITY); // To print every n seconds

        Thread swan = new Thread(new Animal("Swan", swanAngle));
        Thread crawfish = new Thread(new Animal("Crawfish", crawfishAngle));
        Thread pike = new Thread(new Animal("Pike", pikeAngle));

        System.out.printf("Set timer to %d seconds%n", seconds);

        Hangman hangman = new Hangman(seconds, swan, crawfish, pike);
        Thread timer = new Thread(hangman);

        long startTime = System.currentTimeMillis();
        Waggon.setStartTime(startTime);

        swan.start();
        crawfish.start();
        pike.start();
        timer.start();

        while (timer.isAlive()) {
            System.out.printf("Now coordinates are (%f, %f)%n", Waggon.getCoordX(), Waggon.getCoordY());
            
            try {
                Thread.sleep(delay * 1000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.printf("Waggon stopped at (%f, %f)%n", Waggon.getCoordX(), Waggon.getCoordY());

        System.out.println("\nHere is the history:");
        System.out.println(Waggon.getHistory());
    }
}
