package tree;

import edu.hse.cs.tree.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

public class TreeTest {

    @Test
    public void testGetChildren(){
        MutableRootNode<String> root = populateTree();
        Assertions.assertEquals(root.getChildren().size(), 3);
    }


    public static MutableRootNode<String> populateTree(){
        MutableRootNode<String> root = new MutableRootNode<>("Root");

        MutableParentNode<String> parent0 = new MutableParentNode<>("Parent0");
        MutableParentNode<String> parent1 = new MutableParentNode<>("Parent1");

        MutableChildNode<String> child0 = new MutableChildNode<>("Child0");
        MutableChildNode<String> child00 = new MutableChildNode<>("Child00");
        MutableChildNode<String> child01 = new MutableChildNode<>("Child01");
        MutableChildNode<String> child10 = new MutableChildNode<>("Child10");

        Set<IChild<String>> rootChildren = new HashSet<>(3);
        rootChildren.add(parent0);
        rootChildren.add(parent1);
        rootChildren.add(child0);
        root.setChildren(rootChildren);
        parent0.setParent(root);
        parent1.setParent(root);
        child0.setParent(root);

        Set<IChild<String>> parent0Children = new HashSet<>(2);
        parent0Children.add(child00);
        parent0Children.add(child01);
        parent0.setChildren(parent0Children);
        child00.setParent(parent0);
        child01.setParent(parent0);

        Set<IChild<String>> parent1Children = new HashSet<>(1);
        parent1Children.add(child10);
        parent1.setChildren(parent1Children);
        child10.setParent(parent1);

        return root;
    }

}
