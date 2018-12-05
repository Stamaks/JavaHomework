//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//
//public class Main {
//
//    public static void main(String[] args) throws IOException {
//
//        System.out.println("Hello! I am your simple calculator. Try smth like this:\nx = 2\ny = 3\n+");
//
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//
//        String newLine;
//        String[] currentTokens;
//        Operation operation = new Operation();
//        NumberWithType currentResult;
//        while (!(newLine = br.readLine()).toLowerCase().equals("exit")){
//            currentTokens = newLine.split("=");
//            operation.processTokens(currentTokens);
//
//            if (operation.readyToGetResult()) {
//                currentResult = operation.getResult();
//
//                switch (currentResult.variableType) {
//                    case "float":
//                        System.out.println("x " + operation.operation + " y = " +
//                                String.format("%.2f", (float) currentResult.variable));
//                        break;
//                    case "int":
//                        System.out.println("x " + operation.operation + " y = " +
//                                (int) currentResult.variable);
//                        break;
//                    default:
//                        break;
//                }
//            }
//        }
//
//        Calendar cal = Calendar.getInstance();
//        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
//        System.out.println(sdf.format(cal.getTime()) );
//    }
//
//}
