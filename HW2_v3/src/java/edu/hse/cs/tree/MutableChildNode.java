package edu.hse.cs.tree;

public class MutableChildNode<T>
        extends AbstractTreeNode<T>
        implements ChildMutable<T> {

    private ParentMutable<T> parent;

    MutableChildNode(T object) {
        super(object);
    }

    @Override
    public ParentMutable<T> getParent() {
        return parent;
    }

    @Override
    public void setParent(ParentMutable<T> newParent) {
        this.parent = newParent;
    }

    @Override
    public String toStringForm(String indent) {
        return indent + MutableChildNode.class.getSimpleName() + "(" + getObject().toString() + ")";
    }
}
