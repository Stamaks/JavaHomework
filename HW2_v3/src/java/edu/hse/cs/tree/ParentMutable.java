package edu.hse.cs.tree;

import java.util.Collection;
import java.util.Set;

/**
 * The interface all mutable parents must implement.
 * Mutable parent cannot have immutable children:
 * if it could have immutable child then this child cannot be removed (or added first) since it cannot be re-parented.
 * Hence, we cannot extend ParentMutable from ParentImmutable: we cannot return immutable children from mutable parent.
 *
 * @param <T> - type of wrapped object.
 */
public interface ParentMutable<T> extends ObjectWrapper<T> {
    /**
     * Each mutable parent must accept its' children set.
     * The children must be mutable - since they have to be re-parented to this parent.
     *
     * @param children - new set of children to be assigned to their parent.
     */
    void setChildren(Set<? extends ChildMutable<T>> children);

    /**
     * We must return mutable children set from their mutable parent;
     * otherwise, we are not able to remove them when needed.
     *
     * @return the set of mutable children.
     */
    Set<? extends ChildMutable<T>> getChildren();

    /**
     * Add child to the parent's children set.
     * When child is added, the child's parent is changed to this parent.
     *
     * @param child - the child to be added.
     * @return true, if the child was added, false otherwise.
     */
    boolean addChild(ChildMutable<T> child);

    /**
     * Remove child from the parent's children set.
     * When child is removed, the child's parent is changed to null.
     *
     * @param child - the child to be removed.
     * @return true, if the child was removed, false otherwise.
     */
    boolean removeChild(ChildMutable<T> child);

    /**
     * Retrieves all descendants of the node, i.e. children, children's children and so on.
     *
     * @return collection of all descendants
     */
    Collection<? extends ChildMutable<T>> getAllDescendants();

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
