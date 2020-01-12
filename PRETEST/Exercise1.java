package exercises;

public class Exercise1 {

    /**
     * Find the position of an item in a given array, if it is anywhere.
     * PRECONDITION: The array is sorted and contains no null elements.
     * @param array An array sorted by its elements' natural ordering
     * (as expressed by compareTo()).
     * @param item The item for which to search.
     * @return A position in the array which contains the item, or -1 if
     * it does not occur in the array (including if the array is empty or null)
     */
    public static <T extends Comparable<T>> int binarySearch(T[] array, T item) {
    	if(array == null) {
    		return -1;
    	}
    	else {
        	int first = 0;
        	int last = array.length;
        	int mid = 0;
        	while (last - first > 1) {
        		mid = (first + last) / 2;
        		int x = item.compareTo(array[mid]);
        		if(x<0)
        			last = mid;
        		else if(x>0)
        			first = mid+1;
        		else {
        			first = mid;
        			last = mid + 1;
        		}
        	}
        	if(first<last && item.compareTo(array[first]) == 0)
        		return first;
        	else return -1;
    	}

    }
    
}
