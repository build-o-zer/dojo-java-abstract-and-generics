package org.buildozers.dojo.abstraction.beginner;

import lombok.Getter;

/**
 * Initial problematic approach - Shape classes with duplication
 * This is the "before" state that students will refactor.
 */
public class Circle extends Shape {
    @Getter
    private double radius;
    
    public Circle(double radius) {
        super("circle", "red");
        this.radius = radius;
    }
    
    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }
    
    @Override
    public double calcultePerimeter() {
        return 2 * Math.PI * radius;
    }
    
}
