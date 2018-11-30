package tree;

import edu.hse.cs.tree.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static tree.TreeTest.populateTree;

public class ToStringFormTest {

    @Test
    public void testToStringFormMutableTree(){
        MutableRootNode<String> root = populateTree();
        String stringForm = root.toStringForm("");
        System.out.println(stringForm);

        // Проверяем, что все ноды содержатся
        Assertions.assertTrue(stringForm.contains("MutableRootNode(Root)"));
        Assertions.assertTrue(stringForm.contains("MutableParentNode(Parent0)"));
        Assertions.assertTrue(stringForm.contains("MutableChildNode(Child00)"));
        Assertions.assertTrue(stringForm.contains("MutableChildNode(Child01)"));
        Assertions.assertTrue(stringForm.contains("MutableParentNode(Parent1)"));
        Assertions.assertTrue(stringForm.contains("MutableChildNode(Child10)"));
        Assertions.assertTrue(stringForm.contains("MutableChildNode(Child0)"));

        MutableRootNode<String> newRoot = TreeImporter.importMutableTree(stringForm);
        Assertions.assertFalse(newRoot.containsDescendants("Root"));
        Assertions.assertTrue(newRoot.containsDescendants("Parent0"));
        Assertions.assertTrue(newRoot.containsDescendants("Child00"));
        Assertions.assertTrue(newRoot.containsDescendants("Child01"));
        Assertions.assertTrue(newRoot.containsDescendants("Parent1"));
        Assertions.assertTrue(newRoot.containsDescendants("Child10"));
        Assertions.assertTrue(newRoot.containsDescendants("Child0"));
    }

}
