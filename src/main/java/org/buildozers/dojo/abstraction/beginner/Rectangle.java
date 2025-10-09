package org.buildozers.dojo.abstraction.beginner;

/**
 * Initial problematic approach - Rectangle class with duplication
 * This is the "before" state that students will refactor.
 */
public class Rectangle extends Shape {
    private double width;
    private double height;
    
    public Rectangle(double width, double height) {
        super("rectangle","blue");
        this.width = width;
        this.height = height;
    }
    
    @Override
    public double calculateArea() {
        return width * height;
    }
    
    @Override
    public double calcultePerimeter() {
        return 2 * (width + height);
    }
    
    public double getWidth() {
        return width;
    }
    
    public double getHeight() {
        return height;
    }
}
