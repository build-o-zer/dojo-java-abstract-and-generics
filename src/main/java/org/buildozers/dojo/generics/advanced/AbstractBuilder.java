package org.buildozers.dojo.generics.advanced;

import java.util.List;

/**
 * Builder pattern with recursive generic bounds.
 * 
 * KATA TASK: Implement fluent builder with recursive generic bounds.
 * - Use "Curiously Recurring Template Pattern" (CRTP)
 * - Ensure type safety in method chaining
 * - Handle inheritance properly with generics
 */
public abstract class AbstractBuilder {
    
    /**
     * Abstract build method - subclasses define what they build.
     * TASK: Add proper generic bounds using recursive pattern
     */
    public abstract Object build();
    
    /**
     * Common validation method.
     * TASK: Add proper return type for method chaining
     */
    public AbstractBuilder validate() {
        // Validation logic here
        return this;
    }
    
    /**
     * Reset builder state.
     * TASK: Add proper return type for method chaining
     */
    public AbstractBuilder reset() {
        // Reset logic here
        return this;
    }
}

/**
 * Concrete builder for Person objects.
 * TASK: Implement proper generic bounds for method chaining.
 */
class PersonBuilder extends AbstractBuilder {
    private String name;
    private int age;
    private List<String> hobbies;
    
    public PersonBuilder setName(String name) {
        this.name = name;
        return this;
    }
    
    public PersonBuilder setAge(int age) {
        this.age = age;
        return this;
    }
    
    public PersonBuilder setHobbies(List<String> hobbies) {
        this.hobbies = hobbies;
        return this;
    }
    
    @Override
    public Object build() {
        // Would return Person object
        return new Object(); // Placeholder
    }
}

/**
 * Another concrete builder to demonstrate the pattern.
 */
class CarBuilder extends AbstractBuilder {
    private String make;
    private String model;
    private int year;
    
    public CarBuilder setMake(String make) {
        this.make = make;
        return this;
    }
    
    public CarBuilder setModel(String model) {
        this.model = model;
        return this;
    }
    
    public CarBuilder setYear(int year) {
        this.year = year;
        return this;
    }
    
    @Override
    public Object build() {
        // Would return Car object
        return new Object(); // Placeholder
    }
}

/**
 * Generic factory for builders.
 * TASK: Add proper generic bounds for factory methods.
 */
class BuilderFactory {
    
    public static Object createBuilder(Class type) {
        // Factory logic would go here
        return new Object(); // Placeholder
    }
    
    public static Object createAndBuild(Class type, Object... params) {
        // Create, configure, and build in one call
        return new Object(); // Placeholder
    }
    
    private BuilderFactory() {
        // Private constructor
    }
}
