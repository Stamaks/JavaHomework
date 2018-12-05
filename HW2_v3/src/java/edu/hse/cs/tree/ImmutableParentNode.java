package edu.hse.cs.tree;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ImmutableParentNode<T>
        extends ImmutableChildNode<T>
        implements ParentImmutable<T>, ChildImmutable<T> {
    private Set<ChildImmutable<T>> children;

    /**
     * Creates an immutable tree that is a copy of mutable tree represented by {@link MutableParentNode}.
     *
     * @param source a root of mutable tree (does not have a parent).
     * @throws IllegalArgumentException if source is not a root node ({@param source} has a parent)
     */
    public ImmutableParentNode(MutableParentNode<T> source) {
        super(null, null); // stub
        // TODO implement public constructor
        throw new RuntimeException("Not implemented yet.");
    }

    /**
     * Creates immutable node with specified parent based on {@link MutableParentNode}.
     *
     * @param source mutable node which is base for the new immutable one
     * @param parent parent of new immutable node
     */
    private ImmutableParentNode(MutableParentNode<T> source, ParentImmutable<T> parent) {
        super(null, null); // stub
        // TODO implement private constructor
        throw new RuntimeException("Not implemented yet.");
    }

    @Override
    public Set<? extends ChildImmutable<T>> getChildren() {
        // TODO implement getter
        throw new RuntimeException("Not implemented yet.");
    }

    @Override
    public Collection<? extends ChildImmutable<T>> getAllDescendants() {
        // TODO implement tree traversing and collecting
        throw new RuntimeException("Not implemented yet.");
    }

    @Override
    public boolean hasChildWithValue(T childValue) {
        // TODO implement collection search
        throw new RuntimeException("Not implemented yet.");
    }

    @Override
    public boolean hasDescendantWithValue(T childValue) {
        // TODO implement recursive search
        throw new RuntimeException("Not implemented yet.");
    }

    @Override
    public String toStringForm(String indent) {
        // TODO implement toStringForm
        throw new RuntimeException("Not implemented yet.");
    }
}
