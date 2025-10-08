package org.buildozers.dojo.generics.beginner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the UnsafeBox -> Generic Box conversion.
 * 
 * These tests demonstrate why generics are needed for type safety.
 * The first tests show problems with current implementation,
 * later tests will work after generics implementation.
 */
public class UnsafeBoxTest {
    
    private UnsafeBox box;
    
    @BeforeEach
    public void setUp() {
        box = new UnsafeBox();
    }
    
    @Test
    public void testBasicOperations() {
        assertTrue(box.isEmpty());
        
        box.setItem("Hello World");
        assertFalse(box.isEmpty());
        assertEquals("Hello World", box.getItem());
        
        box.clear();
        assertTrue(box.isEmpty());
    }
    
    @Test
    public void testTypeSafetyIssue() {
        // This test shows the problem with non-generic box
        box.setItem("String value");
        
        // This requires casting and can fail at runtime
        String value = (String) box.getItem();
        assertEquals("String value", value);
        
        // This would cause ClassCastException at runtime:
        // box.setItem(123);
        // String badCast = (String) box.getItem(); // Runtime error!
    }
    
    // TODO: After implementing Generic Box<T>, add these tests:
    /*
    @Test
    public void testGenericBoxString() {
        Box<String> stringBox = new Box<>();
        stringBox.setItem("Hello");
        String value = stringBox.getItem(); // No casting needed!
        assertEquals("Hello", value);
    }
    
    @Test
    public void testGenericBoxInteger() {
        Box<Integer> intBox = new Box<>();
        intBox.setItem(42);
        Integer value = intBox.getItem(); // No casting needed!
        assertEquals(Integer.valueOf(42), value);
    }
    
    @Test
    public void testCompileTimeTypeSafety() {
        Box<String> stringBox = new Box<>();
        // stringBox.setItem(123); // This would not compile!
        // This is the power of generics - compile-time type safety
    }
    */
}
