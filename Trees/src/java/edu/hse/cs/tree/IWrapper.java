package edu.hse.cs.tree;

/**
 * The interface that any wrapper (an object that wraps another object) must implement.
 * @param <T> - the type of the wrapped object.
 */
public interface IWrapper<T> {
    T getObject();
}
