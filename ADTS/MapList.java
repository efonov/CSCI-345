package impl;

import java.util.Iterator;
import java.util.NoSuchElementException;

import adt.Map;
import adt.List;

/**
 * MapList
 * 
 * An implementation of List that uses a Map as its
 * underlying implementation.
 * 
 * CSCI 345, Wheaton College
 * Spring 2016
 * @param <E> The base-type of the list
 */
public class MapList<E> implements List<E> {

    /**
     * The internal representation (can be any 
     * implementation of map).
     */
    private Map<Integer, E> internal;
    int size = 0;
    
    
    /**
     * Constructor that is given the internal representation.
     * From a software development perspective, that's a bad idea
     * (breaks encapsulation), but for the purpose of this project 
     * it allows us to parameterize this class by what implementation
     * of Map we use. (Maybe in a future version we'll use 
     * reflection instead).
     */
    public MapList() {
        this.internal = new ArrayMap<Integer,E>();
    }
    
    /**
     * Return an iterator over this collection (remove() is
     * unsupported, nor is concurrent modification checked).
     */
    public Iterator<E> iterator() {
    	return new Iterator<E>() {
            int i = 0;

            public boolean hasNext() {
             return i<size;
            }

            public E next() {
                if (this.hasNext()) {
                    return internal.get(i++);
                } 
                else throw new NoSuchElementException();
            }

        };    
    }

    /**
     * Append the specified element to the end of this list.
     * This increases the size by one.
     * @param element The element to be appended
     */
    public void add(E element) {
    	internal.put(size, element);
    	size++;
    }

    /**
     * Replace the element at the specified position in this list
     * with the specified element. If the index is invalid, an 
     * IndexOutOfBoundsException is thrown.
     * @param index The index of the element to return
     * @param element The element at the specified position
     */
    public void set(int index, E element) {
    	if(!internal.containsKey(index)) throw new IndexOutOfBoundsException();
    	else internal.put(index, element);
    }

    /**
     * Retrieve the element at the specified position in this list.
     * If the index is invalid, an IndexOutOfBoundsException is thrown.
     * @param index The index of the element to return
     * @return The element at the specified position
     */
    public E get(int index) {
    	if(!internal.containsKey(index)) throw new IndexOutOfBoundsException();
    	else return internal.get(index);
    }


    /**
     * Insert a new item at the specified position, shifting the
     * item already at the position and everything after it over
     * one position. If the index is equal to the length of the list,
     * then this is equivalent to the add method. If the index is 
     * negative or is greater than the length, an IndexOutOfBoundsException 
     * is thrown.
     * @param index The index into which to insert the element
     * @param element The element which to insert
     */
    public void insert(int index, E element) {
    	Map<Integer, E> temp = new ArrayMap<Integer,E>();
    	for(int i = 0; i < size; i++) {
			temp.put(i, internal.get(i));
		}
    	
    	if(index == size) {
 		    //System.out.println("adding: " + element + " at " + index);
 		    //System.out.println(internal);
    		add(element);
 		    //System.out.println(internal);
 		    //System.out.println("added: " + element + " at " + index + "\n");

    	}

    	else if(index < 0 || index > size) throw new IndexOutOfBoundsException();
    	
    	else {
// 		    System.out.println("inserting: " + element + " at " + index);
// 		    System.out.println(internal);
    		internal.put(index, element);
    		for(int i = index; i < size; i++) {
    			internal.put(i+1, temp.get(i));
    		}
    		size++;
// 		    System.out.println(internal);
// 		    System.out.println("inserted: " + element + " at " + index + "\n");

    	}
    }

    /**
     * Remove (and return) the element at the specified position.
     * This reduces the size of the list by one and, if necessary,
     * shifts other elements over. If the index is invalid, an 
     * IndexOutOfBoundsException is thrown.
     * @param index The index of the element to remove
     * @return The item removed
     */
   public E remove(int index) {
	   E toRemove = internal.get(index);
	   
	   if(index < 0 || index >= size()) throw new IndexOutOfBoundsException();	
	   
	   else {
//		   System.out.println("removing: " + toRemove + " at " + index);
//		   System.out.println(internal);
		   for(int i = index; i<size; i++) {
			   set(i, internal.get(i+1));
		   }
		   internal.remove(size);
		   size--;
		   internal.remove(size);

//		   System.out.println(internal);
//		   System.out.println("removed: " + toRemove + " at " + index + "\n");
	   }
	   return toRemove;

	}
/**
    * Return the number of elements in this list.
    * @return The number of elements in this list.
    */
    public int size() {
    	return size;
    }
    
    @Override
    public String toString() {
    	String toReturn = "[";
        boolean prefix = false;
    	for (E item : this) {
            if (prefix)
                toReturn += ", ";
    		toReturn += item;
            prefix = true;
    	}
    	return toReturn +"]";
    }
    
    	


}
