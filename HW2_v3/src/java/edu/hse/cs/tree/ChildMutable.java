package edu.hse.cs.tree;

/**
 * Mutable child can be re-parented: it can change its' parent.
 * But, while getting new parent, the child must appear in the new parent children;
 * it means that the parent must add the given child and it must be a mutable parent to do that.
 *
 * @param <T> - type of wrapped object.
 */
public interface ChildMutable<T> extends ObjectWrapper<T> {
    /**
     * Mutable child cannot have immutable parent (by construction)
     *
     * @return the parent of this mutable child
     */
    ParentMutable<T> getParent();

    /**
     * Re-parenting this child to the given parent.
     * The {@param newParent} must add this node as a child.
     *
     * @param newParent - new parent to be assigned to this child.
     */
    void setParent(ParentMutable<T> newParent);
}
