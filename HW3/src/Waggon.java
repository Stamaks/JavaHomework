import java.util.Random;

public class Waggon {

    private static Random rand = new Random();

    private double[] coordinates;
    private StringBuilder history;
    private long startTime;

    public Waggon() {
        coordinates = new double[2];
        coordinates[0] = 0;
        coordinates[1] = 0;
        this.startTime = System.currentTimeMillis();
        history = new StringBuilder();
    }

    public double[] getCoordinates() {
        synchronized (this) {
            return coordinates;
        }
    }

    public void setCoordinates(double coordX, double coordY) {
        synchronized (this) {
            coordinates[0] = coordX;
            coordinates[1] = coordY;
            history.append(String.format("Start coordinate x: %.2f%n", coordX));
            history.append(String.format("Start coordinate y: %.2f%n", coordY));
        }
    }

    public String getHistory() {
        return history.toString();
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public void changeWaggonCoordinates(String species, double angle) {
        synchronized (this) {
            int coef = 1 + rand.nextInt(9); // [1, 10)
            coordinates[0] += coef * Math.cos(angle);
            coordinates[1] += coef * Math.sin(angle);

            history.append(String.format(
                    "%.3fs: %s moved waggon to (%.2f, %.2f)%nCoefficient: %d, cos: %.2f, sin: %.2f%n",
                    (System.currentTimeMillis() - startTime) / 1000.0,
                    species, coordinates[0], coordinates[1], coef, Math.cos(angle), Math.sin(angle)
            ));
        }
    }
}
