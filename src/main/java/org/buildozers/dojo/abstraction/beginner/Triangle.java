package org.buildozers.dojo.abstraction.beginner;

import lombok.Getter;

/**
 * Initial problematic approach - Triangle class with duplication
 * This is the "before" state that students will refactor.
 */
public class Triangle extends Shape {
    @Getter
    private double base;

    @Getter
    private double height;
    
    public Triangle(double base, double height) {
        super("triangle", "green");
        this.base = base;
        this.height = height;
    }
    
    @Override
    public double calculateArea() {
        return 0.5 * base * height;
    }
    
    @Override
    // We can't calculate the perimeter if we don't have at least where is the height from the base
    public double calcultePerimeter(){
        return 0;
    }
}
