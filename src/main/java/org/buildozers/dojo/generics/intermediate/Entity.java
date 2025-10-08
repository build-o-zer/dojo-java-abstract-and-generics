package org.buildozers.dojo.generics.intermediate;

/**
 * Entity base class and specific entities.
 * TASK: Create proper inheritance hierarchy with generics.
 */
public abstract class Entity {
    protected Long id;
    
    public Entity(Long id) {
        this.id = id;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
}

/**
 * Person entity extending Entity
 */
class Person extends Entity {
    private String name;
    private int age;
    
    public Person(Long id, String name, int age) {
        super(id);
        this.name = name;
        this.age = age;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getAge() {
        return age;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
    
    @Override
    public String toString() {
        return "Person{id=" + id + ", name='" + name + "', age=" + age + "}";
    }
}

/**
 * Product entity extending Entity
 */
class Product extends Entity {
    private String name;
    private double price;
    
    public Product(Long id, String name, double price) {
        super(id);
        this.name = name;
        this.price = price;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    @Override
    public String toString() {
        return "Product{id=" + id + ", name='" + name + "', price=" + price + "}";
    }
}
