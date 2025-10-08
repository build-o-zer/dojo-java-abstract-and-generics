# Java Kata: Abstract Classes Fundamentals

**Subject:** Abstract Classes and Methods  
**Goal:** Understand the basics of abstract classes and when to use them  
**Level:** Beginner  
**Duration:** 15-20 minutes  

---

## 道場での対話 (Dojo Dialogue)

### Scene: The Workshop of Shapes

*The deshi sits before their IDE, staring at repetitive code. Multiple shape classes with similar structures but different implementations. The sensei observes quietly.*

**Sensei:** "I see you are troubled by repetition, young deshi. What burden weighs upon your code?"

**Deshi:** "Sensei, I have created classes for Circle, Rectangle, and Triangle. Each has an area calculation, but I find myself copying the same structure over and over. There must be a better way."

**Sensei:** "Ah, you have discovered the need for abstraction. When many things share a common essence but differ in details, we use abstract classes. Let me show you the way of the abstract."

---

## The Teaching

**Sensei:** "First, observe your current predicament:"

```java
// Current problematic approach
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
}

public class Rectangle {
    private double width, height;
    
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
}
```

**Deshi:** "Sensei, I see the pattern! Both have `calculateArea()` and `displayInfo()`, but `displayInfo()` is almost identical while `calculateArea()` differs."

**Sensei:** "Excellent observation! This is where abstract classes shine. They allow us to define common behavior while forcing subclasses to implement specific details."

---

## The Practice (実践)

**Sensei:** "Now, young deshi, we shall refactor with wisdom. First, create the abstract foundation:"

```java
public abstract class Shape {
    protected String name;
    
    public Shape(String name) {
        this.name = name;
    }
    
    // Abstract method - must be implemented by subclasses
    public abstract double calculateArea();
    
    // Concrete method - shared by all shapes
    public void displayInfo() {
        System.out.println("This is a " + name + " with area: " + calculateArea());
    }
    
    public String getName() {
        return name;
    }
}
```

<details>
<summary><button>🧙‍♂️ Advice from Sensei</button></summary>

**Sensei whispers:** "Notice how we use `protected` for the name field. This allows subclasses to access it directly while keeping it hidden from outside classes. Also, see how the abstract method `calculateArea()` has no body - it's like a promise that subclasses must fulfill."

</details>

### Basic Implementation

```java
public class Circle extends Shape {
    private double radius;
    
    public Circle(double radius) {
        super("circle");
        this.radius = radius;
    }
    
    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }
}
```

<details>
<summary><button>🧙‍♂️ Advice from Sensei</button></summary>

**Sensei advises:** "The `super("circle")` call invokes the parent constructor. The `@Override` annotation is not required but is highly recommended - it helps catch errors and makes intent clear."

</details>

---

## Common Pitfalls (落とし穴)

**Sensei warns:** "Beware these common traps, deshi:"

1. **Forgetting to implement abstract methods**
   ```java
   // This will not compile!
   public class Triangle extends Shape {
       // Error: must implement calculateArea()
   }
   ```

2. **Trying to instantiate abstract classes**
   ```java
   // This will not compile!
   Shape shape = new Shape("generic"); // Cannot instantiate abstract class
   
   // Correct way:
   Shape shape = new Circle(5.0); // Instantiate concrete subclass
   ```

3. **Misunderstanding abstract vs concrete methods**
   ```java
   public abstract class BadExample {
       // Wrong: abstract methods cannot have a body
       public abstract void doSomething() {
           System.out.println("This won't compile");
       }
   }
   ```

---

## The Challenge (挑戦)

**Sensei:** "Now, deshi, prove your understanding with this challenge:"

### Setup
Navigate to your `abstraction` package and examine the initial code structure.

<details>
<summary><button>🎯 Solution from Sensei - Step 1</button></summary>

Create the abstract `Shape` class:

```java
package org.buildozers.dojo.abstraction;

public abstract class Shape {
    protected String name;
    protected String color;
    
    public Shape(String name, String color) {
        this.name = name;
        this.color = color;
    }
    
    public abstract double calculateArea();
    public abstract double calculatePerimeter();
    
    public void displayInfo() {
        System.out.printf("%s %s - Area: %.2f, Perimeter: %.2f%n", 
            color, name, calculateArea(), calculatePerimeter());
    }
    
    public String getName() { return name; }
    public String getColor() { return color; }
}
```

</details>

### Your Mission
1. Create an abstract `Shape` class with:
   - Fields for `name` and `color`
   - Abstract methods for `calculateArea()` and `calculatePerimeter()`
   - A concrete `displayInfo()` method
   
2. Implement three concrete classes:
   - `Circle` (radius)
   - `Rectangle` (width, height)
   - `Square` (side)

3. Create a test class to demonstrate polymorphism

<details>
<summary><button>🎯 Solution from Sensei - Step 2</button></summary>

Implement the concrete classes:

```java
// Circle.java
package org.buildozers.dojo.abstraction;

public class Circle extends Shape {
    private double radius;
    
    public Circle(String color, double radius) {
        super("circle", color);
        this.radius = radius;
    }
    
    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }
    
    @Override
    public double calculatePerimeter() {
        return 2 * Math.PI * radius;
    }
}

// Rectangle.java
package org.buildozers.dojo.abstraction;

public class Rectangle extends Shape {
    private double width, height;
    
    public Rectangle(String color, double width, double height) {
        super("rectangle", color);
        this.width = width;
        this.height = height;
    }
    
    @Override
    public double calculateArea() {
        return width * height;
    }
    
    @Override
    public double calculatePerimeter() {
        return 2 * (width + height);
    }
}
```

</details>

<details>
<summary><button>🎯 Solution from Sensei - Step 3</button></summary>

Complete implementation with Square and test:

```java
// Square.java
package org.buildozers.dojo.abstraction;

public class Square extends Shape {
    private double side;
    
    public Square(String color, double side) {
        super("square", color);
        this.side = side;
    }
    
    @Override
    public double calculateArea() {
        return side * side;
    }
    
    @Override
    public double calculatePerimeter() {
        return 4 * side;
    }
}

// ShapeTest.java
package org.buildozers.dojo.abstraction;

public class ShapeTest {
    public static void main(String[] args) {
        Shape[] shapes = {
            new Circle("red", 5.0),
            new Rectangle("blue", 4.0, 6.0),
            new Square("green", 3.0)
        };
        
        for (Shape shape : shapes) {
            shape.displayInfo();
        }
    }
}
```

</details>

### Expected Result
```
red circle - Area: 78.54, Perimeter: 31.42
blue rectangle - Area: 24.00, Perimeter: 20.00
green square - Area: 9.00, Perimeter: 12.00
```

---

## Reflection (反省)

**Sensei:** "What wisdom have you gained today, deshi?"

**Deshi:** "I have learned that abstract classes are like blueprints - they define what must exist without dictating exactly how. They eliminate code duplication while ensuring consistency."

**Sensei:** "Well spoken. Abstract classes are the foundation of good object-oriented design. They provide structure while preserving flexibility."

### Key Takeaways
- 🏗️ Abstract classes define common structure and behavior
- 🎯 Abstract methods must be implemented by subclasses
- 🔄 Concrete methods in abstract classes provide shared functionality
- 🚫 You cannot instantiate abstract classes directly
- ✅ Abstract classes enable polymorphism and code reuse

---

## Next Steps

Continue your journey with:
- [Java Kata: Abstract Classes for Refactoring](./java-abstraction-intermediate.md)
- [Java Kata: Advanced Abstraction Patterns](./java-abstraction-advanced.md)

---

*"In abstraction, we find the essence of things, free from the burden of specific implementation."* - Java Dojo Wisdom
