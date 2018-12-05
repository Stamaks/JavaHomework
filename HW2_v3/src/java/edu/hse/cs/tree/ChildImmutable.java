package edu.hse.cs.tree;

/**
 * The interface that all immutable children must implement.
 * And their parent must be immutable as well.
 * Rationale: in case of mutable, we will need to reparent the child removed, but it is impossible.
 *
 * @param <T> type of wrapped object.
 */
public interface ChildImmutable<T> extends ObjectWrapper<T> {
    /**
     * The method provides the parent that had been assigned to the child upon its creation.
     * Immutable child can be created in immutable parent constructor only and cannot be moved
     * from its' immutable parent. The parent of the immutable child cannot be reset and thus
     * must be immutable by itself;
     *
     * @return the parent of the child.
     */
    ParentImmutable<T> getParent();
}
