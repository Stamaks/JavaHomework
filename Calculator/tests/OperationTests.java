import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class OperationTests {

    @Test
    public void processTokensStartPoint() {
        Operation op = new Operation();

        Assert.assertEquals(0, op.x.variable.intValue());
        Assert.assertEquals("unknown", op.x.variableType);
        Assert.assertEquals(0, op.y.variable.intValue());
        Assert.assertEquals("unknown", op.y.variableType);
        Assert.assertEquals(' ', op.operation);
    }

    @Test
    public void processTokensSimple() {
        String testString = "x = 3";
        Operation op = new Operation();

        String[] tokens = testString.split("=");
        op.processTokens(tokens);

        Assert.assertEquals(3, op.x.variable.intValue());
        Assert.assertEquals("int", op.x.variableType);
        Assert.assertEquals(0, op.y.variable.intValue());
        Assert.assertEquals("unknown", op.y.variableType);
        Assert.assertEquals(' ', op.operation);


        testString = "y=2.5";

        tokens = testString.split("=");
        op.processTokens(tokens);

        Assert.assertEquals(3, op.x.variable.intValue());
        Assert.assertEquals("int", op.x.variableType);
        Assert.assertEquals(2.5F, op.y.variable.floatValue(), 0.1);
        Assert.assertEquals("float", op.y.variableType);
        Assert.assertEquals(' ', op.operation);


        testString = " +    ";

        tokens = testString.split("=");
        op.processTokens(tokens);

        Assert.assertEquals(3, op.x.variable.intValue());
        Assert.assertEquals("int", op.x.variableType);
        Assert.assertEquals(2.5F, op.y.variable.floatValue(), 0.1);
        Assert.assertEquals("float", op.y.variableType);
        Assert.assertEquals('+', op.operation);
    }

    @Test
    public void processTokensStress() {
        Operation op = new Operation();

        String testString = "x = 3";
        String[] tokens = testString.split("=");
        op.processTokens(tokens);

        testString = "y=2.5";
        tokens = testString.split("=");
        op.processTokens(tokens);

        testString = "+   ";
        tokens = testString.split("=");
        op.processTokens(tokens);

        Assert.assertEquals(3, op.x.variable.intValue());
        Assert.assertEquals("int", op.x.variableType);
        Assert.assertEquals(2.5F, op.y.variable.floatValue(), 0.1);
        Assert.assertEquals("float", op.y.variableType);
        Assert.assertEquals('+', op.operation);


        testString = " + -   ";

        tokens = testString.split("=");
        op.processTokens(tokens);

        Assert.assertEquals(3, op.x.variable.intValue());
        Assert.assertEquals("int", op.x.variableType);
        Assert.assertEquals(2.5F, op.y.variable.floatValue(), 0.1);
        Assert.assertEquals("float", op.y.variableType);
        Assert.assertEquals('+', op.operation);


        testString = "  x   =1.55";

        tokens = testString.split("=");
        op.processTokens(tokens);

        Assert.assertEquals(1.55F, op.x.variable.floatValue(), 0.1);
        Assert.assertEquals("float", op.x.variableType);
        Assert.assertEquals(2.5F, op.y.variable.floatValue(), 0.1);
        Assert.assertEquals("float", op.y.variableType);
        Assert.assertEquals('+', op.operation);


        testString = "slfj sdlkfjs wpoeri";

        tokens = testString.split("=");
        op.processTokens(tokens);

        Assert.assertEquals(1.55F, op.x.variable.floatValue(), 0.1);
        Assert.assertEquals("float", op.x.variableType);
        Assert.assertEquals(2.5F, op.y.variable.floatValue(), 0.1);
        Assert.assertEquals("float", op.y.variableType);
        Assert.assertEquals('+', op.operation);

        testString = "slfj sdlkfjs wpoeri";

        tokens = testString.split("=");
        op.processTokens(tokens);

        Assert.assertEquals(1.55F, op.x.variable.floatValue(), 0.1);
        Assert.assertEquals("float", op.x.variableType);
        Assert.assertEquals(2.5F, op.y.variable.floatValue(), 0.1);
        Assert.assertEquals("float", op.y.variableType);
        Assert.assertEquals('+', op.operation);


        testString = "x = 3abc";

        tokens = testString.split("=");
        op.processTokens(tokens);

        Assert.assertEquals(1.55F, op.x.variable.floatValue(), 0.1);
        Assert.assertEquals("float", op.x.variableType);
        Assert.assertEquals(2.5F, op.y.variable.floatValue(), 0.1);
        Assert.assertEquals("float", op.y.variableType);
        Assert.assertEquals('+', op.operation);

    }

    @Test
    public void readyToGetResultStartPoint() {
        Operation op = new Operation();

        Assert.assertFalse(op.readyToGetResult());
    }

    @Test
    public void readyToGetResultSimple() {
        Operation op = new Operation();

        String testString = "x = 3";
        String[] tokens = testString.split("=");
        op.processTokens(tokens);

        Assert.assertFalse(op.readyToGetResult());


        testString = "y = 3";
        tokens = testString.split("=");
        op.processTokens(tokens);

        Assert.assertFalse(op.readyToGetResult());


        testString = "*";
        tokens = testString.split("=");
        op.processTokens(tokens);

        Assert.assertTrue(op.readyToGetResult());
    }

    @Test
    public void readyToGetResultStress() {
        Operation op = new Operation();

        String testString = "x = 3";
        String[] tokens = testString.split("=");
        op.processTokens(tokens);

        Assert.assertFalse(op.readyToGetResult());


        testString = "y = 3";

        tokens = testString.split("=");
        op.processTokens(tokens);

        Assert.assertFalse(op.readyToGetResult());


        testString = "y = 5.5";

        tokens = testString.split("=");
        op.processTokens(tokens);

        Assert.assertFalse(op.readyToGetResult());


        testString = "+ *";

        tokens = testString.split("=");
        op.processTokens(tokens);

        Assert.assertFalse(op.readyToGetResult());


        testString = "/";

        tokens = testString.split("=");
        op.processTokens(tokens);

        Assert.assertTrue(op.readyToGetResult());


        testString = "y = 3abc";

        tokens = testString.split("=");
        op.processTokens(tokens);

        Assert.assertTrue(op.readyToGetResult());


        testString = "*";

        tokens = testString.split("=");
        op.processTokens(tokens);

        Assert.assertTrue(op.readyToGetResult());
    }

    @Test
    public void getResultStartPoint() {
        Operation op = new Operation();

        Assert.assertEquals(0, op.getResult().variable.intValue());
        Assert.assertEquals("unknown", op.getResult().variableType);
    }

    @Test
    public void getResultSimple() {
        Operation op = new Operation();

        String testString = "x = 3";
        String[] tokens = testString.split("=");
        op.processTokens(tokens);
        testString = "y=2.5";
        tokens = testString.split("=");
        op.processTokens(tokens);
        testString = " +    ";
        tokens = testString.split("=");
        op.processTokens(tokens);

        Assert.assertEquals(5.5, op.getResult().variable.floatValue(), 0.1);
        Assert.assertEquals("float", op.getResult().variableType);
    }

    @Test
    public void getResultStress() {
        Operation op = new Operation();

        String testString = "x = 3";
        String[] tokens = testString.split("=");
        op.processTokens(tokens);
        testString = "y=2.5";
        tokens = testString.split("=");
        op.processTokens(tokens);
        testString = " +    ";
        tokens = testString.split("=");
        op.processTokens(tokens);

        testString = "y =      2";
        tokens = testString.split("=");
        op.processTokens(tokens);

        Assert.assertEquals(5, op.getResult().variable.intValue());
        Assert.assertEquals("int", op.getResult().variableType);


        testString = "y =      2abc";
        tokens = testString.split("=");
        op.processTokens(tokens);

        Assert.assertEquals(5, op.getResult().variable.intValue());
        Assert.assertEquals("int", op.getResult().variableType);


        testString = "x = 1.1";
        tokens = testString.split("=");
        op.processTokens(tokens);
        testString = "y =2.2";
        tokens = testString.split("=");
        op.processTokens(tokens);

        Assert.assertEquals(3.3, op.getResult().variable.floatValue(), 0.1);
        Assert.assertEquals("float", op.getResult().variableType);


        testString = "y = -2";
        tokens = testString.split("=");
        op.processTokens(tokens);
        testString = "x = -8";
        tokens = testString.split("=");
        op.processTokens(tokens);
        testString = "  *         ";
        tokens = testString.split("=");
        op.processTokens(tokens);

        Assert.assertEquals(16, op.getResult().variable.intValue());
        Assert.assertEquals("int", op.getResult().variableType);


        testString = "y = 3";
        tokens = testString.split("=");
        op.processTokens(tokens);
        testString = "x = 5";
        tokens = testString.split("=");
        op.processTokens(tokens);
        testString = "/         ";
        tokens = testString.split("=");
        op.processTokens(tokens);

        Assert.assertEquals(1, op.getResult().variable.intValue());
        Assert.assertEquals("int", op.getResult().variableType);


        testString = "y = 3";
        tokens = testString.split("=");
        op.processTokens(tokens);
        testString = "x = 5.5";
        tokens = testString.split("=");
        op.processTokens(tokens);
        testString = "-         ";
        tokens = testString.split("=");
        op.processTokens(tokens);

        Assert.assertEquals(2.5, op.getResult().variable.floatValue(), 0.1);
        Assert.assertEquals("float", op.getResult().variableType);
    }
}