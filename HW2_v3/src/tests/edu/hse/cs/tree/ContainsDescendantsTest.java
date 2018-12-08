package edu.hse.cs.tree;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static edu.hse.cs.tree.TreeTest.populateTree;


public class ContainsDescendantsTest {

    @Test
    public void testToStringFormMutableTree(){
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

}
