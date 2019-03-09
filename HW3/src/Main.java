import java.util.Scanner;

public class Main {

    private static final int seconds = 25;
    private static final int delay = 2; // Print every 2 seconds
    private static final double swanAngle = Math.PI / 3;
    private static final double crawfishAngle = Math.PI;
    private static final double pikeAngle = 5 * Math.PI / 3;

    public static void main(String[] args) {

        checkArguments(args);

        Waggon waggon = new Waggon();

        setWaggonStartCoordinates(args, waggon);

        System.out.println("Program starts!");

        // Create animals
        Animal swan = new Animal("Swan", swanAngle, waggon);
        Animal crawfish = new Animal("Crawfish", crawfishAngle, waggon);
        Animal pike = new Animal("Pike", pikeAngle, waggon);

        // Create animals' threads
        Thread swanThread = new Thread(swan);
        Thread crawfishThread = new Thread(crawfish);
        Thread pikeThread = new Thread(pike);

        System.out.printf("Set timer to %d seconds%n", seconds);

        // Create a hangman that kills threads in n seconds
        Hangman hangman = new Hangman(seconds, swanThread, crawfishThread, pikeThread);
        Thread timer = new Thread(hangman);

        // Measure startTime for the waggon history
        waggon.setStartTime(System.currentTimeMillis());

        swanThread.start();
        crawfishThread.start();
        pikeThread.start();
        timer.start();

        double[] currentCoords;
        while (timer.isAlive()) {
            currentCoords = waggon.getCoordinates();
            System.out.printf("Now the waggon coordinates are (%.2f, %.2f)%n", currentCoords[0], currentCoords[1]);
            
            try {
                Thread.sleep(delay * 1000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        currentCoords = waggon.getCoordinates();
        System.out.printf("%nThe waggon stopped at (%.2f, %.2f)%n", currentCoords[0], currentCoords[1]);

        // Show the waggon or the animals histories if asked
        showHistories(swan, crawfish, pike, waggon);
    }

    private static void checkArguments(String[] args) {
        if (args.length != 0 && args.length != 2) {
            System.out.print("Wrong number of arguments! " +
                    "Please, rerun the program with the right number of arguments " +
                    "(no arguments or coordinate x and coordinate y)");
            System.exit(0);
        }
    }

    private static void setWaggonStartCoordinates(String[] args, Waggon waggon) {
        if (args.length == 2) {
            try {
                waggon.setCoordinates(Double.parseDouble(args[0]), Double.parseDouble(args[1]));
            }
            catch (NumberFormatException e)
            {
                System.out.print("Wrong type of arguments!");
                System.exit(0);
            }
        }
        else {
            waggon.setCoordinates(0, 0);
        }
    }

    private static void showHistories(Animal swan, Animal crawfish, Animal pike, Waggon waggon) {
        Scanner scanner = new Scanner(System.in);
        String scan = "";
        boolean shouldExit = false;
        do {

            do {
                printPossibilities();
                scan = scanner.next();
            } while (scan.length() != 1 && !"wscpq".contains(scan));

            switch (scan.charAt(0)) {
                case 'q':
                    shouldExit = true;
                    break;
                case 'w':
                    System.out.println(waggon.getHistory());
                    break;
                case 's':
                    System.out.println(swan.getHistory());
                    break;
                case 'c':
                    System.out.println(crawfish.getHistory());
                    break;
                case 'p':
                    System.out.println(pike.getHistory());
                    break;
                default:
                    break;
            }

        } while (!shouldExit);
    }

    private static void printPossibilities(){
        System.out.println("To print the waggon history enter 'w'");
        System.out.println("To print the swan history enter 's'");
        System.out.println("To print the crawfish history enter 'c'");
        System.out.println("To print the pike history enter 'p'");
        System.out.println("To exit enter q");
    }
}
