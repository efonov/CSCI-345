package impl;

import java.util.Iterator;

import adt.Bag;
import adt.List;

/**
 * ListBag
 * 
 * An implementation of Bag using a List as the underlying
 * implementation.
 * 
 * Recall that our Bag interface differs from what Sedgewick 
 * calls a "bag" (but he's wrong). 
 * 
 * CSCI 345, Wheaton College
 * Spring 2016
 * @param <E> The base-type of the bag
 */
public class ListBag<E> implements Bag<E> {

    /**
     * The internal representation (can be any implementation of
     * List)
     */
    private List<E> internal;
    int size = 0;
    
    public ListBag() {
            internal = new MapList<E>();
    }
    /**
     * Return an iterator over this collection (remove() is
     * unsupported, nor is concurrent modification checked).
     */
   public Iterator<E> iterator() {
   		return internal.iterator();
    }

    /**
     * Add an item to the bag, increasing its count if it's
     * already there.
     * @param item The item to add
     */
    public void add(E item) {
    	internal.add(item);
    	size++;
    }

    /**
     * How many times does this bag contain this item?
     * @param item The item to check
     * @return The number of occurences of this item in the bag
     */
    public int count(E item) {
    	int count = 0;
    	for(int i = 0; i< size; i++) {
    		if(internal.get(i).equals(item)) count++;
    	}
    	return count;
    }

    /**
     * Remove (all occurrences of) an item from the bag, if it's there
     * (ignore otherwise).
     * @param item The item to remove
     */
    public void remove(E item) {
		//System.out.println("initial bag: " + internal);

    	//System.out.println("removing: " + item + "(there are, " + count(item) + " of them)");

    	while(count(item)!=0) {
    		for(int i = 0; i<size(); i++) {
    			if(internal.get(i).equals(item)) {
        			internal.remove(i);
        			size--;
        			//System.out.println("now there are: " + count(item));
        		}
    		}
    	}
    	//System.out.println("all " + item + " removed");
		//System.out.println("new bag: " + internal + "\n");
    	}

    /**
     * The number of items in the bag. This is the sum
     * of the counts, not the number of unique items.
     * @return The number of items.
     */
    public int size() {
    	return size;
    }

    /**
     * Is the bag empty?
     * @return True if the bag is empty, false otherwise.
     */
    public boolean isEmpty() {
    	return size()<=0;
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
