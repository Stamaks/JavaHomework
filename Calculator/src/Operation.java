public class Operation {
    NumberWithType x;
    NumberWithType y;
    char operation;

    public Operation() {
        x = new NumberWithType(0, "unknown");
        y = new NumberWithType(0, "unknown");
        operation = ' ';
    }

    public void processTokens(String[] tokens) {

        // Если токен всего один, вероятно, это операция
        if (tokens.length == 1) {

            if (tokens[0].trim().length() == 1) {

                if (!"+-*/".contains(tokens[0].trim().charAt(0) + "")) {
                    System.err.println("This operation is not implemented!");
                }
                else
                    operation = tokens[0].trim().charAt(0);
            }
            else {
                System.err.println("Something's wrong!");
            }
        }

        if (tokens.length == 2) {

            // Пытаемся получить число
            NumberWithType number = transformToken(tokens[1].trim());

            // Если число удалось получить, запоминаем его
            if (!number.variableType.equals("unknown")) {

                switch (tokens[0].trim()) {
                    case "x":
                        x = number;
                        break;
                    case "y":
                        y = number;
                        break;
                    default:
                        System.err.println("Enter x or y!");
                        break;
                }
            }
        }

        if (tokens.length > 2) {
            System.err.println("Something's wrong!");
        }
    }

    public boolean readyToGetResult() {
        return (operation != ' ' && !x.variableType.equals("unknown") && !y.variableType.equals("unknown"));
    }

    public NumberWithType getResult()
    {
        if (readyToGetResult())
            return calculate();
        else
            return new NumberWithType(0, "unknown");
    }

    private NumberWithType calculate() {

        switch (operation) {
            case '+':
                return add();
            case '-':
                return sub();
            case '/':
                return div();
            case '*':
                return mult();
            default:
                System.err.println("Wrong operation!");
                break;
        }

        return new NumberWithType(0, "unknown");
    }

    private NumberWithType transformToken(String operand) {

        // Если строка содержит строчку, вероятнее всего, это float
        if (operand.contains(".")) {
            try {
                return new NumberWithType(Float.parseFloat(operand), "float");
            } catch (NumberFormatException e) {
                System.err.println("Number format is wrong!");
            }
        }
        else {
            try {
                return new NumberWithType(Integer.parseInt(operand), "int");
            } catch (NumberFormatException e) {
                System.err.println("Number format is wrong!");
            }
        }

        return new NumberWithType(0, "unknown");
    }

    private NumberWithType add() {

        // Проверка, что оба числа имеют тип int или float
        if (x.variableType.equals("unknown") || y.variableType.equals("unknown")) {
            System.err.println("Unknown variable type!");
            return new NumberWithType(0, "unknown");
        }

        // Отдельно рассматриваем случай, когда оба числа - int, тогда вернем int
        if (x.variableType.equals("int") && y.variableType.equals("int")) {

            if (x.variable.longValue() + y.variable.longValue() <= Integer.MAX_VALUE &&
                    x.variable.longValue() + y.variable.longValue() >= Integer.MIN_VALUE)
                return new NumberWithType(x.variable.intValue() + y.variable.intValue(), "int");
            else
                System.err.println("Wrong numbers size!");
        }

        return new NumberWithType(x.variable.floatValue() + y.variable.floatValue(), "float");
    }

    private NumberWithType sub() {

        // Проверка, что оба числа имеют тип int или float
        if (x.variableType.equals("unknown") || y.variableType.equals("unknown")) {
            System.err.println("Unknown variable type!");
            return new NumberWithType(0, "unknown");
        }

        // Отдельно рассматриваем случай, когда оба числа - int, тогда вернем int
        if (x.variableType.equals("int") && y.variableType.equals("int")) {

            // Проверяем на переполнение
            if (x.variable.longValue() - y.variable.longValue() <= Integer.MAX_VALUE &&
                    x.variable.longValue() - y.variable.longValue() >= Integer.MIN_VALUE)
                return new NumberWithType(x.variable.intValue() - y.variable.intValue(), "int");
            else
                System.err.println("Wrong numbers size!");
        }

        return new NumberWithType(x.variable.floatValue() - y.variable.floatValue(), "float");
    }

    private NumberWithType div() {

        // Проверка, что оба числа имеют тип int или float
        if (x.variableType.equals("unknown") || y.variableType.equals("unknown")) {
            System.err.println("Unknown variable type!");
            return new NumberWithType(0, "unknown");
        }

        // Отдельно рассматриваем случай, когда оба числа - int, тогда вернем int
        if (x.variableType.equals("int") && y.variableType.equals("int")) {

            // Проверяем, не делим ли на 0
            if (y.variable.intValue() != 0)
                return new NumberWithType(x.variable.intValue() / y.variable.intValue(), "int");
        }

        // Проверяем, не делим ли на 0
        if (y.variable.floatValue() != 0)
            return new NumberWithType(x.variable.floatValue() / y.variable.floatValue(), "float");

        System.err.println("Zero division!");

        return new NumberWithType(0, "unknown");
    }

    private NumberWithType mult() {

        // Проверка, что оба числа имеют тип int или float
        if (x.variableType.equals("unknown") || y.variableType.equals("unknown")) {
            System.err.println("Unknown variable type!");
            return new NumberWithType(0, "unknown");
        }

        // Отдельно рассматриваем случай, когда оба числа - int, тогда вернем int
        if (x.variableType.equals("int") && y.variableType.equals("int")) {

            // Проверяем на переполнение
            if (x.variable.longValue() * y.variable.longValue() <= Integer.MAX_VALUE &&
                    x.variable.longValue() * y.variable.longValue() >= Integer.MIN_VALUE)
                return new NumberWithType(x.variable.intValue() * y.variable.intValue(), "int");
            else
                System.err.println("Wrong numbers size!");
        }

        return new NumberWithType(x.variable.floatValue() * y.variable.floatValue(), "float");
    }
}
