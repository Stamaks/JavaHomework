package edu.hse.cs.tree;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ImportTreeTest {

    @Test
    public void testToStringFormMutableTree(){
        String stringForm = "MutableRootNode(Root)\n" +
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
        for (ChildMutable child : newRoot.getChildren()) {
            if (child instanceof MutableParentNode)
                objects.add((String)((MutableParentNode) child).getObject());
            else
                objects.add((String)((MutableChildNode) child).getObject());
        }

        Assertions.assertTrue(objects.contains("Parent0"));
        Assertions.assertTrue(objects.contains("Parent1"));
        Assertions.assertTrue(objects.contains("Child0"));

        // TODO: Переписать!!!!!!
        for (ChildMutable child : newRoot.getChildren()) {
            if (child instanceof MutableParentNode && ((MutableParentNode) child).getObject().equals("Parent0")) {
                objects.clear();
                for (ChildMutable childOfParent0 : newRoot.getChildren()) {
                    if (childOfParent0 instanceof MutableParentNode)
                        objects.add((String)((MutableParentNode) child).getObject());
                    else
                        objects.add((String)((MutableChildNode) child).getObject());
                }
            }

            else
                objects.add((String)((MutableChildNode) child).getObject());
        }

    }

}
