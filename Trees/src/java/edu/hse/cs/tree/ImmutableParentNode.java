package edu.hse.cs.tree;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ImmutableParentNode<T>
        extends
        AbstractTreeNode<T>
        implements
        IChild<T>,
        IParent<T> {
    private final IParent<T> parent;
    private final Set<? extends IChild<T>> children;

    public ImmutableParentNode(T object, IParent<T> parent, Set<? extends IChild<T>> children) {
        super(object);
        this.parent = parent;

        Set<IChild<T>> temp = new HashSet<>();

        for (IChild<T> child : children) {
            if (child instanceof ImmutableParentNode) {
                ImmutableParentNode<T> node = new ImmutableParentNode<T>(((ImmutableParentNode<T>) child).getObject(),
                        this, ((ImmutableParentNode<T>) child).getChildren());

                temp.add(node);
            }

            if (child instanceof ImmutableChildNode) {
                ImmutableChildNode<T> node = new ImmutableChildNode<T>(((ImmutableChildNode<T>) child).getObject(), this);

                temp.add(node);
            }

            if (child instanceof MutableParentNode) {
                ImmutableParentNode<T> node = new ImmutableParentNode<T>(((MutableParentNode<T>) child).getObject(),
                        this, ((MutableParentNode<T>) child).getChildren());

                temp.add(node);
            }

            if (child instanceof MutableChildNode) {
                ImmutableChildNode<T> node = new ImmutableChildNode<T>(((MutableChildNode<T>) child).getObject(), this);

                temp.add(node);
            }
        }

        this.children = temp;
    }

    @Override
    public IParent<T> getParent() {
        return parent;
    }

    @Override
    public Set<? extends IChild<T>> getChildren() {
        return this.children;
    }

    @Override
    public Collection<? extends IChild<T>> getAllDescendants() {
        // TODO implement getAllDescendants in ImmutableParentNode
        throw new RuntimeException("not implemented yet!");
    }

    @Override
    public boolean contains(T childValue) {
        for (IChild<T> child : this.children) {
            if (child instanceof ImmutableChildNode && ((ImmutableChildNode<T>) child).getObject() == childValue)
                return true;
            if (child instanceof ImmutableParentNode && ((ImmutableParentNode<T>) child).getObject() == childValue)
                return true;
        }

        return false;
    }

    @Override
    public boolean containsDescendants(T childValue) {
        // TODO implement containsDescendants in ImmutableParentNode
        throw new RuntimeException("not implemented yet!");
    }

    @Override
    public String toStringForm(String indent) {
        String allNodes = "";

        if (children != null)
            for (IChild<T> el : children) {
                allNodes += "\n" + ((AbstractTreeNode) el).toStringForm(indent + INDENT);
            }

        return indent + ImmutableRootNode.class.getSimpleName() + "(" + getObject().toString() + ")" + allNodes;
    }
}