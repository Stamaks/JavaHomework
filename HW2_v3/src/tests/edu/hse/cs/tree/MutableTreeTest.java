package edu.hse.cs.tree;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class MutableTreeTest {

    @Test
    public void testRoot() {
        MutableParentNode<String> root = populateTree();
        Assertions.assertEquals("Root", root.getObject());
    }

    @Test
    public void testGetChildren() {
        MutableParentNode<String> root = new MutableParentNode<>("Root");

        MutableParentNode<String> parent0 = new MutableParentNode<>("Parent0");
        MutableParentNode<String> parent1 = new MutableParentNode<>("Parent1");

        MutableChildNode<String> child0 = new MutableChildNode<>("Child0");

        Set<ChildMutable<String>> rootChildren = new HashSet<>(3);
        rootChildren.add(parent0);
        rootChildren.add(parent1);
        rootChildren.add(child0);
        root.setChildren(rootChildren);
        parent0.setParent(root);
        parent1.setParent(root);
        child0.setParent(root);

        Assertions.assertEquals(3, root.getChildren().size());
        Assertions.assertEquals(rootChildren, root.getChildren());

    }

    @Test
    public void testAddChild() {
        MutableParentNode<String> root =
                (MutableParentNode<String>) TreeImporter.importMutableTree("MutableParentNode(Root)");

        MutableChildNode<String> child0 = new MutableChildNode<>("Child0");

        root.addChild(child0);

        Assertions.assertEquals(1, root.getChildren().size());
        Assertions.assertTrue(root.getChildren().contains(child0));
    }

    @Test
    public void testToStringFormMutableTree2(){
        MutableParentNode<String> root = populateTree();
        String stringForm = root.toStringForm("");

        // Проверяем, что все ноды содержатся. Не можем проверить иначе, потому что HashSet не запоминает порядок
        Assertions.assertTrue(stringForm.contains("MutableParentNode(Root)"));
        Assertions.assertTrue(stringForm.contains("\n    MutableParentNode(Parent0)"));
        Assertions.assertTrue(stringForm.contains("\n        MutableChildNode(Child00)"));
        Assertions.assertTrue(stringForm.contains("\n        MutableChildNode(Child01)"));
        Assertions.assertTrue(stringForm.contains("\n    MutableParentNode(Parent1)"));
        Assertions.assertTrue(stringForm.contains("\n        MutableChildNode(Child10)"));
        Assertions.assertTrue(stringForm.contains("\n    MutableChildNode(Child0)"));

        // Рандомная проверка на то, что наша строка не содержит каких-нибудь неверных данных
        Assertions.assertFalse(stringForm.contains("\n     MutableChildNode(Child0)"));
        Assertions.assertFalse(stringForm.contains("MutableChildNode(Child)"));
    }

    @Test
    public void testMutableTreeFromStringForm(){
        String stringForm = "MutableParentNode(Root)\n" +
                "    MutableParentNode(Parent0)\n" +
                "        MutableChildNode(Child00)\n" +
                "        MutableChildNode(Child01)\n" +
                "    MutableParentNode(Parent1)\n" +
                "        MutableChildNode(Child10)\n" +
                "    MutableChildNode(Child0)";

        MutableParentNode<String> newRoot = (MutableParentNode<String>) TreeImporter.importMutableTree(stringForm);
        Assertions.assertNotNull(newRoot);
        Assertions.assertEquals("Root", newRoot.getObject());
        Assertions.assertEquals(3, newRoot.getChildren().size());

        List<String> objects = new ArrayList<>();
        List<ChildMutable> children = new ArrayList<>();

        for (ChildMutable child : newRoot.getChildren()) {
            if (child instanceof MutableParentNode)
                objects.add((String) child.getObject());
            else
                objects.add((String) child.getObject());
            children.add(child);
        }

        Assertions.assertTrue(objects.contains("Parent0"));
        Assertions.assertTrue(objects.contains("Parent1"));
        Assertions.assertTrue(objects.contains("Child0"));
        Assertions.assertEquals(3, objects.size());

        for (ChildMutable child : children) {
            if (child.getObject().equals("Parent0")) {
                Assertions.assertEquals("Root", child.getParent().getObject());
                Assertions.assertEquals(2, ((MutableParentNode) child).getChildren().size());

                objects.clear();
                Set<ChildMutable<String>> childSet = ((MutableParentNode) child).getChildren();
                for (ChildMutable ch : childSet)
                {
                    objects.add((String) ch.getObject());

                    Assertions.assertEquals("Parent0", ch.getParent().getObject());
                }

                Assertions.assertTrue(objects.contains("Child00"));
                Assertions.assertTrue(objects.contains("Child01"));
            }

            if (child.getObject().equals("Parent1")) {
                Assertions.assertEquals("Root", child.getParent().getObject());
                Assertions.assertEquals(1, ((MutableParentNode) child).getChildren().size());

                objects.clear();
                Set<ChildMutable<String>> childSet = ((MutableParentNode) child).getChildren();
                for (ChildMutable ch : childSet)
                {
                    objects.add((String) ch.getObject());

                    Assertions.assertEquals("Parent1", ch.getParent().getObject());
                }

                Assertions.assertTrue(objects.contains("Child10"));
            }

            if (child.getObject().equals("Child0"))
            {
                Assertions.assertEquals("Root", child.getParent().getObject());
            }
        }
    }

    @Test void testHasChildWithValue() {
        MutableParentNode<String> root = populateTree();
        String stringForm = root.toStringForm("");

        MutableParentNode<String> newRoot = (MutableParentNode<String>) TreeImporter.importMutableTree(stringForm);
        Assertions.assertNotNull(newRoot);
        Assertions.assertTrue(newRoot.hasChildWithValue("Child0"));
        Assertions.assertTrue(newRoot.hasChildWithValue("Parent0"));
        Assertions.assertTrue(newRoot.hasChildWithValue("Parent1"));
        Assertions.assertFalse(newRoot.hasChildWithValue("Child00"));
        Assertions.assertFalse(newRoot.hasChildWithValue("Child01"));
        Assertions.assertFalse(newRoot.hasChildWithValue("Child10"));
    }

    @Test
    public void testHasDescendadntWithValue(){
        MutableParentNode<String> root = populateTree();
        String stringForm = root.toStringForm("");

        MutableParentNode<String> newRoot = (MutableParentNode<String>) TreeImporter.importMutableTree(stringForm);
        Assertions.assertNotNull(newRoot);
        Assertions.assertFalse(newRoot.hasDescendantWithValue("Root"));
        Assertions.assertTrue(newRoot.hasDescendantWithValue("Parent0"));
        Assertions.assertTrue(newRoot.hasDescendantWithValue("Child00"));
        Assertions.assertTrue(newRoot.hasDescendantWithValue("Child01"));
        Assertions.assertTrue(newRoot.hasDescendantWithValue("Parent1"));
        Assertions.assertTrue(newRoot.hasDescendantWithValue("Child10"));
        Assertions.assertTrue(newRoot.hasDescendantWithValue("Child0"));

        Assertions.assertFalse(newRoot.hasDescendantWithValue("Child000"));
    }

    @Test
    public void testGetAllDescendants() {
        MutableParentNode<String> root = new MutableParentNode<>("Root");

        MutableParentNode<String> parent0 = new MutableParentNode<>("Parent0");
        MutableParentNode<String> parent1 = new MutableParentNode<>("Parent1");

        MutableChildNode<String> child0 = new MutableChildNode<>("Child0");
        MutableChildNode<String> child00 = new MutableChildNode<>("Child00");
        MutableChildNode<String> child01 = new MutableChildNode<>("Child01");
        MutableChildNode<String> child10 = new MutableChildNode<>("Child10");

        Set<ChildMutable<String>> descendants = new HashSet<>();

        Set<ChildMutable<String>> rootChildren = new HashSet<>(3);
        rootChildren.add(parent0);
        rootChildren.add(parent1);
        rootChildren.add(child0);
        root.setChildren(rootChildren);
        parent0.setParent(root);
        parent1.setParent(root);
        child0.setParent(root);

        Set<ChildMutable<String>> parent0Children = new HashSet<>(2);
        parent0Children.add(child00);
        parent0Children.add(child01);
        parent0.setChildren(parent0Children);
        child00.setParent(parent0);
        child01.setParent(parent0);

        Set<ChildMutable<String>> parent1Children = new HashSet<>(1);
        parent1Children.add(child10);
        parent1.setChildren(parent1Children);
        child10.setParent(parent1);

        descendants.addAll(rootChildren);
        descendants.addAll(parent0Children);
        descendants.addAll(parent1Children);

        Assertions.assertEquals(descendants, root.getAllDescendants());
    }

    public static MutableParentNode<String> populateTree(){
        MutableParentNode<String> root = new MutableParentNode<>("Root");

        MutableParentNode<String> parent0 = new MutableParentNode<>("Parent0");
        MutableParentNode<String> parent1 = new MutableParentNode<>("Parent1");

        MutableChildNode<String> child0 = new MutableChildNode<>("Child0");
        MutableChildNode<String> child00 = new MutableChildNode<>("Child00");
        MutableChildNode<String> child01 = new MutableChildNode<>("Child01");
        MutableChildNode<String> child10 = new MutableChildNode<>("Child10");

        Set<ChildMutable<String>> rootChildren = new HashSet<>(3);
        rootChildren.add(parent0);
        rootChildren.add(parent1);
        rootChildren.add(child0);
        root.setChildren(rootChildren);
        parent0.setParent(root);
        parent1.setParent(root);
        child0.setParent(root);

        Set<ChildMutable<String>> parent0Children = new HashSet<>(2);
        parent0Children.add(child00);
        parent0Children.add(child01);
        parent0.setChildren(parent0Children);
        child00.setParent(parent0);
        child01.setParent(parent0);

        Set<ChildMutable<String>> parent1Children = new HashSet<>(1);
        parent1Children.add(child10);
        parent1.setChildren(parent1Children);
        child10.setParent(parent1);

        return root;
    }

}
