package org.buildozers.dojo.abstraction.beginner;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Simple JUnit tests for Triangle class
 * Tests basic functionality before abstraction refactoring
 */
class TriangleTest {
    
    private Triangle triangle;
    
    @BeforeEach
    void setUp() {
        triangle = new Triangle(8.0, 3.0);
    }
    
    @Test
    void testCalculateArea() {
        double expectedArea = 0.5 * 8.0 * 3.0;
        assertEquals(expectedArea, triangle.calculateArea(), 0.001);
    }
    
    @Test
    void testCalculateAreaRightTriangle() {
        Triangle rightTriangle = new Triangle(6.0, 4.0);
        assertEquals(12.0, rightTriangle.calculateArea(), 0.001);
    }
    
    @Test
    void testGetBase() {
        assertEquals(8.0, triangle.getBase());
    }
    
    @Test
    void testGetHeight() {
        assertEquals(3.0, triangle.getHeight());
    }
    
    @Test
    void testDisplayInfo() {
        // This test mainly ensures the method doesn't throw exceptions
        assertDoesNotThrow(() -> triangle.displayInfo());
    }
    
    @Test
    void testZeroBase() {
        Triangle zeroTriangle = new Triangle(0.0, 5.0);
        assertEquals(0.0, zeroTriangle.calculateArea());
    }
    
    @Test
    void testUnitTriangle() {
        Triangle unitTriangle = new Triangle(2.0, 1.0);
        assertEquals(1.0, unitTriangle.calculateArea());
    }
}
