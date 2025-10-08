package org.buildozers.dojo.abstraction.beginner;

/**
 * Initial problematic approach - Triangle class with duplication
 * This is the "before" state that students will refactor.
 */
public class Triangle {
    private double base;
    private double height;
    
    public Triangle(double base, double height) {
        this.base = base;
        this.height = height;
    }
    
    public double calculateArea() {
        return 0.5 * base * height;
    }
    
    public void displayInfo() {
        System.out.println("This is a triangle with area: " + calculateArea());
    }
    
    public double getBase() {
        return base;
    }
    
    public double getHeight() {
        return height;
    }
}
