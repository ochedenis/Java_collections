package com.shpp.p2p.cs.dmokhno.assignment16;


import java.lang.reflect.Array;
import java.util.*;

/**
 * Independent re-creation of Java.util LinkedList.
 *
 * @param <T> The type of data items to be added to the list.
 */
public class MyLinkedList<T> extends AbstractSequentialList<T> {
    /** Current size of the list. **/
    private int size;
    /** First link of the list. **/
    private Link<T> firstLink;
    /** Last link of the list. **/
    private Link<T> lastLink;



    public MyLinkedList() { size = 0; }

    public MyLinkedList( Collection<? extends T> coll ) { addAll( coll ); }


    /**
     * Adds new element to the list.
     *
     * @param element Element that must to be added.
     * @return True, if element was added to the list.
     */
    public boolean add(T element) {
        try {

            addLast( element );
            return true;

        } catch( Exception e ) {
            return false;
        }
    }

    /**
     * Links first element to the list, adds element at the list.
     *
     * @param element Element that must to be linked.
     */
    public void addFirst(T element) {

        if( firstLink == null ) {

            Link<T> newLink = new Link<>( null, element, null );
            firstLink = newLink;
            lastLink = newLink;

        } else {

            Link<T> nextLink = firstLink;
            Link<T> newLink = new Link<>( null, element, nextLink );
            nextLink.previous = newLink;
            firstLink = newLink;

        }

        size++;
    }

    /**
     * Links first element to the list, adds element at the list.
     *
     * @param element Element that must to be linked.
     */
    public void addLast(T element) {

        if( lastLink == null ) {

            Link<T> newLink = new Link<>( null, element, null );
            firstLink = newLink;
            lastLink = newLink;

        } else {

            Link<T> prevLink = lastLink;
            Link<T> newLink = new Link<>( prevLink, element, null );
            prevLink.next = newLink;
            lastLink = newLink;

        }

        size++;
    }

    /**
     * Adds element in list on current position.
     *
     * @param index Position on which element must tobe added.
     * @param element Element to be add.
     */
    public void add(int index, T element) {

        if( index < 0 || index > size ) {

            throw new IndexOutOfBoundsException( "Index " + index + " out-of-bounds for length "
                    + size + "." );

        } else {

            if( index == 0 ) {
                addFirst( element );
            } else if( index == size ) {
                addLast( element );
            } else {
                Link<T> nextLink = linkOnIndex( index );
                Link<T> prevLink = nextLink.previous;
                Link<T> newLink = new Link<>( prevLink, element, nextLink );
                prevLink.next = newLink;
                nextLink.previous = newLink;
                size++;
            }
        }
    }

    /**
     * Gets from the list element that contained on current position.
     *
     * @param index Position where element is located.
     * @return Element that contained on current position.
     */
    public T get( int index ) {

        if( index < 0 || index >= size ) {

            throw new IndexOutOfBoundsException( "Index " + index + " out-of-bounds for length "
                    + size + "." );

        } else { return linkOnIndex( index ).element; }
    }

    /**
     * Gets first element from the list.
     *
     * @return First element in list.
     */
    public T getFirst() {

        if ( size == 0 ) {
            throw new NoSuchElementException("No item has been added to the list.");
        } else { return firstLink.element; }
    }

    /**
     * Gets last element from the list.
     *
     * @return Last element in list.
     */
    public T getLast() {

        if ( size == 0 ) {
            throw new NoSuchElementException("No item has been added to the list.");
        } else { return lastLink.element; }
    }

    /**
     * Gets first element from the list.
     *
     * @return First element in list.
     */
    public T element() { return getFirst(); }

    /**
     * Return number of elements contains in list.
     *
     * @return Number of elements contains in list.
     */
    public int size() { return size; }

    /**
     * Remove first element from list.
     *
     * @return First element from list.
     */
    public T removeFirst() {

        if( size == 0 ) {
            throw new NoSuchElementException("No item has been added to the list.");
        } else {

            if( size == 1 ) {
                Link<T> first = firstLink;
                firstLink = null;
                lastLink = null;
                size--;

                return first.element;
            } else {
                Link<T> first = firstLink;
                firstLink = first.next;
                firstLink.previous = null;
                size--;

                return first.element;
            }
        }
    }

    /**
     * Remove last element from list.
     *
     * @return Last element from list.
     */
    public T removeLast() {

        if( size == 0 ) {
            throw new NoSuchElementException("No item has been added to the list.");
        } else {

            if( size == 1 ) {
                Link<T> first = firstLink;
                firstLink = null;
                lastLink = null;
                size--;

                return first.element;
            } else {
                Link<T> last = lastLink;
                lastLink = last.previous;
                lastLink.next = null;
                size--;

                return last.element;
            }
        }
    }

    /**
     * Remove first element from list.
     *
     * @return First element from list.
     */
    public T remove() {
        return removeFirst();
    }

    /**
     * Removes from the list element that contained on current position.
     *
     * @param index Position where element is located.
     * @return Element that was removed.
     */
    public T remove( int index ) {

        if( index < 0 || index >= size ) {

            System.out.println( "\nInvalid index argument!\n" );
            throw new IndexOutOfBoundsException( "Index " + index + " out-of-bounds for length "
                    + size + "." );

        } else {

            if( index == 0 ) {
                return removeFirst();
            } else if( index == size - 1 ) {
                return removeLast();
            } else {
                Link<T> targeted = linkOnIndex( index );
                deleteTargetLink( targeted );

                return targeted.element;
            }
        }
    }

    /**
     * Removes from the list given object, only if it contains in the list.
     *
     * @param obj Object that need to be removed.
     * @return True, if object was removed.
     */
    public boolean remove( Object obj ) {
        boolean matched = false;
        Link<T> current = firstLink;

        while( current != null ) {

            if( Objects.equals( obj, current.element ) ) {
                deleteTargetLink( current );
                matched = true;
                break;
            }

            current = current.next;
        }

        return matched;
    }

    /**
     * Remove first occurrence of given object, if object contains at the list.
     *
     * @param obj Object that need to be removed.
     * @return True, if object was removed.
     */
    public boolean removeFirstOccurrence( Object obj ) { return remove( obj ); }

    /**
     * Remove first occurrence of given object from end of the list.
     * Remove object only if it contains at the list.
     *
     * @param obj Object that need to be removed.
     * @return True, if object was removed.
     */
    public boolean removeLastOccurrence( Object obj ) {
        boolean matched = false;
        Link<T> current = lastLink;

        while( current != null ) {

            if( Objects.equals( obj, current.element ) ) {
                deleteTargetLink( current );
                matched = true;
                break;
            }

            current = current.previous;
        }

        return matched;
    }

    /**
     * Set current element on needed position.
     *
     * @param index Position on which element must to be set.
     * @param element Element to be set.
     * @return Previous element that was on current position.
     */
    public T set(int index, T element) {

        if( index < 0 || index > size ) {

            System.out.println( "\nInvalid index argument!\n" );
            throw new IndexOutOfBoundsException( "Index " + index + " out-of-bounds for length "
                    + size + "." );

        } else {
            Link<T> neededLink = linkOnIndex( index );
            T prevElmnt = neededLink.element;
            neededLink.element = element;

            return prevElmnt;
        }
    }

    /**
     * Return position of current object.
     *
     * @param obj Object that must to be check.
     * @return Position of current element, -1 if current element does not exist in the list.
     */
    public int indexOf(Object obj) {
        Link<T> current = firstLink;

        for( int index = 0; index < size; index++ ) {

            if( Objects.equals( obj, current.element ) ) {
                return index;
            }

            current = current.next;
        }

        return -1;
    }

    /**
     * Return position of first matched element from end of the list.
     *
     * @param obj Object that must to be check.
     * @return Position of first matched element from end of the list,
     * -1 if current element does not exist in the list.
     */
    public int lastIndexOf(Object obj) {
        Link<T> current = lastLink;

        for( int index = size - 1; index > -1; index-- ) {

            if( Objects.equals( obj, current.element ) ) {
                return index;
            }

            current = current.previous;
        }

        return -1;
    }

    /**
     * Return boolean that represent contained current element at list or not.
     *
     * @param obj Object that must to be check.
     * @return True, if object contains at list.
     */
    public boolean contains(Object obj) { return indexOf( obj ) > -1; }

    /**
     * Adds to list all elements from given collection, started from given position.
     *
     * @param index Position from which elements must to be added.
     * @param coll Collection of elements that must to be add.
     * @return True, if elements was added.
     */
    public boolean addAll(int index, Collection<? extends T> coll) {

        if( index < 0 || index > size ) {

            System.out.println( "\nInvalid index argument!\n" );
            throw new IndexOutOfBoundsException( "Index " + index + " out-of-bounds for length "
                    + size + "." );

        } else {

            if( coll.size() == 0 ) {
                return false;
            } else {

                if( index == size ) {

                    for( T element : coll ) { addLast( element ); }
                    return true;

                } else {
                    Link<T> firstOfRest = linkOnIndex( index );
                    Link<T> current = firstOfRest.previous;

                    if( current == null ) {
                        for( T element : coll ) { addFirst( element ); }
                        return true;
                    } else {

                        for( T element : coll ) {
                            Link<T> newLink = new Link<>( current, element, null );
                            current.next = newLink;
                            current = newLink;
                            size ++;
                        }

                        current.next = firstOfRest;

                        return true;
                    }
                }
            }
        }
    }

    /**
     * Adds to list all elements from given collection.
     *
     * @param coll Collection of elements that must to be add.
     * @return True, if elements was added.
     */
    public boolean addAll(Collection<? extends T> coll) { return addAll( size, coll ); }

    /** Remove all the elements from a list. **/
    public void clear() {
        firstLink = null;
        lastLink = null;
        size = 0;
    }

    /**
     * Custom iterator for current class.
     *
     * @return Iterator.
     */
    public Iterator<T> iterator() {
        return new MyIterator();
    }

    /**
     * List iterator for current class.
     *
     * @param index Position from which list elements need to be iterate.
     * @return Each list element started from given position.
     */
    public ListIterator<T> listIterator(int index) {

        if( index < 0 || index >= size ) {

            throw new IndexOutOfBoundsException( "Index " + index + " out-of-bounds for length "
                    + size + "." );

        } else {
            return new IteratorByIndex( index );
        }
    }

    /**
     * Custom descending iterator for current class.
     *
     * @return Descending iterator for current class.
     */
    public Iterator<T> descendingIterator() { return new DescendIterator(); }

    /**
     * Gives and remove first element from list.
     *
     * @return First element from list.
     */
    public T pollFirst() {
        if( firstLink != null ) {
            return removeFirst();
        } else {
            return null;
        }
    }

    /**
     * Gives and remove last element from list.
     *
     * @return Last element from list.
     */
    public T pollLast() {

        if( lastLink != null ) {
            return removeLast();
        } else {
            return null;
        }
    }

    /**
     * Transfers all elements from the list to object array.
     *
     * @return Object array of all list elements.
     */
    public Object[] toArray() {
        Object[] result = new Object[size];
        int index = 0;
        Iterator iterator = new MyIterator();

        while( iterator.hasNext() ) {
            result[ index++ ] = iterator.next();
        }

        return result;
    }

    /**
     * Transfers all elements from the list to custom array.
     *
     * @param arr Custom array where elements should be transferred.
     * @param <Type> Type of custom array.
     * @return Custom array of all list elements.
     */
    public <Type> Type[] toArray( Type[] arr ) {
        try {

            if( arr.length < size ) {
                arr = (Type[]) Array.newInstance(arr.getClass().getComponentType(), size);
            }

            int index = 0;
            Iterator iterator = new MyIterator();

            while( iterator.hasNext() ) {
                arr[ index ] = (Type) iterator.next();
                index++;
            }

            return arr;

        } catch( Exception e ) {
            throw new ArrayStoreException("wrong array type.");
        }
    }

    /**
     * Finds needed link and return it.
     *
     * @param index Needed position in a list.
     * @return Needed link.
     */
    private Link<T> linkOnIndex( int index ) {
        Link<T> link;

        if( index < size / 2 ) {

            link = firstLink;
            for( int i = 0; i != index; i++ ) { link = link.next; }
            return link;

        } else {

            link = lastLink;
            for( int i = size - 1; i != index; i-- ) { link = link.previous; }
            return link;
        }
    }

    /**
     * Removes targeted link from list.
     *
     * @param targeted Targeted link for removing.
     */
    private void deleteTargetLink( Link targeted ) {

        if( targeted.previous == null ) {
            removeFirst();
        } else if( targeted.next == null ) {
            removeLast();
        } else {
            (targeted.previous).next = targeted.next;
            (targeted.next).previous = targeted.previous;
            size--;
        }
    }

    /** Reverse sequential order iterator. **/
    private class DescendIterator implements Iterator<T> {
        Link<T> returnedLink;
        Link<T> currentLink = lastLink;

        @Override
        public boolean hasNext() {
            return currentLink != null;
        }

        @Override
        public T next() {
            returnedLink = currentLink;
            currentLink = currentLink.previous;

            return returnedLink.element;
        }

        @Override
        public void remove() {
            if( returnedLink == null) {
                throw new IllegalStateException();
            } else {
                deleteTargetLink( returnedLink );
            }
        }
    }

    /** Custom override of ListIterator. **/
    private class IteratorByIndex implements ListIterator<T> {
        int index;
        Link<T> returnedLink;
        Link<T> currentLink;


        IteratorByIndex( int index ) {
            this.index = index;
            currentLink = linkOnIndex(index);
        }


        @Override
        public boolean hasNext() { return index < MyLinkedList.this.size; }

        @Override
        public T next() {
            returnedLink = currentLink;
            currentLink = currentLink.next;
            index++;

            return returnedLink.element;
        }

        @Override
        public boolean hasPrevious() { return index > 0; }

        @Override
        public T previous() {
            index --;
            return returnedLink.element;
        }

        @Override
        public int nextIndex() {
            return index;
        }

        @Override
        public int previousIndex() {
            return index - 1;
        }

        @Override
        public void remove() { throw new UnsupportedOperationException("remove()"); }

        @Override
        public void set(T element) {
            currentLink.element = element;
        }

        @Override
        public void add(T element) {
            MyLinkedList.this.add( element );
        }
    }

    /** Custom iterator for current class. **/
    private class MyIterator implements Iterator<T> {
        Link<T> returnedLink;
        Link<T> currentLink = firstLink;

        @Override
        public boolean hasNext() {
            return currentLink != null;
        }

        @Override
        public T next() {
            returnedLink = currentLink;
            currentLink = currentLink.next;

            return returnedLink.element;
        }

        @Override
        public void remove() {
            if( returnedLink == null) {
                throw new IllegalStateException();
            } else {
                deleteTargetLink( returnedLink );
            }
        }
    }

    /**
     * Creates links for current list.
     * @param <T> The type of data items to be added to the list.
     */
    private class Link<T> {
        T element;
        Link<T> previous;
        Link<T> next;

        Link( Link<T> previous, T element, Link<T> next ) {
            this.element = element;
            this.previous = previous;
            this.next = next;
        }
    }
}