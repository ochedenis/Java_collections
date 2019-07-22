package com.shpp.p2p.cs.dmokhno.assignment16;

import java.util.AbstractList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Independent re-creation of Java.util.ArrayList
 *
 * @param <T> The type of data items to be added to the list.
 */
public class MyArrayList<T> extends AbstractList<T> {
    /** Main object array, that the class works with. **/
    private T[] array;
    /** Basic capacity of object array. **/
    private final int DEFAULT_CAPACITY = 10;
    /** The current size of the filled data cells. **/
    private int currentSize = 0;



    public MyArrayList( int capacity ) {

        if( capacity > 0 ) {
            array = (T[]) new Object[ capacity ];
        } else {
            array = (T[]) new Object[ DEFAULT_CAPACITY ];
        }

    }

    public MyArrayList() { array = (T[]) new Object[ DEFAULT_CAPACITY ]; }


    /**
     * Adds new element at list.
     *
     * @param element Element that must to be added.
     * @return True, if element was added to list.
     */
    public boolean add(T element) {
        try {
            if( array.length == currentSize ) {
                increaseArray( array.length );
            }

            array[currentSize++] = element;

            return true;

        } catch( Exception e ) {
            return false;
        }
    }

    /**
     * Adds element in the list by current index.
     *
     * @param index Index on which element must tobe added.
     * @param element Element to be add.
     */
    public void add(int index, T element) {

        if( index < 0 || index > currentSize ) {

            throw new ArrayIndexOutOfBoundsException( "Index " + index + " out-of-bounds for length "
                    + currentSize + "." );
        } else {

            if( array.length == currentSize ) { increaseArray( array.length ); }

            if( index == currentSize ) {
                array[index] = element;
            } else {

                if (currentSize - index >= 0) {
                    System.arraycopy(array, index, array, index + 1, currentSize - index);
                }

                array[index] = element;
            }

            currentSize++;
        }
    }

    /**
     * Set current element on needed index.
     *
     * @param index Index on which element must to be set.
     * @param element Element to be set.
     * @return Previous element that was on current index.
     */
    public T set(int index, T element) {
        T obj;

        if( index < 0 || index >= currentSize ) {

            throw new ArrayIndexOutOfBoundsException( "Index " + index + " out-of-bounds for length "
                    + currentSize + "." );
        } else {
            obj = array[index];
            array[index] = element;
        }

        return obj;
    }

    /**
     * Gets from the list element that contained on current index.
     *
     * @param index Index that contains element.
     * @return Element that contained on current index.
     */
    public T get( int index ) {

        if( index < 0 || index >= currentSize ) {

            throw new ArrayIndexOutOfBoundsException( "Index " + index + " out-of-bounds for length "
                    + currentSize + "." );

        } else { return array[index]; }
    }

    /**
     * Return number of elements in the list.
     *
     * @return Number of elements in the list.
     */
    public int size() { return currentSize; }

    /**
     * Removes from the list element that contained on current index.
     *
     * @param index Index that contains element.
     * @return Element that was removed.
     */
    public T remove( int index ) {
        T obj;

        if( index < 0 || index >= currentSize ) {

            throw new ArrayIndexOutOfBoundsException( "Index " + index + " out-of-bounds for length "
                    + currentSize + "." );
        } else {

            obj = array[index];
            if( array.length == currentSize ) { increaseArray( array.length ); }
            System.arraycopy(array, index + 1, array, index, currentSize - index);

            array[currentSize - 1] = null;
            currentSize --;
        }

        return obj;
    }

    /**
     * Removes from the list given object, only if it contains in the list.
     *
     * @param obj Object that need to be removed.
     * @return True, if object was removed.
     */
    public boolean remove( Object obj ) {

        boolean matched = false;

        for( int i = 0; i < currentSize; i++ ) {

            if( Objects.equals( obj, array[i] ) ) {
                matched = true;
                remove( i );
                break;
            }
        }

        return matched;
    }

    /**
     * Return boolean that represent empty list or not.
     *
     * @return True, if list is empty.
     */
    public boolean isEmpty() { return currentSize == 0; }

    /** Remove all the elements from a list. **/
    public void clear() {
        array = (T[])new Object[ DEFAULT_CAPACITY ];
        currentSize = 0;
    }

    /**
     * Return boolean that represent contained current element at list or not.
     *
     * @param obj Object that must to be check.
     * @return True, if object contains at list.
     */
    public boolean contains( Object obj ) {

        for( int i = 0; i < currentSize; i++ ) {

            if( Objects.equals( obj, array[i] ) ) { return true; }
        }

        return false;
    }

    /**
     * Return index of current element at list.
     *
     * @param obj Object that must to be check.
     * @return Index of current element, -1 if current element does not exist in the list.
     */
    public int indexOf( Object obj ) {

        for( int i = 0; i < currentSize; i++ ) {
            if( Objects.equals( obj, array[i] ) ) { return i; }
        }

        return -1;
    }

    /**
     * Return index of first matched element from end of the list.
     *
     * @param obj Object that must to be check.
     * @return Index of first matched element from end of the list,
     * -1 if current element does not exist in the list.
     */
    public int lastIndexOf( Object obj ) {

        for( int i = currentSize - 1; i > -1; i -- ) {
            if( Objects.equals( obj, array[i] ) ) { return i; }
        }

        return -1;
    }

    /**
     * Iterates through all list and performs current action to each element in the list.
     *
     * @param action Action that each element must to perform.
     */
    public void forEach(Consumer<? super T> action) {

        for( int i = 0; i < currentSize; i++ ) {
            action.accept( getElement(array, i) );
        }
    }

    /**
     * Removes from list all elements that match with elements from given collection.
     *
     * @param coll Collection of elements that need to be removed.
     * @return True, if at least one element was removed.
     */
    public boolean removeAll(Collection<?> coll) {

        boolean changed = false;

        for(Object obj : coll ) {

            for( int index = 0; index < currentSize; index++ ) {

                if( Objects.equals( obj, array[index] ) ) {
                    remove( index );
                    changed = true;
                    index--;
                }
            }
        }

        return changed;
    }

    /**
     * Ensure capacity of the list.
     *
     * @param minCapacity Value on which capacity must to be ensured.
     */
    public void ensureCapacity(int minCapacity) { increaseArray( minCapacity ); }

    /** Trim list to current element size. **/
    public void trimToSize() {

        if( array.length > currentSize ) {
            T[] newArr = (T[])new Object[ currentSize ];
            System.arraycopy( array, 0, newArr, 0, currentSize );
            array = newArr;
        }
    }

    /**
     * Retain at list all elements that match with elements from given collection.
     *
     * @param coll Collection of elements that need to be retained.
     * @return True, if list was changed after method calls.
     */
    public boolean retainAll(Collection<?> coll) {
        boolean changed = false;
        boolean match = false;

        for( int index = 0; index < currentSize; index++ ) {

            for( Object obj : coll ) {

                if( Objects.equals( obj, array[index] ) ) {

                    match = true;
                    break;
                }
            }

            if( match ) {
                match = false;
            } else {
                remove( index );
                changed = true;
                index--;
            }
        }

        return changed;
    }

    /**
     *  Removes from list all elements that correspond to given filter.
     *
     * @param filter Boolean filter for removing unneeded elements.
     * @return True, if list was changed after method calls.
     */
    public boolean removeIf(Predicate<? super T> filter) {
        boolean changed = false;

        for( int index = 0; index < currentSize; index++ ) {

            if( filter.test( array[ index ] ) ) {
                remove( index );
                index--;
                changed = true;
            }
        }

        return changed;
    }

    /**
     * Gets element from needed array at needed index.
     *
     * @param arr Needed array.
     * @param index Needed index.
     * @return Element from array.
     */
    private T getElement( Object[] arr, int index ) { return (T)arr[index]; }

    /**
     * Increases main array on needed size.
     *
     * @param increaseOn Size at what array must be increased.
     */
    private void increaseArray( int increaseOn ) {
        T[] newArr = (T[])new Object[ currentSize + increaseOn ];

        System.arraycopy( array, 0, newArr, 0, currentSize );

        array = newArr;
    }

    /**
     * Creates custom iterator for current class.
     *
     * @return Iterator.
     */
    public Iterator<T> iterator() {
        return new MyIterator();
    }

    /** Custom iterator for current class. **/
    private class MyIterator implements Iterator<T> {

        int index = 0;

        @Override
        public boolean hasNext() {
            return index < currentSize;
        }

        @Override
        public T next() {
            return array[ index ++ ];
        }

        @Override
        public void remove() { throw new UnsupportedOperationException("remove()"); }
    }
}