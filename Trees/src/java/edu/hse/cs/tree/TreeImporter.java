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
            getChild(input, 0, 0, "");
        }



        return null; // temporary stub
    }

    public static AbstractTreeNode getChild(String input, int currentIndentSize, final int indentSize, String objectType) {
        String regex = "[)][\n] {" + currentIndentSize + "}[M]";
        String[] nodes = input.split(regex);

        for (int i = 0; i < nodes.length; i++) {
            if (i != nodes.length - 1)
                nodes[i] += ")";
            if (i != 0){
                nodes[i] = "M" + nodes[i];
            }
        }

        if (nodes.length == 0 || input.isEmpty()) {
            System.out.println("Input is empty!");
//            return null;
            throw new IllegalArgumentException("Input is empty!");
        }

        String parentNode = nodes[0].trim();

        if (!parentNode.matches("Mutable(Root|Parent|Child)Node[(].*[)]")) {
            System.out.println("Input doesn't match a template!");
//            return null;
            throw new IllegalArgumentException("Input doesn't match a template!");
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
//                return null;
                throw new IllegalArgumentException("Input doesn't match a template! Several roots!");
            }

            // Выясняем, какой тип у объекта корня и создаем корень
            if (objectString.matches("-{0,1}[0-9]+")) {
                objectType = "int";

                try {
                    int object = Integer.parseInt(objectString);
                    treeNode = new MutableRootNode(object);
                } catch (NumberFormatException e) {
                    System.out.println("Type mismatch! Too big number!");
//                    return null;
                    throw new IllegalArgumentException("Type mismatch! Big number!");
                }

            } else if (objectString.matches("-{0,1}[0-9]+[.,][0-9]+")) {
                objectType = "double";

                try {
                    double object = Double.parseDouble(objectString);
                    treeNode = new MutableRootNode(object);
                } catch (NumberFormatException e) {
                    System.out.println("Type mismatch! Too big number!");
//                    return null;
                    throw new IllegalArgumentException("Type mismatch! Big number!");
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
//                return null;
                throw new IllegalArgumentException("Input doesn't match a template! No root!");
            }

            // Создаем parent с объектом типа objectType
            if (objectString.matches("-{0,1}[0-9]+") && objectType.equals("int")) {
                try {
                    int object = Integer.parseInt(objectString);
                    treeNode = new MutableParentNode(object);
                } catch (NumberFormatException e) {
                    System.out.println("Type mismatch! Too big number!");
//                    return null;
                    throw new IllegalArgumentException("Type mismatch! Too big number!");
                }

            } else if (objectString.matches("-{0,1}[0-9]+[.,][0-9]+") && objectType.equals("double")) {
                try {
                    double object = Double.parseDouble(objectString);
                    treeNode = new MutableParentNode(object);
                } catch (NumberFormatException e) {
                    System.out.println("Type mismatch! Too big number!");
//                    return null;
                    throw new IllegalArgumentException("Type mismatch! Too big number!");
                }

            } else if (objectType.equals("String")) {
                treeNode = new MutableParentNode(objectString);
            }
            else {
                System.out.println("Type mismatch! Expected " + objectType);
            }

        } else {
            // Если objectType пуст, значит, не было корня
            if (objectType.isEmpty())
            {
                System.out.println("Input doesn't match a template! No root!");
//                return null;
                throw new IllegalArgumentException("Input doesn't match a template! No root!");
            }

            // Создаем parent с объектом типа objectType
            if (objectString.matches("-{0,1}[0-9]+") && objectType.equals("int")) {
                try {
                    int object = Integer.parseInt(objectString);
                    treeNode = new MutableChildNode(object);
                } catch (NumberFormatException e) {
                    System.out.println("Type mismatch! Too big number!");
//                    return null;
                    throw new IllegalArgumentException("Type mismatch!");
                }

            } else if (objectString.matches("-{0,1}[0-9]+[.,][0-9]+") && objectType.equals("double")) {
                try {
                    double object = Double.parseDouble(objectString);
                    treeNode = new MutableChildNode(object);
                } catch (NumberFormatException e) {
                    System.out.println("Type mismatch! Too big number!");
//                    return null;
                    throw new IllegalArgumentException("Type mismatch!");
                }

            } else if (objectType.equals("String")) {
                treeNode = new MutableChildNode(objectString);
            }
            else {
                System.out.println("Type mismatch! Expected " + objectType);
            }
        }

        // Получаем и добавляем детей
        for (int i = 1; i < nodes.length; i++) {
            System.out.println(i + " " + nodes[i]);
            AbstractTreeNode node = getChild(nodes[i], currentIndentSize + indentSize,
                    indentSize, objectType);

            if (treeNode instanceof MutableRootNode) {
                ((MutableRootNode) treeNode).addChild(node);
            }
            if (treeNode instanceof  MutableParentNode)
                ((MutableParentNode) treeNode).addChild(node);
            if (treeNode == null) {
                System.out.println("TreeNode is null!!!");
//                return null;
                throw new IllegalArgumentException("TreeNode id null!");
            }

            if (node instanceof MutableParentNode)
                ((MutableParentNode) node).setParent((IParent) treeNode);
            else
                ((MutableChildNode) node).setParent((IParent) treeNode);
        }

        return treeNode;
    }
}
