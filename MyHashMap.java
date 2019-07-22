package com.shpp.p2p.cs.dmokhno.assignment17;

import com.shpp.p2p.cs.dmokhno.assignment16.MyArrayList;

import java.util.*;

/**
 * Independent re-creation of java.util.HashMap.
 *
 * @param <K> Needed type of "key" elements.
 * @param <V> Needed type of "value" elements.
 */
public class MyHashMap<K, V> implements Map<K, V> {
    /** Basic capacity of storage array. **/
    private final int BASIC_CAPACITY = 100;
    /** Exponent value on which increases main array . **/
    private final int INCREASE_ON = 4;
    /** Main storage array, that the class works with. **/
    private Entry<K, V>[] array;
    /** Current size of the map. **/
    private int size;



    public MyHashMap() { array = new Entry[ BASIC_CAPACITY ]; }

    public MyHashMap( Map<? extends K, ? extends V> map ) {
        array = new Entry[ BASIC_CAPACITY ];
        putAll( map );
    }



    /**
     * Used to put key-value entry into a map.
     *
     * @param key Key element that must be pasted into the map.
     * @param value Value that represent current  key in map.
     * @return Previous value, if key was already exist. Null, If a new pair is passed.
     */
    public V put(K key, V value) {
        if( size / 2 == array.length ) {
            increaseArray();
        }

        int index = getKeyIndex( key );

        if( array[index] == null ) {
            array[index] = new Entry<>( key, value, null );
            size++;

            return null;
        } else {

            Entry<K, V> current = array[index];

            while( true ) {
                if( Objects.equals( current.key, key ) ) {
                    V returnValue = current.value;
                    current.value = value;

                    return returnValue;
                }

                if( current.next == null ) {
                    break;
                }

                current = current.next;
            }

            current.next = new Entry<>( key, value, null );
            size++;

            return null;
        }
    }

    /**
     * Returns the value to which key is mapped, or null if no mapping for current key.
     *
     * @param key Key whose value is need to return.
     * @return Value to which the specified key is mapped, or null if no mapping for current key.
     */
    public V get(Object key) {
        int index = getKeyIndex( key );

        if( array[index] == null ) {
            return null;
        }

        Entry<K, V> current = array[index];

        while( current != null ) {

            if( Objects.equals( current.key, key ) ) {
                return current.value;
            } else {
                current = current.next;
            }
        }

        return null;
    }

    /**
     * Removes the mapping for the current key from map, if its exist.
     *
     * @param key Key whose mapping is to be removed from the map.
     * @return Previous value associated with key, or null if there was no mapping for key.
     */
    public V remove(Object key) {
        int index = getKeyIndex( key );

        if( array[index] == null ) {
            return null;
        }

        Entry<K, V> prevEntry = null;
        Entry<K, V> current = array[index];

        while( current != null ) {

            if( Objects.equals( key, current.key ) ) {
                V returnValue = current.value;
                removeEntry( prevEntry, current, index );

                return returnValue;
            }

            prevEntry = current;
            current = current.next;
        }

        return null;
    }

    /**
     * Removes the entry for the specified key only if it is mapped to the current value.
     *
     * @param key Key with which the current value must be associated.
     * @param value Value expected to be associated with the current key.
     * @return True if the value was removed.
     */
    public boolean remove(Object key, Object value) {
        int index = getKeyIndex( key );

        if( array[index] == null ) {
            return false;
        }

        Entry<K, V> prevEntry = null;
        Entry<K, V> current = array[index];

        while( current != null ) {

            if( Objects.equals( key, current.key ) && Objects.equals( current.value , value ) ) {
                removeEntry( prevEntry, current, index );

                return true;
            }

            prevEntry = current;
            current = current.next;
        }

        return false;
    }

    /**
     * Checks if map contains a mapping for the current key.
     *
     * @param key Key what must to be checked.
     * @return True if this map contains current key.
     */
    public boolean containsKey(Object key) {
        int index = getKeyIndex( key );

        if( array[index] == null ) {
            return false;
        }

        Entry<K, V> current = array[index];

        while( current != null ) {

            if( Objects.equals( current.key, key ) ) {
                return true;
            } else {
                current = current.next;
            }
        }

        return false;
    }

    /**
     * Returns true if this map contains specified value.
     *
     * @param value Value whose presence is need to be tested.
     * @return True if this map has current value.
     */
    public boolean containsValue(Object value) {
        Iterator<Entry<K, V>> iterator = new MyIterator();

        while( iterator.hasNext() ) {
            Entry<K, V> current = iterator.next();

            if( Objects.equals( current.value, value ) ) {
                    return true;
            }
        }

        return false;
    }

    /**
     * Returns true if this map contains no key-value pair.
     *
     * @return True if this map is empty.
     */
    public boolean isEmpty() { return size == 0; }

    /**
     * Returns the number of key-value pair in this map.
     *
     * @return Number of key-value pair.
     */
    public int size() { return size; }

    /**
     * Puts all key-value pairs from given map to current map.
     *
     * @param map Map with key-value pairs for put.
     */
    public void putAll(Map<? extends K, ? extends V> map) {

        for(Map.Entry<? extends K, ? extends V> entry: map.entrySet()) {
            put( entry.getKey(), entry.getValue() );
        }
    }

    /** Removes all key-value pairs from map. **/
    public void clear() {
        array = new Entry[ BASIC_CAPACITY ];
        size = 0;
    }

    /**
     * Returns a Set of the keys contained in this map.
     *
     * @return Set of the keys contained in this map.
     */
    public Set<K> keySet() {
        MySet<K> set = new MySet<>();
        Iterator<Entry<K, V>> iterator = new MyIterator();

        while( iterator.hasNext() ) {
            Entry<K, V> current = iterator.next();
            set.add( current.key );
        }


        return set;
    }

    /**
     * Returns a Set of key-value entry contained in this map.
     *
     * @return Set of key-value entry.
     */
    public Set<Map.Entry<K, V>> entrySet() {
        MySet<Map.Entry<K, V>> set = new MySet<>();
        Iterator<Entry<K, V>> iterator = new MyIterator();

        while( iterator.hasNext() ) {
            set.add( iterator.next() );
        }

        return set;
    }

    /**
     * Returns a Collection of the values contained in this map.
     *
     * @return Values contained in this map.
     */
    public Collection<V> values() {
        MyArrayList<V> arr = new MyArrayList<>(size);
        Iterator<Entry<K, V>> iterator = new MyIterator();

        while( iterator.hasNext() ) {
            Entry<K, V> current = iterator.next();
            arr.add( current.value );
        }

        return arr;
    }

    /**
     * Replaces value of current kay at new.
     *
     * @param key Key with which value is associated.
     * @param value New value for current value.
     * @return Previous value associated with current key, or null if there was no key-value pair.
     */
    public V replace(K key, V value) {
        int index = getKeyIndex( key );

        if( array[index] == null ) {
            return null;
        }

        Entry<K, V> current = array[index];

        while( current != null ) {

            if( Objects.equals( current.key, key ) ) {
                V returnValue = current.value;
                current.value = value;

                return returnValue;
            } else {
                current = current.next;
            }
        }

        return null;
    }

    /**
     * Replaces the entry for the specified key only if it associated with current value.
     *
     * @param key Key with which values is associated.
     * @param oldValue Value expected to be associated with key.
     * @param newValue Value to be associated with the specified key.
     * @return True if the value was replaced.
     */
    public boolean replace(K key, V oldValue, V newValue) {
        int index = getKeyIndex( key );

        if( array[index] == null ) {
            return false;
        }

        Entry<K, V> current = array[index];

        while( current != null ) {

            if( Objects.equals( current.key, key ) && Objects.equals( current.value, oldValue ) ) {
                current.value = newValue;
                return true;
            } else {
                current = current.next;
            }
        }

        return false;
    }

    /**
     * Return string representing all key-value pairs ​​contained in the current map.
     *
     * @return String representing all key-value pairs.
     */
    public String toString() {
        if( size == 0 ) return "{ }";

        StringBuilder str = new StringBuilder("{ ");
        Iterator<Entry<K, V>> iterator = new MyIterator();

        while( iterator.hasNext() ) {
            Entry<K, V> current = iterator.next();
            str.append(current.key).append(" => ").append(current.value).append(" } { ");
        }

        return str.substring( 0, str.length() - 2 );
    }

    /**
     * Removes current entry from key links.
     *
     * @param prevEntry Entry that stay before target entry.
     * @param targetEntry Entry that must to be removed.
     * @param index Index where entry contains.
     */
    private void removeEntry( Entry<K, V> prevEntry, Entry<K, V> targetEntry, int index ) {
        if( prevEntry == null && targetEntry.next == null ) {
            array[index] = null;
        }

        if( prevEntry != null ) {

            if( targetEntry.next == null ) {
                prevEntry.next = null;
            } else {
                prevEntry.next = targetEntry.next;
            }
        } else {
            array[index] = targetEntry.next;
        }

        size--;
    }

    /** Increases main array. **/
    private void increaseArray() {
        Iterator<Entry<K, V>> iterator = new MyIterator();
        int index = 0;
        Entry[] oldEntries = new Entry[ size ];

        while( iterator.hasNext() ) {
            oldEntries[index] = iterator.next();
            index ++;
        }

        array = new Entry[ array.length * INCREASE_ON ];
        size = 0;

        for( Entry entry: oldEntries ) {
            put( (K)entry.getKey(), (V)entry.getValue() );
        }
    }

    /**
     * Gets kay index from "hashCode".
     *
     * @param key Key whose index needs to be found.
     * @return Index for current key.
     */
    private int getKeyIndex( Object key ) {
        return key == null ? 0 : Math.abs( key.hashCode() % array.length );
    }

    /** Custom iterator for current class. **/
    private class MyIterator implements Iterator<Entry<K, V>> {
        int index = 0;
        Entry<K, V> returnEntry;
        Entry<K, V> currentEntry;

        MyIterator() {
            findCurrent( false );
        }

        private void findCurrent( boolean nextIndex ) {
            if( nextIndex ) {
                index++;

                if( index == array.length ) {
                    currentEntry = null;
                    return;
                }
            }

            while( array[index] == null ) {
                index++;

                if( index == array.length ) {
                    currentEntry = null;
                    return;
                }
            }

            currentEntry = array[index];
        }

        @Override
        public boolean hasNext() {
            return currentEntry != null;
        }

        @Override
        public Entry<K, V> next() {
            returnEntry = currentEntry;

            if( currentEntry.next != null ) {
                currentEntry = currentEntry.next;
            } else {
                findCurrent( true );
            }

            return returnEntry;
        }

        @Override
        public void remove() { throw new UnsupportedOperationException("remove()"); }
    }

    /**
     * Contains key-value entries with links on another key-value pairs in set where current
     * entry mapped.
     *
     * @param <K> Type of key values.
     * @param <V> Type of values.
     */
    private class Entry<K, V> implements Map.Entry {
        private K key;
        private V value;
        private Entry<K ,V> next;

        Entry( K key, V value, Entry<K, V> next ) {
            this.key = key;
            this.value = value;
            this.next = next;

        }

        @Override
        public Object getKey() {
            return key;
        }

        @Override
        public Object getValue() {
            return value;
        }

        @Override
        public Object setValue(Object obj) {
            try {
                V returnValue = this.value;
                this.value = (V)obj;

                return returnValue;
            } catch( Exception e ) {
                throw new ArrayStoreException("wrong value type.");
            }
        }

    }
}