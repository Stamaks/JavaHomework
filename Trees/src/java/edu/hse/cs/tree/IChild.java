package edu.hse.cs.tree;

/**
 * Interface IChild must be implemented by any tree node that has parent: any child has its' parent.
 */
public interface IChild<T> {
    IParent<T> getParent();
}
