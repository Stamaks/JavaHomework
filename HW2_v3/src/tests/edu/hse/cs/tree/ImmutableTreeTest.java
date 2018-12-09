package edu.hse.cs.tree;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static edu.hse.cs.tree.MutableTreeTest.populateTree;

public class ImmutableTreeTest {
    @Test
    public void testRoot() {
        MutableParentNode<String> root = populateTree();

        ImmutableParentNode<String> immRoot = new ImmutableParentNode<>(root);

        Assertions.assertEquals("Root", immRoot.getObject());
    }

    @Test
    public void testGetChildren() {
        MutableParentNode<String> root = populateTree();

        ImmutableParentNode<String> immRoot = new ImmutableParentNode<>(root);

        Assertions.assertEquals(3, immRoot.getChildren().size());

        List<String> objects = new ArrayList<>(3);
        for (ChildImmutable<String> child : immRoot.getChildren()) {
            objects.add(child.getObject());
        }

        Assertions.assertTrue(objects.contains("Parent0"));
        Assertions.assertTrue(objects.contains("Parent1"));
        Assertions.assertTrue(objects.contains("Child0"));
    }

    @Test
    public void testToStringFormMutableTree(){
        MutableParentNode<String> root = populateTree();

        ImmutableParentNode<String> immRoot = new ImmutableParentNode<>(root);

        String stringForm = immRoot.toStringForm("");

        // Проверяем, что все ноды содержатся. Не можем проверить иначе, потому что HashSet не запоминает порядок
        Assertions.assertTrue(stringForm.contains("ImmutableParentNode(Root)"));
        Assertions.assertTrue(stringForm.contains("\n    ImmutableParentNode(Parent0)"));
        Assertions.assertTrue(stringForm.contains("\n        ImmutableChildNode(Child00)"));
        Assertions.assertTrue(stringForm.contains("\n        ImmutableChildNode(Child01)"));
        Assertions.assertTrue(stringForm.contains("\n    ImmutableParentNode(Parent1)"));
        Assertions.assertTrue(stringForm.contains("\n        ImmutableChildNode(Child10)"));
        Assertions.assertTrue(stringForm.contains("\n    ImmutableChildNode(Child0)"));

        // Рандомная проверка на то, что наша строка не содержит каких-нибудь неверных данных
        Assertions.assertFalse(stringForm.contains("\n     ImmutableChildNode(Child0)"));
        Assertions.assertFalse(stringForm.contains("ImmutableChildNode(Child)"));
    }

    @Test void testHasChildWithValue() {
        MutableParentNode<String> root = populateTree();

        ImmutableParentNode<String> newRoot = new ImmutableParentNode<>(root);

        Assertions.assertNotNull(newRoot);
        Assertions.assertTrue(newRoot.hasChildWithValue("Child0"));
        Assertions.assertTrue(newRoot.hasChildWithValue("Parent0"));
        Assertions.assertTrue(newRoot.hasChildWithValue("Parent1"));
        Assertions.assertFalse(newRoot.hasChildWithValue("Child00"));
        Assertions.assertFalse(newRoot.hasChildWithValue("Child01"));
        Assertions.assertFalse(newRoot.hasChildWithValue("Child10"));
    }

    @Test
    public void testHasDescendantWithValue(){
        MutableParentNode<String> root = populateTree();

        ImmutableParentNode<String> newRoot = new ImmutableParentNode<>(root);
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
    public void testGetParent(){
        MutableParentNode<String> root = populateTree();

        ImmutableParentNode<String> newRoot = new ImmutableParentNode<>(root);
        Assertions.assertNotNull(newRoot);
        Assertions.assertNull(newRoot.getParent());

        for (ChildImmutable<String> child : newRoot.getChildren())
        {
            Assertions.assertEquals(newRoot, child.getParent());
        }
    }

    @Test
    public void testGetAllDescendants() {
        MutableParentNode<String> root = populateTree();

        ImmutableParentNode<String> immRoot = new ImmutableParentNode<>(root);

        Assertions.assertEquals(6, immRoot.getAllDescendants().size());

        List<String> objects = new ArrayList<>(6);
        for (ChildImmutable<String> child : immRoot.getAllDescendants()) {
            objects.add(child.getObject());
        }

        Assertions.assertTrue(objects.contains("Parent0"));
        Assertions.assertTrue(objects.contains("Parent1"));
        Assertions.assertTrue(objects.contains("Child0"));
        Assertions.assertTrue(objects.contains("Child00"));
        Assertions.assertTrue(objects.contains("Child00"));
        Assertions.assertTrue(objects.contains("Child10"));
    }
}
