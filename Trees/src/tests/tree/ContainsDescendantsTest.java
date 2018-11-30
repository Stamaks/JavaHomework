package tree;

import edu.hse.cs.tree.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static tree.TreeTest.populateTree;

public class ContainsDescendantsTest {

    @Test
    public void testToStringFormMutableTree(){
        MutableRootNode<String> root = populateTree();
        String stringForm = root.toStringForm("");

        MutableRootNode<String> newRoot = TreeImporter.importMutableTree(stringForm);
        Assertions.assertNotNull(newRoot);
        Assertions.assertFalse(newRoot.containsDescendants("Root"));
        Assertions.assertTrue(newRoot.containsDescendants("Parent0"));
        Assertions.assertTrue(newRoot.containsDescendants("Child00"));
        Assertions.assertTrue(newRoot.containsDescendants("Child01"));
        Assertions.assertTrue(newRoot.containsDescendants("Parent1"));
        Assertions.assertTrue(newRoot.containsDescendants("Child10"));
        Assertions.assertTrue(newRoot.containsDescendants("Child0"));

        Assertions.assertFalse(newRoot.containsDescendants("Child000"));
    }

}
