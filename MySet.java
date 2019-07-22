package com.shpp.p2p.cs.dmokhno.assignment17;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Independent re-creation of java.util.Set.
 *
 * @param <T> Type of data items.
 */
class MySet<T> implements Set<T> {
    /** Basic capacity of storage array. **/
    private final int BASIC_CAPACITY = 100;
    /** Exponent value on which increases main array . **/
    private final int INCREASE_ON = 4;
    /** Main storage array, that the class works with. **/
    private Node[] set;
    /** Current size of the set. **/
    private int size;

    MySet()  { set = new Node[ BASIC_CAPACITY ]; }

    @Override
    public boolean add(T value) {
        if( size / 2 == set.length ) {
            increaseSet();
        }

        int index = getObjIndex( value );

        if( set[index] == null ) {
            set[index] = new Node<>( value, null );
            size++;

            return true;
        } else {

            Node<T> current = set[index];

            while( true ) {
                if( Objects.equals( current.element, value ) ) {
                    return false;
                }

                if( current.next == null ) {
                    break;
                }

                current = current.next;
            }

            current.next = new Node<>( value, null );
            size++;

            return true;
        }
    }

    @Override
    public boolean remove(Object obj) {
        int index = getObjIndex( obj );

        if( set[index] == null ) {
            return false;
        }

        Node<T> prevEntry = null;
        Node<T> current = set[index];

        while( current != null ) {

            if( Objects.equals( obj, current.element ) ) {
                removeNode( prevEntry, current, index );
                return true;
            }

            prevEntry = current;
            current = current.next;
        }

        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object obj) {
        int index = getObjIndex( obj );

        if( set[index] == null ) {
            return false;
        }

        Node<T> current = set[index];

        while( current != null ) {

            if( Objects.equals( obj, current.element ) ) {
                return true;
            }

            current = current.next;
        }

        return false;
    }

    @Override
    public Iterator iterator() {
        return new MyIterator();
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[size];
        int index = 0;
        Iterator iterator = new MyIterator();

        while( iterator.hasNext() ) {
            result[ index++ ] = iterator.next();
        }

        return result;
    }

    @Override
    public boolean addAll( Collection<? extends T> coll ) {
        try {

            for( T element : coll ) { add( element ); }
            return true;

        } catch( Exception e ) {
            return false;
        }
    }

    @Override
    public void clear() {
        set = new Node[ BASIC_CAPACITY ];
        size = 0;
    }

    @Override
    public boolean removeAll(Collection coll) {
        try {

            for( Object obj : coll ) { remove( obj ); }
            return true;

        } catch( Exception e ) {
            return false;
        }
    }

    @Override
    public boolean retainAll(Collection coll) {
        throw new UnsupportedOperationException("retainAll( collection )");
    }

    @Override
    public boolean containsAll(Collection coll) {
        for( Object obj : coll ) {

            if( !contains( obj ) ) {
                return false;
            }
        }

        return true;
    }

    @Override
    public <Type> Type[] toArray( Type[] arr ) {
        try {

            if( arr.length < size ) {
                arr = (Type[]) Array.newInstance(arr.getClass().getComponentType(), size);
            }

            int index = 0;

            for( Node<T> node : set ) {

                Node<T> current = node;

                while( current != null ) {
                    arr[ index ] = (Type) current.element;
                    index++;
                    current = current.next;
                }
            }

            return arr;

        } catch( Exception e ) {
            throw new ArrayStoreException("wrong array type.");
        }
    }

    /**
     * Returned string representing all the values ​​contained in the set.
     *
     * @return String representing all values.
     */
    public String toString() {
        if( size == 0 ) return "{ }";

        StringBuilder string = new StringBuilder("{ ");

        for( Node<T> node : set ) {

            Node<T> current = node;

            while( current != null ) {
                string.append(current.element).append(", ");
                current = current.next;
            }
        }

        return string.substring( 0 , string.length() - 2 ) + " }";
    }

    /**
     * Removes current node from set.
     *
     * @param prevNode Node that stay before target entry.
     * @param targetNode Node that must to be removed.
     * @param index Index where entry contains.
     */
    private void removeNode( Node<T> prevNode, Node<T> targetNode, int index ) {
        if( prevNode == null && targetNode.next == null ) {
            set[index] = null;
        }

        if( prevNode != null ) {

            if( targetNode.next == null ) {
                prevNode.next = null;
            } else {
                prevNode.next = targetNode.next;
            }
        } else {
            set[index] = targetNode.next;
        }

        size--;
    }

    /** Increases main array. **/
    private void increaseSet() {
        T[] oldSet = (T[]) new Object[ size ];
        oldSet = toArray( oldSet );

        set = new Node[ set.length * INCREASE_ON ];
        size = 0;

        for( T element : oldSet ) {
            add( element );
        }
    }

    /**
     * Gets kay index from "hashCode".
     *
     * @param obj  Object whose index needs to be found.
     * @return Index for current object.
     */
    private int getObjIndex( Object obj ) {
        return obj == null ? 0 : Math.abs( obj .hashCode() % set.length );
    }

    /** Custom iterator for current class. **/
    private class MyIterator implements Iterator<T> {
        /**  **/
        int index = 0;
        /**  **/
        Node<T> returnNode;
        /**  **/
        Node<T> currentNode;

        MyIterator() {
            findCurrent( false );
        }

        /**  **/
        private void findCurrent( boolean nextIndex ) {
            if( nextIndex ) {
                index++;

                if( index == set.length ) {
                    currentNode = null;
                    return;
                }
            }

            while( set[index] == null ) {
                index++;

                if( index == set.length ) {
                    currentNode = null;
                    return;
                }
            }

            currentNode = set[index];
        }

        @Override
        public boolean hasNext() {
            return currentNode != null;
        }

        @Override
        public T next() {
            returnNode = currentNode;

            if( currentNode.next != null ) {
                currentNode = currentNode.next;
            } else {
                findCurrent( true );
            }

            return returnNode.element;
        }

        @Override
        public void remove() { throw new UnsupportedOperationException("remove()"); }
    }

    /**
     * Creates nodes for current set.
     *
     * @param <T> The type of data items to be added to the set.
     */
    private class Node<T> {
        T element;
        Node<T> next;

        Node( T element, Node<T> next ) {
            this.element = element;
            this.next = next;
        }
    }
}
