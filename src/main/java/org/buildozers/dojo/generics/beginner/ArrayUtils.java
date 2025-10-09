package org.buildozers.dojo.generics.beginner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Utility class with non-generic methods that have type safety issues.
 * 
 * KATA TASK: Convert these methods to use generics for better type safety.
 */
public class ArrayUtils<T> {
    
    /**
     * Finds the first element in array.
     * Problems: Returns Object, requires casting, no compile-time type safety.
     */
    public static <T> T findFirst(T[] array) {
        if (array == null || array.length == 0) {
            return null;
        }
        return array[0];
    }
    
    /**
     * Finds the last element in array.
     */
    public static <T> T findLast(T[] array) {
        if (array == null || array.length == 0) {
            return null;
        }
        return array[array.length - 1];
    }
    
    /**
     * Swaps two elements in an array.
     */
    public static <T> void swap(T[] array, int i, int j) {
        if (array == null || i < 0 || j < 0 || i >= array.length || j >= array.length) {
            throw new IllegalArgumentException("Invalid array or indices");
        }
        T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
    
    /**
     * Converts array to list.
     */
    public static <T> List<T> toList(T[] array) {
        if (array == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(Arrays.asList(array));
    }
}
