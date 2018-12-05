package edu.hse.cs.tree;

import java.util.Collection;
import java.util.Set;

/**
 * The interface all immutable parents must implement
 *
 * @param <T> - type of wrapped object.
 */
public interface ParentImmutable<T> extends ObjectWrapper<T> {
    /**
     * The method to get this parent's children.
     * Immutable parent has immutable children only,
     * since it cannot acquire them in any way beyond it's constructor scope.
     *
     * @return - the set of immutable children of this parent.
     */
    Set<? extends ChildImmutable<T>> getChildren();

    /**
     * Retrieves all descendants of the node, i.e. children, children's children and so on.
     *
     * @return collection of all descendants
     */
    Collection<? extends ChildImmutable<T>> getAllDescendants();

    /**
     * Checks that this node contains a child with specified value stored in it.
     *
     * @param childValue - the value of a child.
     * @return - true if at least one child with the given value is found, false otherwise.
     */
    boolean hasChildWithValue(T childValue);

    /**
     * Recursive version of contains - method.
     *
     * @param childValue - the value to check all the descendants against
     * @return - true, if at least one descendants with a given value is found, false - otherwise.
     */
    boolean hasDescendantWithValue(T childValue);
}
