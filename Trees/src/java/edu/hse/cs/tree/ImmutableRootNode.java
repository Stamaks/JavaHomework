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
        return this.children;
    }

    @Override
    public Collection<? extends IChild<T>> getAllDescendants() {
        Set<IChild<T>> descendants = new HashSet<>(this.children);

        for (IChild child : descendants)
        {
            if (child instanceof ImmutableParentNode) {
                descendants.addAll(((ImmutableParentNode) child).getAllDescendants());
            }
            if (child instanceof ImmutableChildNode)
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
            if (child instanceof ImmutableChildNode && ((ImmutableChildNode<T>) child).getObject().equals(childValue))
                return true;
            if (child instanceof ImmutableParentNode && ((ImmutableParentNode<T>) child).getObject().equals(childValue))
                return true;
        }

        return false;
    }

    @Override
    public boolean containsDescendants(T childValue) {

        for (IChild child : this.children)
        {
            if (child instanceof ImmutableParentNode) {
                if (((ImmutableParentNode) child).contains(childValue))
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
            for (IChild<T> el : children) {
                allNodes += "\n" + ((AbstractTreeNode) el).toStringForm(indent + INDENT);
            }

        return ImmutableRootNode.class.getSimpleName() + "(" + getObject().toString() + ")" + allNodes;
    }
}
