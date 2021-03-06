package edu.hse.cs.tree;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class MutableRootNode<T>
        extends
        AbstractTreeNode<T>
        implements
        IParent<T> {

    private Set<? extends IChild<T>> children;

    public MutableRootNode(T object) {
        super(object);
        this.children = new HashSet<>();
    }

    public MutableRootNode(ImmutableRootNode<T> source) {
        super(source.getObject());

        Set<IChild<T>> temp = new HashSet<>();

        for (IChild<T> child : source.getChildren()) {
            if (child instanceof ImmutableParentNode) {
                MutableParentNode<T> node = new MutableParentNode<T>(((ImmutableParentNode<T>) child).getObject());
                node.setParent(this);

                temp.add(node);
            }
            if (child instanceof ImmutableChildNode) {
                MutableChildNode<T> node = new MutableChildNode<T>(((ImmutableChildNode<T>) child).getObject());
                node.setParent(this);

                temp.add(node);
            }
        }

        this.children = temp;

    }

    @Override
    public final Set<? extends IChild<T>> getChildren() {
        return this.children;
    }

    public final void setChildren(Set<? extends IChild<T>> newValue) {
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
            if (child instanceof MutableChildNode && ((MutableChildNode<T>) child).getObject().equals(childValue))
                return true;
            if (child instanceof MutableParentNode && ((MutableParentNode<T>) child).getObject().equals(childValue))
                return true;
        }

        return false;
    }

    @Override
    public boolean containsDescendants(T childValue) {
        for (IChild child : this.children)
        {
            if (child instanceof MutableParentNode) {
                if (((MutableParentNode) child).getObject().equals(childValue)
                                    || ((MutableParentNode) child).containsDescendants(childValue))
                    return true;
            }
            if (child instanceof MutableChildNode)
            {
                if (((MutableChildNode) child).getObject().equals(childValue))
                    return true;
            }
        }

        return false;
    }

    /**
     * Removes the first child of this node that has the specified value
     *
     * @param childValue - the value of the child to be removed
     * @return - the child removed, or null if the child with the given value was not found.
     */
    AbstractTreeNode<T> removeChildByValue(T childValue) {
        AbstractTreeNode node = null;
        for (IChild child : children) {
            if (child instanceof MutableParentNode && ((MutableParentNode) child).getObject().equals(childValue)
                    || child instanceof MutableChildNode && ((MutableChildNode) child).getObject().equals(childValue)) {
                node = (AbstractTreeNode) child;
                children.remove(child);
                break;
            }
        }

        return node;
    }

    /**
     * Removes this node descendants having the specified value.
     *
     * @param childValue - the value of the descendant of this node that must be removed.
     * @return true if at least one descendant was removed, false - otherwise.
     */
    boolean removeDescendantsByValue(T childValue) {
        LinkedList<IChild> queue = new LinkedList<>();

        for (IChild child : children) {
            if (child instanceof MutableParentNode && ((MutableParentNode) child).getObject().equals(childValue)
                    || child instanceof MutableChildNode && ((MutableChildNode) child).getObject().equals(childValue)) {
                children.remove(child);
                return true;
            }

            if (child instanceof MutableParentNode)
                queue.addLast((MutableParentNode) child);
        }

        IChild currentNode;

        while (!queue.isEmpty()) {
            currentNode = queue.removeFirst();

            for (Object child : ((MutableParentNode) currentNode).getChildren()) {
                if (child instanceof MutableParentNode && ((MutableParentNode) child).getObject().equals(childValue)
                        || child instanceof MutableChildNode && ((MutableChildNode) child).getObject().equals(childValue)) {
                    ((MutableParentNode) currentNode).removeChildByValue(childValue);
                    return true;
                }

                if (child instanceof MutableParentNode)
                    queue.addLast((MutableParentNode) child);
            }

        }

        return false;
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
        // Так нельзя, кидаем исключение
        if (node instanceof ImmutableRootNode || node instanceof ImmutableParentNode
                || node instanceof ImmutableChildNode)
            throw new IllegalArgumentException("Node is Immutable!");

        if (node instanceof MutableRootNode) {
            MutableParentNode parent = new MutableParentNode(node.getObject());

            parent.setChildren(((MutableRootNode) node).getChildren());

            Set<IChild<T>> temp = new HashSet<>(this.children);
            temp.add(parent);

            this.children = temp;
        }

        Set<IChild<T>> temp = new HashSet<>(this.children);

        if (node instanceof MutableParentNode)
            temp.add((MutableParentNode) node);
        if (node instanceof MutableChildNode)
            temp.add((MutableChildNode) node);

        this.children = temp;
    }

    @Override
    public String toStringForm(String indent) {
        String allNodes = "";

        if (children != null)
            for (IChild<T> el : children) {
                allNodes += "\n" + ((AbstractTreeNode) el).toStringForm(indent + INDENT);
            }

        return MutableRootNode.class.getSimpleName() + "(" + getObject().toString() + ")" + allNodes;
    }
}
