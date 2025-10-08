package org.buildozers.dojo.abstraction.beginner;

/**
 * Initial problematic approach - Shape classes with duplication
 * This is the "before" state that students will refactor.
 */
public class Circle {
    private double radius;
    
    public Circle(double radius) {
        this.radius = radius;
    }
    
    public double calculateArea() {
        return Math.PI * radius * radius;
    }
    
    public void displayInfo() {
        System.out.println("This is a circle with area: " + calculateArea());
    }
    
    public double getRadius() {
        return radius;
    }
}
