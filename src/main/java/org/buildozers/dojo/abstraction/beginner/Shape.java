package org.buildozers.dojo.abstraction.beginner;

import lombok.Getter;

/**
 * Abastract class for Shape
 */
public abstract class Shape {

    @Getter
    protected String name;

    @Getter
    protected String color;

    public Shape(String name, String color) {
        this.name = name;
        this.color = color; 
    }

    public abstract double calculateArea();

    public abstract double calcultePerimeter();

    public void displayInfo() {
        System.out.println("This is a " +color + " " + name 
        + " with area: " + calculateArea() 
        + " and with perimeter: " + calcultePerimeter());
    }
}
