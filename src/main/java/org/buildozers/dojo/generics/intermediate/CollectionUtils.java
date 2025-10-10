package org.buildozers.dojo.generics.intermediate;

import java.util.List;

/**
 * Utility class demonstrating PECS (Producer Extends, Consumer Super) principles
 * with wildcard generics.
 * 
 * This class provides methods that show how to use bounded wildcards effectively:
 * - Producer (? extends T): When you need to read from a collection
 * - Consumer (? super T): When you need to write to a collection
 * 
 * @since 1.0
 */
public class CollectionUtils {
    
    /**
     * Private constructor to prevent instantiation of utility class.
     */
    private CollectionUtils() {
        throw new UnsupportedOperationException("Utility class should not be instantiated");
    }
    
    /**
     * Copies all elements from source collection to destination collection.
     * Demonstrates PECS principle:
     * - Source uses "? extends T" (Producer) - we read from it
     * - Destination uses "? super T" (Consumer) - we write to it
     * 
     * This allows flexible usage:
     * - Can copy List&lt;Person&gt; to List&lt;Entity&gt; (Person extends Entity)
     * - Can copy List&lt;Person&gt; to List&lt;Object&gt; (Object is super of Person)
     * 
     * @param <T> the type parameter for the copy operation
     * @param source the source collection to copy from (Producer - extends T)
     * @param destination the destination collection to copy to (Consumer - super T)
     * @throws IllegalArgumentException if source or destination is null
     */
    public static <T> void copy(List<? extends T> source, List<? super T> destination) {
        // TODO: Implement copy with proper wildcards
        // Hint: Iterate through source and add each element to destination
        throw new UnsupportedOperationException("TODO: Implement copy method using PECS principles");
    }
    
    /**
     * Adds all provided items to the target collection.
     * Demonstrates Consumer pattern with "? super T" - we write to the collection.
     * 
     * This allows adding Person objects to List&lt;Entity&gt; or List&lt;Object&gt;.
     * 
     * @param <T> the type parameter for the items
     * @param target the target collection to add items to (Consumer - super T)
     * @param items the items to add (varargs)
     * @throws IllegalArgumentException if target is null
     */
    @SafeVarargs
    public static <T> void addAll(List<? super T> target, T... items) {
        // TODO: Implement addAll with proper bounds
        // Hint: Iterate through items and add each to target
        throw new UnsupportedOperationException("TODO: Implement addAll method using Consumer pattern");
    }
}
