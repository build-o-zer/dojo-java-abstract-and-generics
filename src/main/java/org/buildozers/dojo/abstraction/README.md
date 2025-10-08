# Java Abstraction Katas

This directory contains three progressive Java katas designed to teach and practice abstraction concepts, specifically abstract classes and abstract methods, and their benefits for refactoring.

## 📁 Package Structure

```
src/main/java/org/buildozers/dojo/abstraction/
├── beginner/          # Beginner kata - Basic abstract classes
├── intermediate/      # Intermediate kata - Refactoring with abstractions
└── advanced/          # Advanced kata - Complex abstraction patterns
```

## 🎯 Learning Objectives

- **Abstract Classes**: Understand when and how to use abstract classes
- **Abstract Methods**: Learn to define contracts through abstract methods
- **Template Method Pattern**: Master the template method design pattern
- **Refactoring**: Practice identifying duplication and eliminating it
- **Code Design**: Develop skills for creating maintainable, extensible code

## 📚 Kata Progression

### 1. Beginner: Abstract Classes Fundamentals
**File**: `java-abstraction-beginner.md`
- **Duration**: 15-20 minutes
- **Focus**: Basic abstract class concepts
- **Practice**: Shape hierarchy with area calculations
- **Key Concepts**: Abstract methods, concrete methods, inheritance

### 2. Intermediate: Abstract Classes for Refactoring  
**File**: `java-abstraction-intermediate.md`
- **Duration**: 25-30 minutes
- **Focus**: Refactoring legacy code using abstract classes
- **Practice**: Payment processing system refactoring
- **Key Concepts**: Template Method pattern, code duplication elimination

### 3. Advanced: Advanced Abstraction Patterns
**File**: `java-abstraction-advanced.md`  
- **Duration**: 35-45 minutes
- **Focus**: Complex abstraction patterns and system design
- **Practice**: Data processing pipeline with multiple abstraction layers
- **Key Concepts**: Generics with abstractions, multiple design patterns

## 🚀 Getting Started

### Prerequisites
- Java 11 or higher
- Basic understanding of object-oriented programming
- Familiarity with inheritance concepts

### Running the Initial Examples

Each kata includes "before" state examples that demonstrate the problems abstraction solves:

```bash
# Beginner kata - See the duplication problem
java -cp target/classes org.buildozers.dojo.abstraction.beginner.ShapeTestBefore

# Intermediate kata - See payment processing duplication  
java -cp target/classes org.buildozers.dojo.abstraction.intermediate.PaymentTestBefore

# Advanced kata - See monolithic design problems
java -cp target/classes org.buildozers.dojo.abstraction.advanced.MonolithicDataProcessor
```

### Building the Project

```bash
# Compile all classes
mvn clean compile

# Run tests
mvn test

# Build the complete project
mvn clean package
```

## 🏗️ Kata Structure

Each kata follows the dojo dialogue format:

1. **道場での対話 (Dojo Dialogue)**: Setting the scene with Sensei and Deshi
2. **The Teaching**: Core concepts explanation
3. **The Practice**: Hands-on implementation
4. **Common Pitfalls**: What to avoid
5. **The Challenge**: Progressive exercises with hidden solutions
6. **Reflection**: Learning consolidation

## 💡 Interactive Features

Each kata includes:

- **🧙‍♂️ Advice from Sensei**: Contextual tips and insights
- **🎯 Solution from Sensei**: Step-by-step solutions (hidden by default)
- **Multiple Difficulty Levels**: Progressive challenges within each kata

## 🎌 Dojo Philosophy

These katas follow traditional dojo principles:

- **Progressive Learning**: Each kata builds on the previous
- **Practice-Oriented**: Hands-on coding with immediate feedback
- **Reflection-Based**: Understanding why, not just how
- **Respectful Guidance**: Learning through dialogue and wisdom

## 📖 Using the Kata Viewer

Open `kata-viewer.html` in your browser to:
- Read katas in a formatted, interactive interface
- Toggle solution visibility
- Get contextual advice from the Sensei
- Track your progress through the learning path

## 🔗 Next Steps

After completing these katas, consider exploring:
- Interface vs Abstract Class comparisons
- Advanced design patterns (Strategy, Factory, Observer)
- Generic programming with abstractions
- Testing strategies for abstract classes

---

*"In abstraction, we find the essence of things, free from the burden of specific implementation."* - Java Dojo Wisdom
