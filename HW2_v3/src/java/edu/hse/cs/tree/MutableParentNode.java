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
        super(object);

        this.children = new HashSet<>();
    }

    // ParentMutable implementation:
    @Override
    public Set<? extends ChildMutable<T>> getChildren() {
        return this.children;
    }

    @Override
    public void setChildren(Set<? extends ChildMutable<T>> children) {
        this.children = new HashSet<>(children) ;
    }

    @Override
    public boolean addChild(ChildMutable<T> child) {
        this.children.add(child);

        return true;
    }

    @Override
    public boolean removeChild(ChildMutable<T> childToRemove) {

        for (ChildMutable<T> child : children) {
            if (child.equals(childToRemove)) {
                children.remove(child);
                return true;
            }
        }

        return false;
    }

    @Override
    public Collection<? extends ChildMutable<T>> getAllDescendants() {
        Set<ChildMutable<T>> descendants = new HashSet<>();

        for (ChildMutable child : children)
        {
            if (child instanceof MutableParentNode) {
                descendants.addAll(((MutableParentNode<T>) child).getAllDescendants());
            }

            children.add(child);
        }

        return descendants;
    }

    @Override
    public boolean hasChildWithValue(T childValue) {

        for (ChildMutable<T> child : this.children) {
            if (child.getObject().equals(childValue))
                return true;
        }

        return false;
    }

    @Override
    public boolean hasDescendantWithValue(T childValue) {
        for (ChildMutable<T> child : this.children)
        {
            if (child.getObject().equals(childValue))
                return true;

            if (child instanceof MutableParentNode && ((MutableParentNode<T>) child).hasDescendantWithValue(childValue))
                return true;
        }

        return false;
    }

    @Override
    public String toStringForm(String indent) {
        String allNodes = "";

        if (children != null)
            for (ChildMutable<T> el : children) {
                allNodes += "\n" + ((AbstractTreeNode) el).toStringForm(indent + INDENT);
            }

        return indent + MutableParentNode.class.getSimpleName() + "(" + getObject().toString() + ")" + allNodes;
    }
}
