package impl;
/**
 * PQQueue.java
 *
 * Class to implement a queue using a priority queue.
 * 
 * CS 345, Wheaton College
 * Originally for CSCI 245, Spring 2007
 * Revised Jan 4, 2016
 */

import java.util.Comparator;
import java.util.NoSuchElementException;

import adt.FullContainerException;
import adt.Map;
import adt.Queue;

public class PQQueue<E> implements Queue<E> {

    /**
     * The priority queue to use as an internal representation.
     */
    private HeapPriorityQueue<E> pq;

    /**
     * Place to store data associated with representative
     * values in the priority queue.
     */
    private Map<E, Integer> arrivalTimes;

    

    /**
     * Constructor.
     * @param maxSize The capacity of this queue.
     */
    //arrivalTime tracker:
    private int index;
    public PQQueue(int maxSize) {
    	index = 1;
        arrivalTimes = new ListMap<E, Integer>();
        pq = new HeapPriorityQueue<E>(maxSize, new Comparator<E>() {
			public int compare(E o1, E o2) {
				if(arrivalTimes.get(o1)<arrivalTimes.get(o2)) return 1;
				if(arrivalTimes.get(o1)>arrivalTimes.get(o2)) return -1;
				else return 0;
			}
        });
    }

    /**
     * Is this queue empty? It is if the pq is empty.
     * @return True if this is empty, false otherwise.
     */
    public boolean isEmpty() { return pq.isEmpty(); }

    /**
     * Is this queue full? It is if the pq is full.
     * @return True if this is full, false otherwise.
     */
    public boolean isFull() { return pq.isFull(); }

    /**
     * Retrieve (but do not remove) the front element of this queue.
     * @return The front element.
     */
    public E front() { 
    	if(isEmpty()) throw new NoSuchElementException();
    	return pq.max();
    }

    /**
     * Retrieve and remove the front element of this queue.
     * @return The front element.
     */
    public E remove() {
    	if(isEmpty()) throw new NoSuchElementException();
    	E toRemove = pq.extractMax();
    	arrivalTimes.remove(toRemove);
    	return toRemove;
    	
    }

    /**
     * Add an element to the back of this queue.
     * @param x The element to add.
     */
    public void enqueue(E x) {
    	if(isFull()) throw new FullContainerException();
    	else {
        	arrivalTimes.put(x, index);
    		pq.insert(x);
        	index++;
    	}
    }

}
