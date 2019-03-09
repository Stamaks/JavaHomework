package karnaukhova;

import java.util.Random;

public class Animal implements Runnable {

    private static Random rand = new Random();

    private String species;
    private double angle;
    private StringBuilder history;
    public Waggon waggon;

    public String getSpecies() {
        return species;
    }

    public double getAngle() {
        return angle;
    }

    public String getHistory() {
        return history.toString();
    }

    public Animal(String species, double angle, Waggon waggon) {
        this.species = species;
        this.angle = angle;
        this.waggon = waggon;

        history = new StringBuilder();
    }

    @Override
    public void run() {
        System.out.printf("%s starts to pull with angle %.2f%n", species, angle);

        while (true) {
            try {
                waggon.changeWaggonCoordinates(species, angle);

                int millsToSleep = 1000 + rand.nextInt(4000); // [1000, 5000)
                history.append(String.format(
                        "%s pulled the waggon and fell asleep for %.3f seconds%n", species, millsToSleep / 1000.0
                ));
                Thread.sleep(millsToSleep);
            }
            catch (InterruptedException e) {
                System.out.printf("%s died of fatigue :(%n", species);
                break;
            }
        }
    }
}
