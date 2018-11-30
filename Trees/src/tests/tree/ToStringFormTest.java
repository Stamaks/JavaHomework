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

        // Проверяем, что все ноды содержатся. Не можем проверить иначе, потому что HashSet не запоминает порядок
        Assertions.assertTrue(stringForm.contains("MutableRootNode(Root)"));
        Assertions.assertTrue(stringForm.contains("\n    MutableParentNode(Parent0)"));
        Assertions.assertTrue(stringForm.contains("\n        MutableChildNode(Child00)"));
        Assertions.assertTrue(stringForm.contains("\n        MutableChildNode(Child01)"));
        Assertions.assertTrue(stringForm.contains("\n    MutableParentNode(Parent1)"));
        Assertions.assertTrue(stringForm.contains("\n        MutableChildNode(Child10)"));
        Assertions.assertTrue(stringForm.contains("\n    MutableChildNode(Child0)"));

        Assertions.assertFalse(stringForm.contains("\n     MutableChildNode(Child0)"));
        Assertions.assertFalse(stringForm.contains("MutableChildNode(Child)"));


    }

}
