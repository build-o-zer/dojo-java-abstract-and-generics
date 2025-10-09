package org.buildozers.dojo.abstraction.beginner;

/**
 * Test class demonstrating the problem with duplicated code
 * Students will see this and realize the need for abstraction
 */
public class ShapeTestBefore {
    public static void main(String[] args) {
        System.out.println("=== Before Abstraction - Notice the Duplication ===");
        
        Shape circle = new Circle(5.0);
        Shape rectangle = new Rectangle(4.0, 6.0);
        Shape triangle = new Triangle(3.0, 4.0);
        
        // Notice how similar the display logic is, but we can't treat them uniformly
        circle.displayInfo();
        rectangle.displayInfo();
        triangle.displayInfo();
        
        System.out.println("\nProblem: Each class has nearly identical displayInfo() method!");
        System.out.println("Solution: Use abstract classes to eliminate duplication.");
    }
}
