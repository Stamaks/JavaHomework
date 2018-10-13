import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class Main {

    private static String x = "";
    private static String y = "";
    private static String xType;
    private static String yType;
    private static char operation = ' ';

    public static void main(String[] args) throws IOException {

        System.out.println("Hello! I am your simple calculator. Try smth like this:\nx = 2\ny = 3\n+");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String newLine;
        String[] currentTokens;
        while (!(newLine = br.readLine()).toLowerCase().equals("exit")){
            currentTokens = newLine.split("=");
            proccess(currentTokens);
        }

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        System.out.print(sdf.format(cal.getTime()) );
    }

    private static void proccess(String[] tokens) {

        if (tokens.length == 1) {

            if (tokens[0].trim().length() == 1) {

                operation = tokens[0].trim().charAt(0);

                if (!"+-*/".contains(operation + "")) {
                    System.err.println("This operation is not implemented!");
                    operation = ' ';
                }
            }
            else {
                System.err.println("Something's wrong!");
            }
        }

        if (tokens.length == 2) {

            if (tokens[0].trim().equals("x")) {

                //Проверка на то, что введенная строка после знака = это число
                if (!(xType = getType(tokens[1].trim())).isEmpty())
                    x = tokens[1].trim();
            }
            else if (tokens[0].trim().equals("y")) {

                    //Проверка на то, что введенная строка после знака = это число
                    if (!(yType = getType(tokens[1].trim())).isEmpty())
                        y = tokens[1].trim();
                }
                else {
                    System.err.println("Enter x or y!");
            }
        }

        if (tokens.length > 2) {
            System.err.println("Something's wrong!");
            return;
        }

        if (!x.isEmpty() && !y.isEmpty() && operation != ' ')
            if (operation == '/' && Float.parseFloat(y) == 0)
                System.err.println("Zero division!");
            else
                perform();
    }

    public static void perform() {

        float result;

        result = calculate(Float.parseFloat(x), Float.parseFloat(y));

        if (getType(x).equals("int") && getType(y).equals("int")) {
            if (result > Integer.MAX_VALUE || result < Integer.MIN_VALUE)
                System.err.println("Too big number!");
            else
                System.out.println("x " + operation + " y = " + (int) result); //Округлится вниз, как и нужно.
        }
        else
            System.out.println("x " + operation + " y = " + String.format("%.2f", result));
    }

    public static float calculate(float x, float y) {

        switch (operation) {
            case '+':
                return x + y;
            case '-':
                return x - y;
            case '/':
                return x / y;
            case '*':
                return x * y;
            default:
                System.err.println("Wrong operation!");
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
                System.err.println("Number format is wrong!");
            }
        }
        else {
            try {
                Integer.parseInt(operand);
                return "int";
            } catch (NumberFormatException e) {
                System.err.println("Number format is wrong!");
            }
        }

        return "";
    }

}
