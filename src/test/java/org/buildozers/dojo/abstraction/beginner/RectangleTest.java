package org.buildozers.dojo.abstraction.beginner;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Simple JUnit tests for Rectangle class
 * Tests basic functionality before abstraction refactoring
 */
class RectangleTest {
    
    private Rectangle rectangle;
    
    @BeforeEach
    void setUp() {
        rectangle = new Rectangle(4.0, 6.0);
    }
    
    @Test
    void testCalculateArea() {
        double expectedArea = 4.0 * 6.0;
        assertEquals(expectedArea, rectangle.calculateArea(), 0.001);
    }
    
    @Test
    void testCalculateAreaSquare() {
        Rectangle square = new Rectangle(5.0, 5.0);
        assertEquals(25.0, square.calculateArea(), 0.001);
    }
    
    @Test
    void testGetWidth() {
        assertEquals(4.0, rectangle.getWidth());
    }
    
    @Test
    void testGetHeight() {
        assertEquals(6.0, rectangle.getHeight());
    }
    
    @Test
    void testDisplayInfo() {
        // This test mainly ensures the method doesn't throw exceptions
        assertDoesNotThrow(() -> rectangle.displayInfo());
    }
    
    @Test
    void testZeroDimensions() {
        Rectangle zeroRectangle = new Rectangle(0.0, 5.0);
        assertEquals(0.0, zeroRectangle.calculateArea());
    }
    
    @Test
    void testUnitRectangle() {
        Rectangle unitRectangle = new Rectangle(1.0, 1.0);
        assertEquals(1.0, unitRectangle.calculateArea());
    }
}
