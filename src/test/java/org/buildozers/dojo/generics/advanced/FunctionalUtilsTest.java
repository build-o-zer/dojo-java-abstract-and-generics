package org.buildozers.dojo.generics.advanced;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Tests for advanced functional utilities with complex generic bounds.
 */
class FunctionalUtilsTest {
    
    @Test
    void testCurrentUnsafeImplementation() {
        List<Object> numbers = Arrays.asList(1, 2, 3, 4, 5);
        
        // Current implementation is not type safe
        List<Object> doubled = (List<Object>) FunctionalUtils.map(numbers, x -> (Integer) x * 2);
        assertNotNull(doubled);
        
        List<Object> evens = (List<Object>) FunctionalUtils.filter(numbers, x -> (Integer) x % 2 == 0);
        assertNotNull(evens);
    }
    
    @Test
    void testUnsafeReduction() {
        List<Object> numbers = Arrays.asList(1, 2, 3, 4, 5);
        
        // Current implementation doesn't work properly
        Object sum = FunctionalUtils.reduce(numbers, 0, (a, b) -> (Integer) a + (Integer) b);
        // This won't work as expected due to lack of proper generics
        assertEquals(0, sum); // Placeholder assertion
    }
    
    // TODO: After implementing proper generics, these tests should work:
    /*
    @Test
    void testGenericMap() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        
        // Type-safe mapping
        List<String> strings = FunctionalUtils.map(numbers, Object::toString);
        assertEquals(Arrays.asList("1", "2", "3", "4", "5"), strings);
        
        List<Integer> doubled = FunctionalUtils.map(numbers, x -> x * 2);
        assertEquals(Arrays.asList(2, 4, 6, 8, 10), doubled);
    }
    
    @Test
    void testGenericFilter() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        
        List<Integer> evens = FunctionalUtils.filter(numbers, x -> x % 2 == 0);
        assertEquals(Arrays.asList(2, 4, 6), evens);
    }
    
    @Test
    void testGenericReduce() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        
        Integer sum = FunctionalUtils.reduce(numbers, 0, Integer::sum);
        assertEquals(Integer.valueOf(15), sum);
        
        String concatenated = FunctionalUtils.reduce(
            Arrays.asList("a", "b", "c"), 
            "", 
            (acc, str) -> acc + str
        );
        assertEquals("abc", concatenated);
    }
    
    @Test
    void testComplexBounds() {
        // Test with bounded types
        List<Number> numbers = Arrays.asList(1, 2.5, 3L, 4.0f);
        
        // Should work with Number and its subtypes
        List<String> strings = FunctionalUtils.map(numbers, Object::toString);
        assertEquals(4, strings.size());
    }
    */
}
