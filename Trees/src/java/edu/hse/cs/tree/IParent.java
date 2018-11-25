package edu.hse.cs.tree;

import java.util.Collection;
import java.util.Set;

/**
 * Each parent has its' children set, where each child is unique (no duplicates).
 */
public interface IParent<T> {
    /**
     * Any parent node provides a view on its' children set,
     * so that nobody but parent can control (change) the content of the child set.
     *
     * @return - the children set of this parent
     */
    Set<? extends IChild<T>> getChildren();

    /**
     * Retrieves all descendants of the node, i.e. children, children's children and so on.
     *
     * @return collection of all descendants
     */
    Collection<? extends IChild<T>> getAllDescendants();

    /**
     * Checks that this node contains a child with specified value stored in it.
     *
     * @param childValue - the value of a child.
     * @return - true if at least one child with the given value is found, false otherwise.
     */
    boolean contains(T childValue);

    /**
     * Recursive version of contains - method.
     *
     * @param childValue - the value to check all the descendants against
     * @return - true, if at least one descendants with a given value is found, false - otherwise.
     */
    boolean containsDescendants(T childValue);
}
