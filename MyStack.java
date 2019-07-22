package com.shpp.p2p.cs.dmokhno.assignment16;

import java.util.Collection;
import java.util.EmptyStackException;
import java.util.Objects;

/**
 * Independent re-creation of Java.util Stack.
 *
 * @param <Type> The type of data items to be added to the stack.
 */
public class MyStack<Type> {
    /** First link of the list. **/
    private Node<Type> last;


    public MyStack() { }

    public MyStack( Collection<? extends Type> coll ) {
        for( Type element : coll ) {
            push( element );
        }
    }


    /**
     * Pushes element to the stack.
     *
     * @param element Element what added on top of the stack.
     * @return Added element.
     */
    public void push(Type element) {

        if( last == null ) {
            last = new Node<>( element, null);
        } else {
            last = new Node<>( element, last);
        }
    }

    /**
     * Gives and remove last element from stack.
     *
     * @return Last element from stack.
     */
    public Type pop() {
        if ( last == null ) {
            return null;
        }

        Type returnValue = last.element;
        last = last.next;

        return returnValue;
    }

    /**
     * Gets last element from the stack.
     *
     * @return Last element in stack.
     */
    public Type peek() {
        return last.element;
    }

    /**
     * Return boolean that represent empty stack or not.
     *
     * @return True, if stack is empty.
     */
    public boolean empty() {
        return last == null;
    }

    /**
     * Return position of current object.
     *
     * @param obj Object that must to be check.
     * @return True if current object contains at the stack.
     */
    public boolean search(Object obj) {
        Node<Type> current = last;

        while( current != null ) {
            if( Objects.equals( obj, current.element ) ) {
                return true;
            }

            current = current.next;
        }

        return false;
    }

    /** Removes all the elements from the stack. **/
    public void clear() {
        last = null;
    }

    /**
     * Creates nodes for current stack.
     *
     * @param <Type> The type of data items to be added to the node.
     */
    private class Node<Type> {
        Type element;
        Node<Type> next;

        Node( Type element, Node<Type> next ) {
            this.element = element;
            this.next = next;
        }
    }
}