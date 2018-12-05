package edu.hse.cs.tree;

import java.util.*;

/**
 * Implementation of mutable parent node.
 * Parent node represents an inner node and a root node as well.
 *
 * @param <T> - type of wrapper object.
 */
public class MutableParentNode<T>
        extends MutableChildNode<T>
        implements ParentMutable<T>, ChildMutable<T> {
    private Set<ChildMutable<T>> children;

    MutableParentNode(T object) {
        super(null); // stub
        // TODO implement ctor
        throw new RuntimeException("Not implemented yet.");
    }

    // ParentMutable implementation:
    @Override
    public Set<? extends ChildMutable<T>> getChildren() {
        // TODO implement getter
        throw new RuntimeException("Not implemented yet.");
    }

    @Override
    public void setChildren(Set<? extends ChildMutable<T>> children) {
        // TODO implement children set reassignment
        throw new RuntimeException("Not implemented yet.");
    }

    @Override
    public boolean addChild(ChildMutable<T> child) {
        // TODO implement child adding
        throw new RuntimeException("Not implemented yet.");
    }

    @Override
    public boolean removeChild(ChildMutable<T> child) {
        // TODO implement child removing
        throw new RuntimeException("Not implemented yet.");
    }

    @Override
    public Collection<? extends ChildMutable<T>> getAllDescendants() {
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
