import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.channels.SelectableChannel;

public class Main {

    private static String x = "";
    private static String y = "";
    private static String xType;
    private static String yType;
    private static String operation = "";

    public static void main(String[] args) throws IOException {
        System.out.println("Hello! I am your simple calculator. Try smth like this:\nx = 2\ny = 3\n+");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String newLine;
        String[] currentTokens;
        while (!(newLine = br.readLine()).toLowerCase().equals("exit")){
            currentTokens = newLine.split("=");
            proccess(currentTokens);

            if (!x.isEmpty() && !y.isEmpty() && !operation.isEmpty())
                perform();
        }
    }

    private static void proccess(String[] tokens) {

        if (tokens.length == 1) {
            if (tokens[0].trim().length() == 1) {
                if (!operation.isEmpty()) {
                    System.out.println("Operation already exists!");
                    return;
                }

                operation = tokens[0].trim();
            }
            else {
                System.out.println("Something's wrong!");
            }
        }

        if (tokens.length == 2) {
            if (tokens[0].trim().equals("x")) {
                if (!x.isEmpty()) {
                    System.out.println("x already exists!");
                    return;
                }

                if (!(xType = getType(tokens[1].trim())).isEmpty())
                    x = tokens[1].trim();
            }
            if (tokens[0].trim().equals("y")) {
                if (!y.isEmpty()) {
                    System.out.println("y already exists!");
                    return;
                }

                if (!(yType = getType(tokens[1].trim())).isEmpty())
                    y = tokens[1].trim();
            }
        }

        if (tokens.length > 2) {
            System.out.println("Something's wrong!");
            return;
        }

        if (!x.isEmpty() && !y.isEmpty() && !operation.isEmpty())
            perform();
    }

    public static void perform() {
        String xType = getType(x), yType = getType(y);
        float result;

        if (!xType.isEmpty() && !yType.isEmpty()) {
            result = calculate(Float.parseFloat(x), Float.parseFloat(y));

            if (xType.equals("int") && yType.equals("int"))
                System.out.println("x " + operation + " y = " + (int) result); //Округлится вниз, как и нужно.
            else
                System.out.println("x " + operation + " y = " + result);
        }

        //Все обнуляем
        x = "";
        y = "";
        operation = "";
    }

    public static float calculate(float x, float y) {
        switch (operation.charAt(0)) {
            case '+':
                return x + y;
            case '-':
                return x - y;
            case '/':
                return x / y;
            case '*':
                return x * y;
            default:
                System.out.println("Wrong operation!");
                break;
        }

        return 0;
    }

    public static String getType(String operand) {
        if (operand.contains(".")) {
            try {
                Float.parseFloat(operand);
                return "float";
            } catch (NumberFormatException e) {
                System.err.println(e);
            }
        }

        try {
            Integer.parseInt(operand);
            return "int";
        } catch (NumberFormatException e) {
            System.err.println(e);
        }

        return "";
    }

}
