package com.shpp.p2p.cs.dmokhno.assignment16;

import java.util.Collection;
import java.util.Iterator;

/**
 * Independent re-creation of Java.util Queue.
 *
 * @param <Type> The type of data items to be added to the queue.
 */
public class MyQueue<Type> extends MyLinkedList<Type> {


    MyQueue() {  }

    MyQueue( Collection<? extends Type> coll ) { addAll( coll ); }

    /**
     * Adds new element at queue.
     *
     * @param element Element to add.
     * @return True if element was added.
     */
    boolean offer(Type element) { return add(element); }

    /**
     * Remove first element from queue.
     *
     * @return First element from queue.
     */
    public Type remove() { return removeFirst(); }

    /**
     * Gives and remove first element from queue.
     *
     * @return First element from queue.
     */
    Type poll() { return pollFirst(); }

    /**
     * Gets first element from the queue.
     *
     * @return First element from the queue.
     */
    public Type element() { return getFirst(); }

    /**
     * Gets first element from the queue.
     *
     * @return First element from the queue.
     */
    Type peek() { return getFirst(); }

    /**
     * Used to return a list-iterator containing the same elements as that of the list.
     *
     * @return Unsupported operation exception.
     */
    public Iterator<Type> iterator() { throw new UnsupportedOperationException("iterator()"); }

}
