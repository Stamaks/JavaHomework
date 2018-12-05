package edu.hse.cs.tree;

public abstract class AbstractTreeNode<T> implements ObjectWrapper<T> {
    public static final String INDENT = "    "; // string indent (4 space characters)

    private final T object;

    protected AbstractTreeNode(T object) {
        if (object == null) {
            throw new IllegalArgumentException("Cannot wrap null");
        }
        this.object = object;
    }

    @Override
    public T getObject() {
        return object;
    }

    /**
     * Represents this node in a string form.
     * For nodes with children a string representation must look like a tree with indents corresponding to a node level.
     * N.B. use {@link #INDENT} value when overriding this method.
     *
     * @param indent accumulated indent
     * @return - a string representation of this node.
     */
    public abstract String toStringForm(String indent);
}
