package edu.hse.cs.tree;

public class TreeImporter {
    public static AbstractTreeNode importMutableTree(String input) {

        if (input.isEmpty())
            return null;

        return getChild(input, 4, 4, "");
    }

    private static AbstractTreeNode getChild(String input, int currentIndentSize, final int indentSize, String objectType) {
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
        if (!parentNode.matches("Mutable(Parent|Child)Node[(].*[)]"))
            throw new IllegalArgumentException("Input doesn't match a template!");

        // Находим индекс, после которого идет сам object
        int indexOfLeftPareth = parentNode.indexOf("(");

        // Вычленяем имя класса
        String className = parentNode.substring(0, indexOfLeftPareth);

        // Вычленяем сам объект
        String objectString = parentNode.substring(indexOfLeftPareth + 1, parentNode.length() - 1);

        // Создали ссылку на текущий нод
        AbstractTreeNode treeNode;

        if (className.equals(MutableParentNode.class.getSimpleName())) {

            // Если objectType пуст, значит, не было корня. Выясняем, какой тип у объекта корня
            if (objectType.isEmpty())
                objectType = getObjectType(objectString);

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

            // Добавляем родителю ребенка
            if (treeNode instanceof  MutableParentNode)
                ((MutableParentNode) treeNode).addChild((ChildMutable) node);

            if (treeNode == null)
                throw new IllegalArgumentException("TreeNode is null!");

            // Полученному ноду добавляем родителя
            if (node instanceof MutableParentNode)
                ((MutableParentNode) node).setParent((ParentMutable) treeNode);
            else
                ((MutableChildNode) node).setParent((ParentMutable) treeNode);
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

                if (className.equals(MutableParentNode.class.getSimpleName()))
                    return new MutableParentNode<>(object);

                if (className.equals(MutableChildNode.class.getSimpleName()))
                    return new MutableChildNode<>(object);

            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Type mismatch! Too big number!");
            }
        }

        if (objectType.equals("double")) {
            try {
                double object = Double.parseDouble(objectString);

                if (className.equals(MutableParentNode.class.getSimpleName()))
                    return new MutableParentNode<>(object);

                if (className.equals(MutableChildNode.class.getSimpleName()))
                    return new MutableChildNode<>(object);

            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Type mismatch! Big number!");
            }
        }

        if (className.equals(MutableParentNode.class.getSimpleName()))
            return new MutableParentNode<>(objectString);

        if (className.equals(MutableChildNode.class.getSimpleName()))
            return new MutableChildNode<>(objectString);

        return null;
    }
}
