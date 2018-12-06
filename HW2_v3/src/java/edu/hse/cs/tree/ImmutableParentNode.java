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
        super(source.getObject(), parent);

        Set<ChildImmutable<T>> temp = new HashSet<>();

        for (ChildMutable<T> child : source.getChildren()) {
            if (child instanceof MutableParentNode) {
                ImmutableParentNode<T> node = new ImmutableParentNode<T>((MutableParentNode<T>) child, this);

                temp.add(node);
            }

            if (child instanceof MutableChildNode) {
                ImmutableChildNode<T> node = new ImmutableChildNode<T>(child.getObject(), this);

                temp.add(node);
            }
        }

        this.children = temp;
    }

    @Override
    public Set<? extends ChildImmutable<T>> getChildren() {
        return children;
    }

    @Override
    public Collection<? extends ChildImmutable<T>> getAllDescendants() {
        Set<ChildImmutable<T>> descendants = new HashSet<>(this.children);

        for (ChildImmutable<T> child : descendants)
        {
            if (child instanceof ImmutableParentNode) {
                descendants.addAll(((ImmutableParentNode<T>) child).getAllDescendants());
            }

            descendants.add(child);
        }

        return descendants;
    }

    @Override
    public boolean hasChildWithValue(T childValue) {
        for (ChildImmutable<T> child : this.children) {
            if (child instanceof ImmutableChildNode && child.getObject().equals(childValue))
                return true;
            if (child instanceof ImmutableParentNode && child.getObject().equals(childValue))
                return true;
        }

        return false;
    }

    @Override
    public boolean hasDescendantWithValue(T childValue) {
        for (ChildImmutable<T> child : this.children)
        {
            if (child instanceof ImmutableParentNode) {
                if (((ImmutableParentNode<T>) child).hasChildWithValue(childValue))
                    return true;
            }
            if (child instanceof ImmutableChildNode)
            {
                if (((ImmutableChildNode) child).getObject().equals(childValue))
                    return true;
            }
        }

        return false;
    }

    @Override
    public String toStringForm(String indent) {
        String allNodes = "";

        if (children != null)
            for (ChildImmutable<T> child : children) {
                allNodes += "\n" + ((AbstractTreeNode) child).toStringForm(indent + INDENT);
            }

        return indent + ImmutableParentNode.class.getSimpleName() + "(" + getObject().toString() + ")" + allNodes;
    }
}
