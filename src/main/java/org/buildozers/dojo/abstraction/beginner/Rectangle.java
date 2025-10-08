package org.buildozers.dojo.abstraction.beginner;

/**
 * Initial problematic approach - Rectangle class with duplication
 * This is the "before" state that students will refactor.
 */
public class Rectangle {
    private double width;
    private double height;
    
    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }
    
    public double calculateArea() {
        return width * height;
    }
    
    public void displayInfo() {
        System.out.println("This is a rectangle with area: " + calculateArea());
    }
    
    public double getWidth() {
        return width;
    }
    
    public double getHeight() {
        return height;
    }
}
