package edu.hse.cs.tree;

public class MutableChildNode<T>
        extends AbstractTreeNode<T>
        implements ChildMutable<T> {

    private ParentMutable<T> parent;

    MutableChildNode(T object) {
        super(null);
        // TODO implement ctor
        throw new RuntimeException("Not implemented yet.");
    }

    @Override
    public ParentMutable<T> getParent() {
        // TODO implement getter
        throw new RuntimeException("Not implemented yet.");
    }

    @Override
    public void setParent(ParentMutable<T> newParent) {
        // TODO implement setter
        throw new RuntimeException("Not implemented yet.");
    }

    @Override
    public String toStringForm(String indent) {
        // TODO implement toStringForm
        throw new RuntimeException("Not implemented yet.");
    }
}
