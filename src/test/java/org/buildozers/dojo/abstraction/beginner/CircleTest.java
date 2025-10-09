package org.buildozers.dojo.abstraction.beginner;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Simple JUnit tests for Circle class
 * Tests basic functionality before abstraction refactoring
 */
class CircleTest {
    
    private Circle circle;
    
    @BeforeEach
    void setUp() {
        circle = new Circle(5.0);
    }
    
    @Test
    void testCalculateArea() {
        double expectedArea = Math.PI * 5.0 * 5.0;
        assertEquals(expectedArea, circle.calculateArea(), 0.001);
    }
    
    @Test
    void testCalculateAreaWithSmallRadius() {
        Circle smallCircle = new Circle(1.0);
        assertEquals(Math.PI, smallCircle.calculateArea(), 0.001);
    }
    
    @Test
    void testGetRadius() {
        assertEquals(5.0, circle.getRadius());
    }
    
    @Test
    void testDisplayInfo() {
        // This test mainly ensures the method doesn't throw exceptions
        assertDoesNotThrow(() -> circle.displayInfo());
    }
    
    @Test
    void testZeroRadius() {
        Circle zeroCircle = new Circle(0.0);
        assertEquals(0.0, zeroCircle.calculateArea());
    }
}
