package edu.hse.cs.tree;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * We should not have a specific logic in constructors. Specific logic should be placed into factory methods instead.
 *
 * @param <T> - the type of wrapped object.
 */
public class ImmutableRootNode<T>
        extends
        AbstractTreeNode<T>
        implements
        IParent<T> {
    private final Set<? extends IChild<T>> children;

    public ImmutableRootNode(T object, Set<? extends IChild<T>> children) {
        super(object);
        this.children = children;
    }

    public ImmutableRootNode(MutableRootNode<T> source) {
        super(source.getObject());

        Set<IChild<T>> temp = new HashSet<>();

        for (IChild<T> child : source.getChildren()) {
            if (child instanceof MutableParentNode) {
                ImmutableParentNode<T> node = new ImmutableParentNode<T>(((MutableParentNode<T>) child).getObject(),
                                                    this, source.getChildren());

                temp.add(node);
            }
            if (child instanceof MutableChildNode) {
                ImmutableChildNode<T> node = new ImmutableChildNode<T>(((MutableChildNode<T>) child).getObject(),this);

                temp.add(node);
            }
        }

        this.children = temp;
    }

    /**
     * TODO: test that children set can be read but cannot be changed.
     *
     * @return - children set view.
     */
    @Override
    public Set<? extends IChild<T>> getChildren() {
        throw new RuntimeException("not implemented yet!");
    }

    @Override
    public Collection<? extends IChild<T>> getAllDescendants() {
        // TODO implement getAllDescendants in ImmutableRootNode
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
        // TODO implement containsDescendants in ImmutableRootNode
        throw new RuntimeException("not implemented yet!");
    }

    @Override
    public String toStringForm(String indent) {
        String allNodes = "";

        if (children != null)
            for (IChild<T> el : children) {
                allNodes += "\n" + ((AbstractTreeNode) el).toStringForm(indent);
            }

        return ImmutableRootNode.class.getSimpleName() + "(" + getObject().toString() + ")" + allNodes;
    }
}
