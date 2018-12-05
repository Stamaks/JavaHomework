package edu.hse.cs.tree;

public class ImmutableChildNode<T>
        extends AbstractTreeNode<T>
        implements ChildImmutable<T> {
    private final ParentImmutable<T> parent;

    ImmutableChildNode(T object, ParentImmutable<T> parent) {
        // TODO implement constructor
        super(null); // stub
        this.parent = null; // stub
        throw new RuntimeException("Not implemented yet.");
    }

    @Override
    public ParentImmutable<T> getParent() {
        // TODO implement getter
        throw new RuntimeException("Not implemented yet.");
    }

    @Override
    public String toStringForm(String indent) {
        // TODO implement toStringForm
        throw new RuntimeException("Not implemented yet.");
    }
}
