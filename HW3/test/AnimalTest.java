import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class AnimalTest {
    @Test
    public void getSpecies() {
        Animal swan = new Animal("SWAN", 10);
        Animal cat = new Animal("cat", 100500);

        Assertions.assertEquals("SWAN", swan.getSpecies());
        Assertions.assertEquals("cat", cat.getSpecies());
    }

    @Test
    public void getAngle() {
        Animal swan = new Animal("swan", Math.PI);
        Animal cat = new Animal("CAT", 100500);

        Assertions.assertEquals(3.14, swan.getAngle(), 0.005);
        Assertions.assertEquals(100500, cat.getAngle());
    }

    @Test
    public void getHistory() {
        Animal swan = new Animal("swan", Math.PI);

        int countSwanInWaggonHistory = 0;
        String[] allWordsInWagonHistory = Waggon.getHistory().split("\\s+");

        for (String s : allWordsInWagonHistory)
            if (s.equals("swan"))
                countSwanInWaggonHistory++;

        int countSwanInSwanHistory = 0;
        String[] allWordsInSwanHistory = swan.getHistory().split("\\s+");

        for (String s : allWordsInSwanHistory)
            if (s.equals("swan"))
                countSwanInSwanHistory++;

        Assertions.assertEquals(countSwanInWaggonHistory, countSwanInSwanHistory);
    }
}
