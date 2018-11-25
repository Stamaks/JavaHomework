package edu.hse.cs.tree;

/**
 * Abstract base class for object wrappers that can be organized into a tree structures.
 *
 * @param <T> - the type of the wrapped object.
 */
public abstract class AbstractTreeNode<T>
        implements IWrapper<T> {

    public static final String INDENT = "   "; // string indent (3 space characters)

    private final T object;

    protected AbstractTreeNode(T object) {
        if (object == null) {
            throw new IllegalArgumentException("Cannot wrap null");
        }
        this.object = object;
    }

    /**
     * Unwraps the wrapped object.
     *
     * @return - the object wrapped.
     */
    @Override
    public T getObject() {
        return object;
    }

    /**
     * Represents this node in a string form.
     * For nodes with children s string representation must look like a tree with indents corresponding to a node level.
     * N.B. use INDENT value when overriding this method.
     *
     * @return - a string representation of this node.
     */
    public abstract String toStringForm(String indent);
}
