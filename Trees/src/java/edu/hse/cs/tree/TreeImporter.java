package edu.hse.cs.tree;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TreeImporter {
    public static <T> MutableRootNode<T> importMutableTree(String input) {

        if (input.isEmpty())
            return null;

        return (MutableRootNode) getChild(input, 0, 0, "");
    }

    public static AbstractTreeNode getChild(String input, int currentIndentSize, final int indentSize, String objectType) {
        String regex = "[)][\n] {" + currentIndentSize + "}[M]";
        String[] nodes = input.split(regex);

        // Разбиваем по регэкспу, а потом добавляем символы, которые случайно (или не случайно) удалили
        for (int i = 0; i < nodes.length; i++) {
            if (i != nodes.length - 1)
                nodes[i] += ")";
            if (i != 0){
                nodes[i] = "M" + nodes[i];
            }
        }

        // Если разбиения не произошло или инпут вообще пуст, кидаем исключение
        if (nodes.length == 0 || input.isEmpty())
            throw new IllegalArgumentException("Input is empty!");

        // На случай, если вдруг как-то криво обрезалось, убираем пробелы
        String parentNode = nodes[0].trim();

        // Проверяем нод на совпадение с шаблоном. Не совпадает - кидаем исключение
        if (!parentNode.matches("Mutable(Root|Parent|Child)Node[(].*[)]"))
            throw new IllegalArgumentException("Input doesn't match a template!");

        // Находим индекс, после которого идет сам object
        int indexOfLeftPareth = parentNode.indexOf("(");

        // Вычленяем имя класса
        String className = parentNode.substring(0, indexOfLeftPareth);

        // Вычленяем сам объект
        String objectString = parentNode.substring(indexOfLeftPareth + 1, parentNode.length() - 1);

        // Создали ссылку на текущий нод
        AbstractTreeNode treeNode = null;

        if (className.equals(MutableRootNode.class.getSimpleName())) {

            // Проверка, нет ли в дереве еще одного корня (если objectType уже завели, значит, корень был)
            if (!objectType.isEmpty())
                throw new IllegalArgumentException("Input doesn't match a template! Several roots!");

            // Выясняем, какой тип у объекта корня
            objectType = getObjectType(objectString);

            // Создаем корень
            treeNode = createNode(objectString, objectType, MutableRootNode.class.getSimpleName());

        }
        else if (className.equals(MutableParentNode.class.getSimpleName())) {

            // Если objectType пуст, значит, не было корня
            if (objectType.isEmpty())
                throw new IllegalArgumentException("Input doesn't match a template! No root!");

            // Выясняем, какой тип у объекта родителя
            String thisObjectType = getObjectType(objectString);

            // Если тип текущего объекта не совпадает с типом объекта дерева, кидаем исключени
            if (!thisObjectType.equals(objectType))
                throw new IllegalArgumentException("Input doesn't match a template! Object type mismatch!");

            // Создаем parent с объектом типа objectType
            treeNode = createNode(objectString, objectType, MutableParentNode.class.getSimpleName());

        } else {

            // Если objectType пуст, значит, не было корня
            if (objectType.isEmpty())
                throw new IllegalArgumentException("Input doesn't match a template! No root!");

            // Выясняем, какой тип у объекта ребенка
            String thisObjectType = getObjectType(objectString);

            // Если тип текущего объекта не совпадает с типом объекта дерева, кидаем исключени
            if (!thisObjectType.equals(objectType))
                throw new IllegalArgumentException("Input doesn't match a template! Object type mismatch!");

            // Создаем child с объектом типа objectType
            treeNode = createNode(objectString, objectType, MutableChildNode.class.getSimpleName());
        }

        // Получаем и добавляем детей
        for (int i = 1; i < nodes.length; i++) {
            AbstractTreeNode node = getChild(nodes[i], currentIndentSize + indentSize,
                    indentSize, objectType);

            // Добавляем корню ребенка
            if (treeNode instanceof MutableRootNode)
                ((MutableRootNode) treeNode).addChild(node);

            // Добавляем родителю ребенка
            if (treeNode instanceof  MutableParentNode)
                ((MutableParentNode) treeNode).addChild(node);

            if (treeNode == null)
                throw new IllegalArgumentException("TreeNode is null!");

            // Полученному ноду добавляем родителя
            if (node instanceof MutableParentNode)
                ((MutableParentNode) node).setParent((IParent) treeNode);
            else
                ((MutableChildNode) node).setParent((IParent) treeNode);
        }

        return treeNode;
    }

    private static String getObjectType(String objectString) {

        if (objectString.matches("-{0,1}[0-9]+"))
            return "int";

        if (objectString.matches("-{0,1}[0-9]+[.,][0-9]+"))
            return "double";

        return "String";
    }

    private static AbstractTreeNode createNode(String objectString, String objectType, String className) {
        if (objectType.equals("int")) {
            try {
                int object = Integer.parseInt(objectString);

                if (className.equals(MutableRootNode.class.getSimpleName()))
                    return new MutableRootNode(object);

                if (className.equals(MutableParentNode.class.getSimpleName()))
                    return new MutableParentNode(object);

                if (className.equals(MutableChildNode.class.getSimpleName()))
                    return new MutableChildNode(object);

            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Type mismatch! Too big number!");
            }
        }

        if (objectType.equals("double")) {
            try {
                double object = Double.parseDouble(objectString);

                if (className.equals(MutableRootNode.class.getSimpleName()))
                    return new MutableRootNode(object);

                if (className.equals(MutableParentNode.class.getSimpleName()))
                    return new MutableParentNode(object);

                if (className.equals(MutableChildNode.class.getSimpleName()))
                    return new MutableChildNode(object);

            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Type mismatch! Big number!");
            }
        }

        if (className.equals(MutableRootNode.class.getSimpleName()))
            return new MutableRootNode(objectString);

        if (className.equals(MutableParentNode.class.getSimpleName()))
            return new MutableParentNode(objectString);

        if (className.equals(MutableChildNode.class.getSimpleName()))
            return new MutableChildNode(objectString);

        return null;
    }
}
