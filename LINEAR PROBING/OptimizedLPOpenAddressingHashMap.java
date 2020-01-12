package impl;

import java.util.Iterator;

/**
 * OptimizedLPOpenAddressingHashMap
 * 
 * An extension to open addressing that avoids using sentinel
 * deleted values when using the linear probing strategy.
 * 
 * @author Thomas VanDrunen
 * CSCI 345, Wheaton College
 * May 18, 2017
 * @param <K> The key-type of the map
 * @param <V> The value-type of the map
 */
public class OptimizedLPOpenAddressingHashMap<K,V> extends OpenAddressingHashMap<K, V> {

    /**
     * Actually unnecessary since the default constructor would
     * have the same effect, but this shows intentionality.
     */
    public OptimizedLPOpenAddressingHashMap() {
        super(1);
    }
    
    
    /**
     * Remove the association for this key, if it exists.
     * @param key The key to remove
     */
    @Override  // now that's a REAL override
    public void remove(K key) {

    	if(!containsKey(key)) {
    		return;
    	}
    
    	//set the gap index to the index of the key to remove
    	int g = find(key);
    	
    	//remove the key (create a null gap)
    	table[g] = null;
    	 
    	//set the potential gap filler to the next index
    	int p = (g+1)%table.length;
    	
    	//until the potential gap filler is null or the 
    	while(table[p]!=null) {
    		//find the ideal index of the potential gap filler
    		int i = h.hash(table[p].key);
    		//if the gap comes between the ideal index of of p filler and p fill the gap
    		if((i<=g && g<p) || (p<i && i<=g) || (g<p && p<i)) {
	        	table[g] = new Pair<K,V>(table[p].key, table[p].value);	
	        	table[p] = null;
	        	g = p;
    		}
    		//else move on to the next potential gap filler
			p = (p+1)%table.length;

    	}
    	
    	//decrement numPairs
    	numPairs--;
    	return;
    }
   
}
