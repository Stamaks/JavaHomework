package edu.hse.cs.tree;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class MutableParentNode<T>
        extends
        AbstractTreeNode<T>
        implements
        IChild<T>,
        IParent<T> {
    private IParent<T> parent;
    private Set<? extends IChild<T>> children;

    public MutableParentNode(T object) {
        super(object);

        this.children = new HashSet<>();
        this.parent = null;
    }

    // IChild implementation:
    @Override
    public IParent<T> getParent() {
        return parent;
    }

    public void setParent(IParent<T> newValue) {
        this.parent = newValue;
    }

    // IParent implementation:
    @Override
    public Set<? extends IChild<T>> getChildren() {
        return this.children;
    }

    public void setChildren(Set<? extends IChild<T>> newValue) {
        this.children = newValue;
    }

    @Override
    public Collection<? extends IChild<T>> getAllDescendants() {
        Set<IChild<T>> descendants = new HashSet<>(this.children);

        for (IChild child : descendants)
        {
            if (child instanceof MutableParentNode) {
                descendants.addAll(((MutableParentNode) child).getAllDescendants());
            }
            if (child instanceof MutableChildNode)
            {
                Set<IChild<T>> temp = new HashSet<>();
                temp.add(child);
                descendants.addAll(temp);
            }
        }

        return descendants;
    }

    @Override
    public boolean contains(T childValue) {
        for (IChild<T> child : this.children) {
            if (child instanceof MutableChildNode && ((MutableChildNode<T>) child).getObject() == childValue)
                return true;
            if (child instanceof MutableParentNode && ((MutableParentNode<T>) child).getObject() == childValue)
                return true;
        }

        return false;
    }

    @Override
    public boolean containsDescendants(T childValue) {
        // TODO implement containsDescendants in MutableParentNode
        throw new RuntimeException("not implemented yet!");
    }

    /**
     * Removes the first child of this node that has the specified value
     *
     * @param childValue - the value of the child to be removed
     * @return - the child removed, or null if the child with the given value was not found.
     */
    AbstractTreeNode<T> removeChildByValue(T childValue) {
        // TODO implement removeChildByValue in MutableParentNode
        throw new RuntimeException("not implemented yet!");
    }

    /**
     * Removes this node descendants having the specified value.
     *
     * @param childValue - the value of the descendant of this node that must be removed.
     * @return true if at least one descendant was removed, false - otherwise.
     */
    boolean removeDescendantsByValue(T childValue) {
        // TODO implement removeDescendantsByValue in MutableParentNode
        throw new RuntimeException("not implemented yet!");
    }

    /**
     * Adds child to the node.
     * <p>
     * N.B. Node may not implement IChild, in this case it should be recreated as a
     * MutableParentNode with the same list of children that node has. It should be done
     * to set a node a new parent.
     *
     * @param node node to be added
     */
    void addChild(AbstractTreeNode<T> node) {
        // TODO implement addChild in MutableParentNode
        throw new RuntimeException("not implemented yet!");

//        if (node instanceof MutableChildNode)
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
