package org.buildozers.dojo.generics.intermediate;

import java.util.Comparator;
import java.util.List;

/**
 * Sorting utilities that need proper bounded generics.
 * 
 * KATA TASK: Add proper generic bounds using extends and super wildcards.
 * - Make sort methods work with Comparable types
 * - Use proper bounds for Comparator methods
 * - Implement PECS pattern correctly
 */
public class SortingUtils {
    
    /**
     * Sorts a list using natural ordering.
     * TASK: Add bounds to ensure T implements Comparable<T>
     */
    public static void sort(List list) {
        // Current implementation is unsafe
        list.sort(null); // This will fail at runtime if items are not Comparable
    }
    
    /**
     * Sorts a list using provided comparator.
     * TASK: Add proper generic bounds and wildcards
     */
    public static void sort(List list, Comparator comparator) {
        list.sort(comparator);
    }
    
    /**
     * Finds minimum element in list.
     * TASK: Add bounds to ensure T extends Comparable<? super T>
     */
    public static Object min(List list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        
        Object min = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            Object current = list.get(i);
            // This cast is unsafe!
            if (((Comparable) current).compareTo(min) < 0) {
                min = current;
            }
        }
        return min;
    }
    
    /**
     * Finds maximum element in list.
     */
    public static Object max(List list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        
        Object max = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            Object current = list.get(i);
            // This cast is unsafe!
            if (((Comparable) current).compareTo(max) > 0) {
                max = current;
            }
        }
        return max;
    }
    
    /**
     * Merges two sorted lists.
     * TASK: Add proper bounds and wildcards
     */
    public static List merge(List list1, List list2) {
        // Implementation would merge two sorted lists
        // For now, just return first list
        return list1;
    }
    
    private SortingUtils() {
        // Private constructor to hide implicit public one
    }
}
