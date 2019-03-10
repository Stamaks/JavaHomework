package karnaukhova;

import java.util.Random;

public class Animal implements Runnable {

    private static Random rand = new Random();

    private String species;
    private double angle;
    private StringBuilder history;
    public Waggon waggon;

    public String getHistory() {
        return history.toString();
    }

    public Animal(String species, double angle, Waggon waggon) {
        this.species = species;
        this.angle = -angle * Math.PI / 180;
        this.waggon = waggon;

        history = new StringBuilder();
    }

    @Override
    public void run() {
        history.append(String.format("%s starts to pull with angle %.2f%n", species, angle));

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
                history.append(String.format("%s died of fatigue :(%n", species));
                break;
            }
        }
    }
}
