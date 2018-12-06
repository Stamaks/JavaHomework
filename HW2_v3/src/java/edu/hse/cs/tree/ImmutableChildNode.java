package edu.hse.cs.tree;

public class ImmutableChildNode<T>
        extends AbstractTreeNode<T>
        implements ChildImmutable<T> {
    private final ParentImmutable<T> parent;

    ImmutableChildNode(T object, ParentImmutable<T> parent) {
        super(object);
        this.parent = parent;
    }

    @Override
    public ParentImmutable<T> getParent() { return parent; }

    @Override
    public String toStringForm(String indent) {
        return indent + ImmutableChildNode.class.getSimpleName() + "(" + getObject().toString() + ")";
    }
}
