package impl;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

import adt.Set;

/**
 * TrieSet
 * 
 * Implementation of the Set ADT for strings using a trie.
 * 
 * @author Thomas VanDrunen
 * CSCI 345, Wheaton College
 * April 22, 2015, revise April 19, 2016
 */
public class TrieSet implements Set<String> {

    /**
     * Class for nodes in this Trie.
     * Invariant: No node except the root can be empty; i.e., For any
     * node (except the root), either it is terminal or it has
     * at least one non-null child (which in turn must either be
     * non-terminal or have at least one non-null child...)
     */
    private class TrieNode {
        /**
         * Children nodes in this trie
         */
        TrieNode[] children;

        /**
         * Is the string which would end at this node (not
         * descend into any child node) in this set?
         */
        boolean terminal;

        /**
         * Constructor for a node initially empty
         * (which, apart from the root, would break the invariant)
         */
        TrieNode() {
            children = new TrieNode[26];
            terminal = false;
        }

        /**
         * Count the number of string (suffixes) in the
         * subtrie rooted at this node.
         * @return
         */
        
        
        
        public int size() { 
        	TrieNode node = root;
        	int size = 0;
        	if(node == null) {
        		return size;
        	}
        	Stack<TrieNode> stack = new Stack<TrieNode>();
        	stack.push(node);
        	
        	while(!stack.isEmpty()) {
        		TrieNode top = stack.pop();
        		for(int i = 0; i < 26; i++) {
        			if(top.children[i] != null)
        			stack.push(top.children[i]);
        		}
        		if(top.terminal) size++;
        	}
        	return size;
        }
        
        


        /**
         * Remove a String (suffix) from the subtrie rooted here.
         * If that removal empties this subtrie, then the subtrie
         * should be pruned.
         * @param item The string to be removed from this subtrie, which
         * is a suffix of the string to be removed from the entire trie.
         * @return this, if the subtrie rooted here is still live after the 
         * removal, or null otherwise
         */
        public TrieNode remove(String item) {
        	
        	if(!contains(item)) {
        		return this;
        	}
        	
    		TrieNode current = root;
        	int i = 0;
        	int terminalCount = 0;
        	
        	//until we reach the last letter of the string...
        	while(i<item.length()-1) {
        		//move down the trie
        		if(current.terminal) terminalCount++;
        		current = current.children[c2i(item.charAt(i++))];
        	}
        	
        	if(root.terminal) {
        		root.terminal=false;
        	}
        	
        	//string is unique (contains no other strings, not a prefix of a longer string)—delete all nodes
        	if(terminalCount == 0 && current.numChildren() == 1) {
        		String newString = item.substring(0, i);
        		current.children[c2i(item.charAt(i))] = null;
        		current.terminal = true;
        		remove(newString);
        	}
        	
        	//string is prefix of another longer string—set terminal to false
        	else if(current.numChildren() > 1) {
        		current.children[c2i(item.charAt(i))].terminal = false;
        	}
        	return this;
        }
        
        public int numChildren() {
        	int numChildren = 0;
        	for(int i = 0; i<26; i++) {
        		if(this.children[i] != null) numChildren++;
        	}
        	return numChildren;
        }
        

        
        

        
		/**
         * Iterator for the strings in this subtrie.
         * @param prefix The prefix to prepend to the suffixes found
         * in this subtrie to make them strings in the main trie
         * @return The iterator
         */
        public Iterator<String> iterator(final String prefix) {
             throw new UnsupportedOperationException(); //(BONUS)
        }

    /**
     * Iterator for the strings in this subtrie that match the given
     * pattern, each with the given prefix prepended to them.
     * The pattern is made of letters and . as a wildcard.
     * For example, if prefix = "ABC" and pattern = ".D.E", then
     * this iterator would return such words as "ABCZDXE" and 
     * "ABCTDFE" if this subtrie contained the strings "ZDXE" and 
     * "TDFE".
     * @param prefix The prefix to prepend to the suffixes found
         * in this subtrie to make them strings in the main trie
     * @param pattern The pattern to match strings in this subtrie
     * against, make up of letters (interpreted literally) and .
     * interpreted as a wildcard.
     */
        public Iterator<String> matchIterator(final String prefix, final String pattern) {
             throw new UnsupportedOperationException(); // (BONUS)
        }
    }

    // --- Exception classes ---
    
    public static class BadCharException extends RuntimeException {
        public BadCharException(char c) {
            super("Bad character: " + c);
        }

        private static final long serialVersionUID = -3495608442105421490L;
    }

    public class BadModeException extends RuntimeException {
        private static final long serialVersionUID = -7783643567574205891L;

        public BadModeException(int mode) {
            super("Unknown Trie mode: " + mode);
        }

    }


    // --- The main parts of the TrieSet class start here ---
    
    /**
     * The root of the trie. This is never null, even when
     * the trie is empty (apart from the root, we assume that
     * (non-null) nodes are never empty).
     */
    private TrieNode root;

    /**
     * 0 - capitals only; 1 - lowercase only; 2 - case insensitive
     */
    private int mode;

    /**
     * Convert a character to an index, according to the mode.
     */
    private int c2i(char c) {
        if ((mode == 0 || mode == 2) && c >= 'A' && c <= 'Z')
            return c - 'A';
        else if ((mode == 1 || mode == 2) && c >= 'a' && c <= 'z')
            return c - 'a';
        else
            throw new BadCharException(c);
    }

    /**
     * Convert an index to a character, according to the mode.
     */
    private char i2c(int i) {
        if (mode == 0 || mode == 2) return (char) ('A' + i);
        else return (char) ('a' + i);
    }

    /**
     * Constructor.
     * @param mode 0 - capitals only; 1 - lowercase only; 2 - case insensitive
     */
    public TrieSet(int mode) {
        if (mode < 0 || mode > 2)
            throw new BadModeException(mode);
        this.mode = mode;
        root = new TrieNode();
    }

    /**
     * Constructor defaulting to capitals only
     */
    public TrieSet() { this(0); }

    
    /**
     * Add an item to this set.
     */
    public void add(String item) {
    	if(!contains(item)) {
    		TrieNode current = root;
        	for(int i = 0; i < item.length(); i++) {
        		//if the next character doesn't exist within the tree yet...
        		if(current.children[c2i(item.charAt(i))] == null) {
        			//make a new node there
        			current.children[c2i(item.charAt(i))] = new TrieNode();

        		}
        		//then, move current to the next character down
        		current = current.children[c2i(item.charAt(i))];
        	}
        	current.terminal = true;
    	}
    	return;
    }


    /**
     * Does this set contain the given item?
     */
    public boolean contains(String item) {
    	TrieNode current = root;
    	int i = 0;
    	while(current != null && i<item.length()) current = current.children[c2i(item.charAt(i++))];
    	return current != null && current.terminal;
    	}

    /**
     * Remove the given item from the set, if it exists
     */
    public void remove(String item) {
        root.remove(item);
    }

    /**
     * Compute the number of items in the set
     */
    public int size() {
        return root.size();
    }

    /**
     * Test if the set is empty. We could call size(),
     * but it's illuminating to do it directly by looking
     * at the root.
     */
    public boolean isEmpty() {
    	return size()==0;
    }

    /**
     * Make an iterator over the strings in this trie.
     */
    public Iterator<String> iterator() {
        return root.iterator("");
    }

    /**
     * Find and return the longest string in the set, if any,
     * that is a prefix of the given string.
     * @param s The string to find a prefix of
     * @return The longest string, if any, in the set, that is
     * a prefix of s
     */
    public String longestPrefixOf(String s) {
         throw new UnsupportedOperationException();//(BONUS)
    }

    /**
     * Return the keys with the given prefix as an iterable collection.
     * @param s The string to find prefixes for
     * @return
     */
    public Iterable<String> keysWithPrefix(final String s) {
         throw new UnsupportedOperationException(); //(BONUS)
    }

    /**
     * Return the keys that match the given pattern as an iterable
     * collection. The "pattern" is a string with some letters replaced
     * with periods. Thus this will find keys of the same length as
     * the given pattern as also having the same letters (in the same
     * places) of those letters that exist in the pattern, but treating
     * the periods as wildcards. This functionality is suggested by
     * Sedgewick. Of course more interesting stuff could be done
     * (say, regular expressions), but this is simple enough for
     * a quick exercise.
     * @param s
     * @return
     */
    public Iterable<String> keysThatMatch(final String s) {
        return new Iterable<String>() {
            public Iterator<String> iterator() {
                return root.matchIterator("", s);
            }
        };
    }

}
