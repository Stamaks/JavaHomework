import java.util.Random;

public class Waggon {

    private static Random rand = new Random();

    private static double coordX;
    private static double coordY;
    private static StringBuilder history = new StringBuilder();
    private static long startTime;

    public static synchronized double getCoordX() {
        return coordX;
    }

    public static void setCoordX(double coordX) {
        Waggon.coordX = coordX;
    }

    public static synchronized double getCoordY() {
        return coordY;
    }

    public static void setCoordY(double coordY) {
        Waggon.coordY = coordY;
    }

    public static String getHistory() {
        return history.toString();
    }

    public static long getStartTime() {
        return startTime;
    }

    public static void setStartTime(long startTime) {
        Waggon.startTime = startTime;
    }

    public static synchronized void changeWaggonCoordinates(String species, double angle) {
        int coef = 1 + rand.nextInt(9); // From 1 (inc) to 10 (exc)
        coordX += coef * Math.cos(angle);
        coordY += coef * Math.sin(angle);

        history.append(String.format(
                "%fs: %s moved waggon to (%f, %f)%nCoefficient: %d, cos: %f, sin: %f%n%n",
                (System.currentTimeMillis() - startTime) / 1000.0,
                species, coordX, coordY, coef, Math.cos(angle), Math.sin(angle)
        ));
    }
}
