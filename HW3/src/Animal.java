import java.util.Random;

public class Animal implements Runnable {

    private static Random rand = new Random();

    private String species;
    private double angle;

    public String getSpecies() {
        return species;
    }

    public double getAngle() {
        return angle;
    }

    public Animal(String species, double angle) {
        this.species = species;
        this.angle = angle;
    }

    @Override
    public void run() {
        System.out.printf("%s starts to pull with angle %f%n", species, angle);

        while (true) {
            try {
                Waggon.changeWaggonCoordinates(species, angle);

                int millsToSleep = 1000 + rand.nextInt(4000); // [1000, 5000)
                Thread.sleep(millsToSleep);
            }
            catch (InterruptedException e) {
                System.out.printf("%s died of fatigue :(%n", species);
                break;
            }
        }
    }
}
