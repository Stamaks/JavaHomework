import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class NumberWithTypeTests {

    @Test
    public void NumberWithTypeSimple() {
        NumberWithType num = new NumberWithType(0, "unknown");

        Assert.assertEquals(0, num.variable.intValue());
        Assert.assertEquals("unknown", num.variableType);
    }

    @Test
    public void NumberWithTypeHard() {
        NumberWithType num = new NumberWithType(2 + 3, "int");

        Assert.assertEquals(5, num.variable.intValue());
        Assert.assertEquals("int", num.variableType);

        num = new NumberWithType(2.9, "float");

        Assert.assertEquals(2.9, num.variable.floatValue(), 0.1);
        Assert.assertEquals("float", num.variableType);
    }
}
