import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class OperationTest {

    @Test
    public void processTokens() {
        String testString = "x = 3";
        Operation op = new Operation();

        Assert.assertEquals(op.x.variable.intValue(), 0);
        Assert.assertEquals(op.x.variableType, "unknown");
        Assert.assertEquals(op.y.variable.intValue(), 0);
        Assert.assertEquals(op.y.variableType, "unknown");
        Assert.assertEquals(op.operation, ' ');


        String[] tokens = testString.split(" ");
        op.processTokens(tokens);

        Assert.assertEquals(op.x.variable.intValue(), 3);
        Assert.assertEquals(op.x.variableType, "int");
        Assert.assertEquals(op.y.variable.intValue(), 0);
        Assert.assertEquals(op.y.variableType, "unknown");
        Assert.assertEquals(op.operation, ' ');


        testString = "y=2.5";

        tokens = testString.split(" ");
        op.processTokens(tokens);

        Assert.assertEquals(op.x.variable.intValue(), 3);
        Assert.assertEquals(op.x.variableType, "int");
        Assert.assertEquals(op.y.variable.floatValue(), 2.5F, 0.1);
        Assert.assertEquals(op.y.variableType, "float");
        Assert.assertEquals(op.operation, ' ');


        testString = " +    ";

        tokens = testString.split(" ");
        op.processTokens(tokens);

        Assert.assertEquals(op.x.variable.intValue(), 3);
        Assert.assertEquals(op.x.variableType, "int");
        Assert.assertEquals(op.y.variable.floatValue(), 2.5F, 0.1);
        Assert.assertEquals(op.y.variableType, "float");
        Assert.assertEquals(op.operation, '+');


        testString = " + -   ";

        tokens = testString.split(" ");
        op.processTokens(tokens);

        Assert.assertEquals(op.x.variable.intValue(), 3);
        Assert.assertEquals(op.x.variableType, "int");
        Assert.assertEquals(op.y.variable.floatValue(), 2.5F, 0.1);
        Assert.assertEquals(op.y.variableType, "float");
        Assert.assertEquals(op.operation, '+');


        testString = "  x   =1.55";

        tokens = testString.split(" ");
        op.processTokens(tokens);

        Assert.assertEquals(op.x.variable.floatValue(), 1.55, 0.1);
        Assert.assertEquals(op.x.variableType, "float");
        Assert.assertEquals(op.y.variable.floatValue(), 2.5F, 0.1);
        Assert.assertEquals(op.y.variableType, "float");
        Assert.assertEquals(op.operation, '+');


        testString = "slfj sdlkfjs wpoeri";

        tokens = testString.split(" ");
        op.processTokens(tokens);

        Assert.assertEquals(op.x.variable.floatValue(), 1.55, 0.1);
        Assert.assertEquals(op.x.variableType, "float");
        Assert.assertEquals(op.y.variable.floatValue(), 2.5F, 0.1);
        Assert.assertEquals(op.y.variableType, "float");
        Assert.assertEquals(op.operation, '+');


        testString = "slfj sdlkfjs wpoeri";

        tokens = testString.split(" ");
        op.processTokens(tokens);

        Assert.assertEquals(op.x.variable.floatValue(), 1.55, 0.1);
        Assert.assertEquals(op.x.variableType, "float");
        Assert.assertEquals(op.y.variable.floatValue(), 2.5F, 0.1);
        Assert.assertEquals(op.y.variableType, "float");
        Assert.assertEquals(op.operation, '+');


        testString = "x = 3abc";

        tokens = testString.split(" ");
        op.processTokens(tokens);

        Assert.assertEquals(op.x.variable.floatValue(), 1.55, 0.1);
        Assert.assertEquals(op.x.variableType, "float");
        Assert.assertEquals(op.y.variable.floatValue(), 2.5F, 0.1);
        Assert.assertEquals(op.y.variableType, "float");
        Assert.assertEquals(op.operation, '+');

    }

    @Test
    public void readyToGetResult() {
        String testString = "x = 3";
        Operation op = new Operation();

        Assert.assertFalse(op.readyToGetResult());


        String[] tokens = testString.split(" ");
        op.processTokens(tokens);

        Assert.assertFalse(op.readyToGetResult());


        testString = "y = 3";

        tokens = testString.split(" ");
        op.processTokens(tokens);

        Assert.assertFalse(op.readyToGetResult());


        testString = "y = 5.5";

        tokens = testString.split(" ");
        op.processTokens(tokens);

        Assert.assertFalse(op.readyToGetResult());


        testString = "+ *";

        tokens = testString.split(" ");
        op.processTokens(tokens);

        Assert.assertFalse(op.readyToGetResult());


        testString = "/";

        tokens = testString.split(" ");
        op.processTokens(tokens);

        Assert.assertTrue(op.readyToGetResult());


        testString = "y = 3abc";

        tokens = testString.split(" ");
        op.processTokens(tokens);

        Assert.assertFalse(op.readyToGetResult());


        testString = "*";

        tokens = testString.split(" ");
        op.processTokens(tokens);

        Assert.assertFalse(op.readyToGetResult());
    }

    @Test
    public void getResult() {
        Operation op = new Operation();

        Assert.assertEquals(op.getResult().variable.intValue(), 0);
        Assert.assertEquals(op.getResult().variableType, "unknown");

        String testString = "x = 3";
        String[] tokens = testString.split(" ");
        op.processTokens(tokens);
        testString = "y=2.5";
        tokens = testString.split(" ");
        op.processTokens(tokens);
        testString = " +    ";
        tokens = testString.split(" ");
        op.processTokens(tokens);

        Assert.assertEquals(op.getResult().variable.floatValue(), 5.5, 0.1);
        Assert.assertEquals(op.getResult().variableType, "float");


        testString = "y =      2";
        tokens = testString.split(" ");
        op.processTokens(tokens);

        Assert.assertEquals(op.getResult().variable.intValue(), 5);
        Assert.assertEquals(op.getResult().variableType, "int");


        testString = "y =      2abc";
        tokens = testString.split(" ");
        op.processTokens(tokens);

        Assert.assertEquals(op.getResult().variable.intValue(), 5);
        Assert.assertEquals(op.getResult().variableType, "int");


        testString = "y = 1.1";
        tokens = testString.split(" ");
        op.processTokens(tokens);
        testString = "y =2.2";
        tokens = testString.split(" ");
        op.processTokens(tokens);

        Assert.assertEquals(op.getResult().variable.floatValue(), 3.3, 0.1);
        Assert.assertEquals(op.getResult().variableType, "float");


        testString = "y = -2";
        tokens = testString.split(" ");
        op.processTokens(tokens);
        testString = "x = -8";
        tokens = testString.split(" ");
        op.processTokens(tokens);
        testString = "  *         ";
        tokens = testString.split(" ");
        op.processTokens(tokens);

        Assert.assertEquals(op.getResult().variable.intValue(), 16);
        Assert.assertEquals(op.getResult().variableType, "int");


        testString = "y = 3";
        tokens = testString.split(" ");
        op.processTokens(tokens);
        testString = "x = 5";
        tokens = testString.split(" ");
        op.processTokens(tokens);
        testString = "/         ";
        tokens = testString.split(" ");
        op.processTokens(tokens);

        Assert.assertEquals(op.getResult().variable.intValue(), 1);
        Assert.assertEquals(op.getResult().variableType, "int");


        testString = "y = 3";
        tokens = testString.split(" ");
        op.processTokens(tokens);
        testString = "x = 5.5";
        tokens = testString.split(" ");
        op.processTokens(tokens);
        testString = "-         ";
        tokens = testString.split(" ");
        op.processTokens(tokens);

        Assert.assertEquals(op.getResult().variable.floatValue(), 2.5, 0.1);
        Assert.assertEquals(op.getResult().variableType, "float");
    }
}