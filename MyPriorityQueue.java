package com.shpp.p2p.cs.dmokhno.assignment17;

import java.util.*;

/**
 * Independent re-creation of java.util.PriorityQueue.
 *
 * @param <T> Type of data items.
 */
public class MyPriorityQueue<T> {
    /** Main object array, that the class works with. **/
    private T[] array;
    /** Basic capacity of object array. **/
    private final int CAPACITY = 10;
    /** The current size of the filled data cells. **/
    private int size = 0;
    /** Custom comparator to current queue. **/
    private Comparator<? super T> compare;



    public MyPriorityQueue() {
        array = (T[]) new Object[CAPACITY];
        compare = compareByDef;
    }

    public MyPriorityQueue( int capacity ) {
        compare = compareByDef;

        if( capacity > 0 ) {
            array = (T[]) new Object[ capacity ];
        } else {
            array = (T[]) new Object[ CAPACITY ];
        }
    }

    public MyPriorityQueue(Collection<? extends T> coll) {
        array = (T[]) new Object[CAPACITY];
        compare = compareByDef;

        for( T value : coll ) {
            add( value );
        }
    }

    public MyPriorityQueue( Comparator<? super T> comparator ) {
        array = (T[]) new Object[CAPACITY];
        compare = comparator;
    }



    /** Default comparator to current queue. **/
    Comparator<Object> compareByDef = (first, second) -> {
        if( first.equals( second ) ) {
            return 0;
        }

        String firstS = first.toString();
        String secondS = second.toString();

        try {
            double firstD = Double.valueOf( firstS );
            double secondD = Double.valueOf( secondS );

            if( firstD > secondD ) {
                return 1;
            } else if( firstD < secondD ) {
                return -1;
            } else {
                return 0;
            }
        } catch( Exception e ) {
            int range;

            if( firstS.length() > secondS.length() ) {
                range = secondS.length();
            } else {
                range = firstS.length();
            }

            for( int i = 0; i < range; i++ ) {
                char firstC = firstS.charAt(i);
                char secondC = secondS.charAt(i);

                if( firstC > secondC ) {
                    return 1;
                }
                if( firstC < secondC ) {
                    return -1;
                }
            }

            if( firstS.length() > secondS.length() ) {
                return 1;
            } else if( firstS.length() < secondS.length() ) {
                return -1;
            } else {
                return 0;
            }
        }
    };

    /**
     * Adds the specified elements into the queue.
     *
     * @param value Element to add.
     * @return True if value was added.
     */
    public boolean add(T value) {
        try {
            if( array.length == size ) {
                increaseArray();
            }

            array[ size++ ] = value;

            if( size > 1 ) {
                checkFloorPriority( size );
            }

            return true;
        } catch( Exception e ) {
            return false;
        }
    }

    /**
     * Retrieves and removes top value of this queue, returns null if queue is empty.
     *
     * @return Top value of this queue, null if queue is empty.
     */
    public T poll() {
        if( size == 0 ) {
            return null;
        }

        T returnValue = array[0];
        array[0] = array[ size - 1 ];
        array[ size - 1 ] = null;
        size--;

        if( size > 1 ) {
            checkCeilPriority( 1 );
        }

        return returnValue;
    }

    /**
     * Retrieves, but does not remove, top value of this queue, returns null if queue is empty.
     *
     * @return Top value of this queue, null if queue is empty.
     */
    public T peek() {
        if( size == 0 ) {
            return null;
        } else {
            return array[0];
        }
    }

    /**
     * Returns an array containing all of the elements in this queue. Elements are not ordered.
     *
     * @return Array containing all of the elements in this queue.
     */
    public Object[] toArray() {

        if( array.length > size ) {
            T[] newArr = (T[])new Object[ size ];
            System.arraycopy( array, 0, newArr, 0, size );
            array = newArr;
        }

        return Arrays.copyOf( array , size );
    }

    /**
     * Removes needed value from this queue, if it is present.
     *
     * @param obj Element that must to be removed from this queue.
     * @return True if element was removed.
     */
    public boolean remove(Object obj) {
        boolean matched = false;

        for( int index = 0; index < size; index++ ) {

            if( obj.equals( array[index] ) ) {
                matched = true;

                if( index == size - 1 ) {
                    array[index] = null;
                    size--;
                    break;
                }

                if( size == 2 ) {
                    array[0] = array[1];
                    array[1] = null;
                    size--;
                    break;
                }

                array[index] = array[ size - 1 ];
                array[ size - 1 ] = null;
                size--;

                checkCeilPriority( index + 1 );

                break;
            }
        }

        return matched;
    }

    /** Removes all of the elements from queue. **/
    public void clear() {
        array = (T[]) new Object[CAPACITY];
        size = 0;
    }

    /**
     * Returns true if this queue is empty.
     *
     * @return True if this queue is empty.
     */
    public boolean isEmpty() { return size == 0; }

    /** Returns the number of elements in this queue. **/
    public int size() { return size; }

    /**
     * Returns an array containing all of the elements in this queue.
     * Elements are not ordered.
     *
     * @param arr Array into which the elements of the queue are to be stored.
     * @param <Type> Type of stored array.
     * @return Array containing all of the elements in this queue.
     */
    public <Type> Type[] toArray(Type[] arr) {
        try {

            if( array.length > size ) {
                T[] newArr = (T[])new Object[ size ];
                System.arraycopy( array, 0, newArr, 0, size );
                array = newArr;
            }

            if( arr.length >= size ) {
                System.arraycopy( array, 0, arr, 0, size);

                return arr;
            } else {
                return (Type[]) Arrays.copyOf( array, size, arr.getClass());
            }

        } catch( Exception e ) {
            throw new ArrayStoreException("wrong array type.");
        }
    }

    /**
     * Returns true if this queue contains current element.
     *
     * @param obj Element to be checked for containment in this queue.
     * @return True if this queue contains current element.
     */
    public boolean contains(Object obj) {
        for( T value : array ) {

            if( obj.equals( value ) ) {
                return true;
            }
        }

        return false;
    }

    /**
     * Returns an iterator over the elements in this queue. Iterator return element without any order.
     *
     * @return Iterator over the elements in this queue.
     */
    public Iterator<T> iterator() {
        return new MyIterator();
    }

    /**
     * Return string representing all elements in this queue.
     *
     * @return String representing all elements in this queue.
     */
    public String toString() {
        StringBuilder str = new StringBuilder("[");

        for( int i = 0; i < size; i++ ) {
            str.append(array[i]).append(", ");
        }

        return size == 0 ? "[ ]" : str.substring(0, str.length() - 2) + "]";
    }

    /**
     * Returns custom comparator used to order the elements in this queue.
     * Null if this queue is sorted by default order.
     *
     * @return Comparator used to order this queue, null if this queue is sorted by default order.
     */
    public Comparator<? super T> comparator() {
        return Objects.equals( compare, compareByDef ) ? null : compare;
    }

    /**
     * Check for default order of elements from bottom of heap to top.
     *
     * @param heapPosition Heap position of current checked element.
     */
    private void checkFloorPriority(int heapPosition ) {

        int indexOfChild = heapPosition - 1;
        int indexOfParent = (heapPosition / 2) - 1;

        if( compare.compare( array[indexOfChild], array[indexOfParent] ) < 0 ) {
            changePositions(indexOfChild, indexOfParent);

            if (indexOfParent != 0) {
                checkFloorPriority(indexOfParent + 1 );
            }
        }
    }

    /**
     * Check for default order of elements from top of heap to bottom.
     *
     * @param heapPosition Heap position of current checked element.
     */
    private void checkCeilPriority( int heapPosition ) {
        int indexOfParent = heapPosition - 1;
        int indexOfChild;

        if( heapPosition * 2 < size) {

            if( compare.compare( array[ (heapPosition * 2) - 1 ], array[ heapPosition * 2 ] ) <= 0 ) {
                indexOfChild = (heapPosition * 2) - 1;
            } else {
                indexOfChild = heapPosition * 2;
            }

            if( compare.compare( array[ indexOfChild ], array[ indexOfParent ] ) < 0 ) {
                changePositions( indexOfChild, indexOfParent );

                if( (indexOfChild + 1) * 2 < size ) {
                    checkCeilPriority( indexOfChild + 1 );
                }
            }
        } else {

            if( (heapPosition * 2) - 1 < size ) {
                indexOfChild = (heapPosition * 2) - 1;

                if( compare.compare( array[ indexOfChild ], array[ indexOfParent ] ) < 0 ) {
                    changePositions( indexOfChild, indexOfParent );
                }
            }
        }
    }

    /**
     * Changes positions in heap of given elements.
     *
     * @param indexOfNew Index of element whose position must to be changed.
     * @param indexFromCheck Index of element on whose position new element will be stored.
     */
    private void changePositions( int indexOfNew, int indexFromCheck ) {
        T newValue = array[ indexOfNew ];
        T oldValue = array[ indexFromCheck ];
        array[ indexFromCheck ] = newValue;
        array[ indexOfNew ] = oldValue;
    }

    /** Increases main array. **/
    private void increaseArray() {
        T[] newArr = (T[])new Object[ array.length + array.length ];

        System.arraycopy( array, 0, newArr, 0, size );

        array = newArr;
    }

    /** Custom iterator for current class. **/
    private class MyIterator implements Iterator<T> {
        int index = 0;

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public T next() {
            return array[ index ++ ];
        }

        @Override
        public void remove() {
            if( index - 1 > -1 ) {
                MyPriorityQueue.this.remove( array[ index - 1 ] );
                index --;
            } else {
                throw new IndexOutOfBoundsException( "Index " + (index - 1) + " out-of-bounds for length "
                        + size + "." );
            }
        }
    }
}
