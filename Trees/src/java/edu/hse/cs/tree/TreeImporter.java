package edu.hse.cs.tree;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TreeImporter {
    public static <T> MutableRootNode<T> importMutableTree(String input) {
        // TODO place your implementation of tree parser here.
        // Here you are also allowed to create static private subroutines in this class

        if (input.isEmpty()) {
            System.out.println("Input is empty!");
        }
        else {
            StringTokenizer st = new StringTokenizer(input);

            String currentToken, className, objectString;
            long numberOfIterations = 0;
            while (st.hasMoreTokens()) {
                ++numberOfIterations;

                currentToken = st.nextToken();

                // Проверяем валидность текущего токена
                if (!currentToken.matches("Mutable(Root|Parent|Child)Node[(].*[)]"))
                    System.out.println("Input is wrong!");

                //TODO: проверять валидность входа, если надо
                // Проверка на то, что первая строка - рут, ...

                // Находим первую левую скобку и по ней делим (может быть строка, например, "бебе(бе)бебе))")
                int indexOfLeftPareth = currentToken.indexOf("(");

                className = currentToken.substring(0, indexOfLeftPareth);

                objectString = currentToken.substring(indexOfLeftPareth + 1, currentToken.length()-1);


//                if (!currentTokensSplit[0].matches("Mutable(Root|Parent|Child)Node") || currentTokensSplit.length < 2)
//                {
//                    System.out.println("Input is wrong!");
//                    break;
//                }
//                else
//                {
//                    if (numberOfIterations == 1 && !currentTokensSplit[0].equals("MutableRootNode"))
//                    {
//                        System.out.println("Input is wrong!");
//                        break;
//                    }
//                    else
//                    {
//                        if
//                    }
//                }
//
            }
        }



        return null; // temporary stub
    }

    public static AbstractTreeNode getChild(String input, int currentIndentSize, final int indentSize, String objectType) {
        String[] nodes = input.split("\\s{" + currentIndentSize + "}");

        if (nodes.length == 0 || input.isEmpty()) {
            System.out.println("Input is empty!");
            return null;
        }

        String parentNode = nodes[0];

        if (!parentNode.matches("Mutable(Root|Parent|Child)Node[(].*[)]")) {
            System.out.println("Input doesn't match a template!");
            return null;
        }

        int indexOfLeftPareth = parentNode.indexOf("(");

        String className = parentNode.substring(0, indexOfLeftPareth);

        String objectString = parentNode.substring(indexOfLeftPareth + 1, parentNode.length() - 1);

        // Создали ссылку на текущий нод
        AbstractTreeNode treeNode = null;

        if (className.equals("MutableRootNode")) {

            // Проверка, нет ли в дереве еще одного корня
            if (!objectType.isEmpty())
            {
                System.out.println("Input doesn't match a template! Several roots!");
                return null;
            }

            // Выясняем, какой тип у объекта корня и создаем корень
            if (objectString.matches("-{0,1}[0-9]+")) {
                objectType = "int";

                try {
                    int object = Integer.parseInt(objectString);
                    treeNode = new MutableRootNode(object);
                } catch (NumberFormatException e) {
                    System.out.println("Type mismatch! Too big number!");
                    return null;
                }

            } else if (objectString.matches("-{0,1}[0-9]+[.,][0-9]+")) {
                objectType = "double";

                try {
                    double object = Double.parseDouble(objectString);
                    treeNode = new MutableRootNode(object);
                } catch (NumberFormatException e) {
                    System.out.println("Type mismatch! Too big number!");
                    return null;
                }

            } else {
                objectType = "String";

                treeNode = new MutableRootNode(objectString);
            }

        }
        else if (className.equals("MutableParentNode")) {
            // Если objectType пуст, значит, не было корня
            if (objectType.isEmpty())
            {
                System.out.println("Input doesn't match a template! No root!");
                return null;
            }

            // Создаем ссылку на родителя
            MutableParentNode parent = null;

            // Создаем parent с объектом типа objectType
            if (objectString.matches("-{0,1}[0-9]+") && objectType.equals("int")) {
                try {
                    int object = Integer.parseInt(objectString);
                    parent = new MutableParentNode(object);
                } catch (NumberFormatException e) {
                    System.out.println("Type mismatch! Too big number!");
                    return null;
                }

            } else if (objectString.matches("-{0,1}[0-9]+[.,][0-9]+") && objectType.equals("double")) {
                try {
                    double object = Double.parseDouble(objectString);
                    parent = new MutableParentNode(object);
                } catch (NumberFormatException e) {
                    System.out.println("Type mismatch! Too big number!");
                    return null;
                }

            } else if (objectType.equals("String")) {
                parent = new MutableParentNode(objectString);
            }
            else {
                System.out.println("Type mismatch! Expected " + objectType);
            }

            // Получаем и добавляем детей
            for (int i = 1; i < nodes.length - 1; i++) {
                AbstractTreeNode node = getChild(nodes[i], currentIndentSize + indentSize,
                        indentSize, objectType);
                parent.addChild(node);

                if (node instanceof MutableParentNode)
                    ((MutableParentNode) node).setParent(parent);
                else
                    ((MutableChildNode) node).setParent(parent);
            }

            return parent;

        }

        // Получаем и добавляем детей
        for (int i = 1; i < nodes.length - 1; i++) {
            AbstractTreeNode node = getChild(nodes[i], currentIndentSize + indentSize,
                    indentSize, objectType);

            if (treeNode instanceof MutableRootNode) {
                ((MutableRootNode) treeNode).addChild(node);
            }
            if (treeNode instanceof  MutableParentNode)
                ((MutableParentNode) treeNode).addChild(node);
            if (treeNode == null) {
                System.out.println("TreeNode is null!!!");
                return null;
            }

            // TODO: мб не заработает!
            if (node instanceof MutableParentNode)
                ((MutableParentNode) node).setParent((IParent) treeNode);
            else
                ((MutableChildNode) node).setParent((IParent) treeNode);
        }

        return null;
    }

//    public static String[] getChildren(String input, String indent)
//    {
//        input.split(indent);
//
//
//        if (input.isEmpty()) {
//            return null;
//        }
//        else {
//
//            // Отрезаем первую строку, расспличиваем по детям. Для каждого ребенка - отрезаем первую строку, расспличиваем...
//            // Каждую отрезанную строку проверяем на простую валидность любому ок паттерну
//            // Получили листья, засунули в массив. Преобразуем лист, проверяя, children ли он.
//            // Засунули чилдрена
//
//            // Как проверять, что у нодов один и тот же тип? Глабальная приватная статик переменная
//
//            String currentToken, className, objectString;
//            long numberOfIterations = 0;
//            while (st.hasMoreTokens()) {
//                ++numberOfIterations;
//
//                currentToken = st.nextToken();
//
//                // Проверяем валидность текущего токена
//                if (!currentToken.matches("Mutable(Root|Parent|Child)Node[(].*[)]"))
//                    System.out.println("Input is wrong!");
//            }
//        }
//    }
}
