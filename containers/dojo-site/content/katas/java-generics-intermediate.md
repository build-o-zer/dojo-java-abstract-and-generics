---
title: "Java Generics with Bounds and Wildcards"
subject: "Bounded Types and Wildcard Generics"
goal: "Master bounded type parameters and wildcard usage in generics"
level: "Intermediate"
duration: "30-35 minutes"
category: "Generics"
difficulty: "intermediate"
concepts:
  - "Bounded type parameters"
  - "Wildcards"
  - "Upper bounds"
  - "Lower bounds"
  - "PECS principle"
prerequisites:
  - "Generic fundamentals"
  - "Type parameters"
  - "Collections framework"
estimated_time: 35
ninja_belt: "orange"
---

*Sensei:* Excellent progress, my student. You have mastered basic generics. Now we venture into deeper waters - bounded types and wildcards.

*Deshi:* Sensei, I'm comfortable with `<T>`, but what about `<T extends Number>` and `<? super T>`?

*Sensei:* Ah, you seek to understand constraints and variance. These are the tools that allow generics to express complex type relationships safely.

## The Challenge: Beyond Simple Type Parameters

*Sensei:* Consider this repository pattern. It should store entities, but currently accepts any `Object`:

```java
Repository repo = new Repository();
repo.add("String");        // Should this be allowed?
repo.add(new Person());    // What about this?
repo.add(new Product());   // Or this?
```

*Deshi:* I see the problem. Without bounds, we lose the benefit of knowing what types are related.

## Understanding Bounded Type Parameters

```mermaid
classDiagram
    class Number {
        <<abstract>>
    }
    
    class Integer {
    }
    
    class Double {
    }
    
    class String {
    }
    
    Number <|-- Integer
    Number <|-- Double
    
    note for Number "T extends Number\nallows Integer, Double, etc.\nbut NOT String"
```

*Sensei:* Observe the power of bounds:

```java
// Without bounds - accepts anything
public class Container<T> {
    public void process(T item) {
        // What methods can you call on T? Only Object methods!
        System.out.println(item.toString());
    }
}

// With bounds - constrains T to Number subtypes  
public class NumberContainer<T extends Number> {
    public void process(T item) {
        // Now you can call Number methods!
        double value = item.doubleValue();
        System.out.println("Value: " + value);
    }
}
```

## The PECS Principle: Producer Extends, Consumer Super

*Deshi:* Sensei, when should I use `extends` vs `super` with wildcards?

*Sensei:* Remember PECS - Producer Extends, Consumer Super:

```mermaid
graph TD
    A[Producer Extends] --> B["? extends T"]
    C[Consumer Super] --> D["? super T"]
    
    B --> E[Reading/Getting data]
    D --> F[Writing/Adding data]
    
    E --> G["Reading Example:<br/>List&lt;? extends Number&gt;<br/>Number n = list.get(0)"]
    F --> H["Writing Example:<br/>List&lt;? super Integer&gt;<br/>list.add(42)"]
```

## Your Mission

Transform these classes to use proper bounded generics and wildcards:

### Task 1: Generic Repository with Entity Bounds

Convert the `Repository` to work with entities:

```java
// BEFORE
public class Repository {
    private List<Object> items = new ArrayList<>();
    
    public void add(Object item) {
        items.add(item);
    }
    // ... other methods
}

// AFTER (your implementation)
public class Repository<T extends Entity> {
    // TODO: Add proper bounds and wildcards
    // TODO: Implement PECS pattern for collections
}
```

*Sensei:* The repository should only accept entities that extend `Entity`, ensuring type safety.

### Task 2: Sorting Utilities with Comparable Bounds

Fix the `SortingUtils` to work safely with comparable types:

```java
// BEFORE (unsafe)
public static void sort(List list) {
    list.sort(null); // Fails at runtime if not Comparable!
}

// AFTER (your implementation)
public static <T extends Comparable<? super T>> void sort(List<T> list) {
    // TODO: Implement with proper bounds
}
```

*Deshi:* That bound looks complex! What does `<? super T>` mean?

*Sensei:* It means T can compare itself to T or any supertype of T. This handles inheritance properly.

### Task 3: Advanced Collection Operations

Implement methods that demonstrate PECS:

```java
public class CollectionUtils {
    
    // Producer - reading from source
    public static <T> void copy(List<? extends T> source, List<? super T> destination) {
        // TODO: Implement copy with proper wildcards
    }
    
    // Consumer - writing to collection
    public static <T> void addAll(List<? super T> target, T... items) {
        // TODO: Implement addAll with proper bounds
    }
}
```

## Complex Generic Relationships

```mermaid
classDiagram
    class Entity {
        <<abstract>>
        +Long getId()
    }
    
    class Person {
        +String getName()
        +int getAge()
    }
    
    class Product {
        +String getName()
        +double getPrice()
    }
    
    class Repository~T extends Entity~ {
        -List~T~ items
        +add(T item)
        +T findById(Long id)
        +List~T~ findAll()
        +addAll(Collection~? extends T~ items)
        +copyTo(List~? super T~ destination)
    }
    
    Entity <|-- Person
    Entity <|-- Product
    Repository --> Entity : "T extends"
    
    note for Repository~T extends Entity~ "Bounded type parameter\nPECS for collections\nType-safe operations"
```

## Expected Results

After implementing bounds and wildcards:

```java
// Bounded repository
Repository<Person> personRepo = new Repository<>();
Person person = new Person(1L, "Alice", 25);
personRepo.add(person);

// This won't compile - String doesn't extend Entity
// Repository<String> stringRepo = new Repository<>(); // Compile error!

// PECS in action
List<Person> people = Arrays.asList(person);
List<Entity> entities = new ArrayList<>();

// Producer extends - can read Person as Entity
CollectionUtils.copy(people, entities); // Works!

// Consumer super - can add Person to Entity list  
List<Object> objects = new ArrayList<>();
CollectionUtils.copy(people, objects); // Works!

// Sorting with bounds
List<Integer> numbers = Arrays.asList(3, 1, 4, 1, 5);
SortingUtils.sort(numbers); // Type-safe sorting!

// This won't compile - Object is not Comparable
// List<Object> objects = Arrays.asList(new Object());
// SortingUtils.sort(objects); // Compile error!
```

## Variance in Action

```mermaid
graph LR
    A[List&lt;Integer&gt;] -->|extends| B[List&lt;? extends Number&gt;]
    C[List&lt;Number&gt;] -->|extends| B
    D[List&lt;Double&gt;] -->|extends| B
    
    E[List&lt;? super Integer&gt;] -->|super| F[List&lt;Number&gt;]
    E -->|super| G[List&lt;Object&gt;]
    
    B --> H[Read-only<br/>Producer]
    E --> I[Write-only<br/>Consumer]
```

## Key Concepts to Master

*Sensei:* Focus on these intermediate concepts:

1. **Bounded Type Parameters**: `<T extends Number>` constrains T to Number subtypes
2. **Upper Bounds**: `<? extends T>` - covariance for producers (reading)
3. **Lower Bounds**: `<? super T>` - contravariance for consumers (writing)
4. **PECS Principle**: Producer Extends, Consumer Super
5. **Multiple Bounds**: `<T extends Number & Comparable<T>>` 
6. **Generic Method Bounds**: Methods can have their own bounded type parameters

## Advanced Patterns

### Recursive Bounds

```java
// Comparable that compares to itself
public interface Comparable<T> {
    int compareTo(T other);
}

// Enum with recursive bound
public abstract class Enum<E extends Enum<E>> {
    // E must be a subtype of Enum<E>
}
```

### Intersection Types

```java
// Multiple bounds with &
public static <T extends Number & Comparable<T>> T max(T a, T b) {
    return a.compareTo(b) > 0 ? a : b;
}
```

## Testing Your Implementation

Run the tests to verify your bounded generics:

```bash
mvn test -Dtest=RepositoryTest
mvn test -Dtest=SortingUtilsTest
```

## Common Pitfalls

*Deshi:* What mistakes should I avoid?

*Sensei:* Be mindful of these traps:

1. **Mixing Extends and Super**: Don't use both in the same declaration
2. **Forgetting PECS**: Use `extends` for reading, `super` for writing
3. **Over-constraining**: Don't add bounds unless you need them
4. **Generic Array Creation**: `new T[10]` doesn't work - use collections instead

## Reflection Questions

*Sensei:* Contemplate these deeper truths:

1. When would you use `<T extends Comparable<T>>` vs `<T extends Comparable<? super T>>`?
2. How do wildcards enable safe variance in Java's type system?
3. Why can't you add to a `List<? extends T>`?
4. When would you need multiple bounds like `<T extends A & B>`?

*Deshi:* I'm starting to see how bounds create a safety net while preserving flexibility!

*Sensei:* Precisely. Bounds are the bridge between strict type safety and flexible reusability.

## Next Steps

Master these concepts before advancing to:
- Complex recursive bounds and self-types
- Generic method type inference
- Advanced variance patterns in functional programming

---

*"In bounded generics, we find the harmony between constraint and freedom."* - Ancient Java Wisdom
