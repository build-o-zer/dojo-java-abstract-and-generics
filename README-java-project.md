# 🎯 Maven Java 21 Project Generator

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://openjdk.java.net/projects/jdk/21/)
[![Maven](https://img.shields.io/badge/Maven-3.8+-blue.svg)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

A comprehensive Maven archetype project for Java 21 development with modern tooling and best practices.

## TL;DR

```bash
$ ./bin/build-and-install-archetype.sh --clean --verbose
```

An alias `mvn-java21-generate` is created for easy project generation.

Create a ~/tmp directory and generate a new project:

```bash
$ mkdir -p ~/tmp
$ cd ~/tmp
$ mvn-java21-generate com.example my-awesome-app 1.0.0
```
This creates a new Maven project `my-awesome-app` with Java 21 support and best practices with lombok and JShell.

## Running a JShell Session

From this project directory, you can start a JShell session with the project classpath:

```bash
$ mvn jshell:run
[INFO] Scanning for projects...
[INFO] 
[INFO] --------------------< org.buildozers:mvn-java-21 >--------------------
[INFO] Building mvn-java-21 1.0.0-SNAPSHOT
[INFO]   from pom.xml
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- jshell:1.4:run (default-cli) @ mvn-java-21 ---
|  Welcome to JShell -- Version 21.0.7
|  For an introduction type: /help intro

jshell> 
```

## 📖 Overview

This project serves as both a **template project** and an **archetype generator** for Java 21 applications. It includes:

- ☕ **Java 21** support with modern language features
- 🏗️ **Maven** build configuration optimized for Java 21
- 🧪 **JUnit 5** testing framework
- 📦 **Lombok** for reducing boilerplate code
- 🔧 **Commons Collections** utilities
- 🚀 **Automated archetype generation** and installation
- 📜 **Convenient bash scripts** for project management

## 🏗️ Project Structure

```
mvn-java-21/
├── 📁 bin/                                    # Utility scripts
│   ├── 🔧 build-and-install-archetype.sh    # Build & install archetype
│   └── 🚀 generate.sh                       # Generate new projects
├── 📁 src/
│   ├── 📁 main/
│   │   ├── 📁 java/
│   │   │   └── fr/fxjavadevblog/mvnjava21/
│   │   │       └── MainProg.java           # Main application class
│   │   └── 📁 jshell/
│   │       └── init.jshell                 # JShell initialization
│   └── 📁 test/
│       └── 📁 java/
│           └── fr/fxjavadevblog/mvnjava21/
│               └── MainProgTest.java       # Test class
├── 📄 pom.xml                              # Maven configuration
├── 📄 lombok.config                        # Lombok configuration
└── 📄 README.md                           # This file
```

## 🚀 Quick Start

### Prerequisites

- ☕ **Java 21** or higher
- 🏗️ **Maven 3.8+**
- 🐧 **Linux/Unix** environment (bash shell)

### 1️⃣ Clone and Build

```bash
# Clone the repository
git clone <repository-url>
cd mvn-java-21

# Build the project
mvn clean compile

# Run tests
mvn test

# Run the main application
mvn exec:java -Dexec.mainClass="org.buildozers.mvnjava21.MainProg"

# Run the Eclipse Collections showcase (colorful examples!)
mvn exec:java -Dexec.mainClass="org.buildozers.mvnjava21.examples.EclipseCollectionsShowcase"
```

#### 🎨 Eclipse Collections Showcase

The project includes a comprehensive demonstration showcasing:

- 📋 **Lists**: Product filtering, transformations, inventory calculations
- 🗺️ **Maps**: Customer management, city grouping, premium filtering  
- 🎯 **Sets**: Feature comparisons, unions, intersections, differences
- 🎪 **Advanced Operations**: Number partitioning, statistics, transformations
- 🌈 **Visual Output**: Colorful console display using Jansi
- 🏗️ **Lombok Integration**: Builder pattern and data classes

### 2️⃣ Create Archetype

Transform this project into a reusable Maven archetype:

```bash
# Build and install the archetype to local repository
./bin/build-and-install-archetype.sh

# Or with options
./bin/build-and-install-archetype.sh --clean --verbose
```

This creates an archetype: `org.buildozers:mvn-java-21-archetype:1.0-SNAPSHOT`

### 3️⃣ Generate New Projects

Once the archetype is installed, generate new projects:

```bash
# Using the convenience script
./bin/generate.sh com.example my-awesome-app 1.0.0

# Or directly with Maven
mvn archetype:generate \
    -DarchetypeGroupId=org.buildozers \
    -DarchetypeArtifactId=mvn-java-21-archetype \
    -DarchetypeVersion=1.0-SNAPSHOT \
    -DgroupId=com.example \
    -DartifactId=my-project \
    -Dversion=1.0.0 \
    -DinteractiveMode=false
```

## 🔧 Available Scripts

### 🏗️ `build-and-install-archetype.sh`

Builds the current project as a Maven archetype and installs it to your local repository.

```bash
# Show help
./bin/build-and-install-archetype.sh --help

# Basic usage
./bin/build-and-install-archetype.sh

# Advanced options
./bin/build-and-install-archetype.sh --clean --verbose --skip-tests
```

**Options:**
- `--clean` / `-c` - Clean project before building
- `--verbose` / `-v` - Enable detailed Maven output
- `--skip-tests` / `-s` - Skip running tests
- `--help` / `-h` - Show detailed help

### 🚀 `generate.sh`

Generates new Maven projects using the installed archetype.

```bash
# Show help
./bin/generate.sh --help

# Generate a project
./bin/generate.sh <base-package> <project-name> <version>

# Examples
./bin/generate.sh com.mycompany my-service 1.0.0
./bin/generate.sh org.example hello-world 0.1.0-SNAPSHOT
```

**Features:**
- ✅ **Archetype verification** before generation
- 🔍 **Input validation** with helpful error messages
- 🎨 **Rich visual feedback** with UTF-8 icons
- 📖 **Comprehensive help** system

## 🧪 Development Workflow

### Building and Testing

```bash
# Clean build
mvn clean compile

# Run all tests
mvn test

# Generate test reports
mvn surefire-report:report

# Run with different profiles
mvn clean test -Pdev
```

### Using JShell

The project includes JShell integration for interactive development:

```bash
# Start JShell with project classpath
mvn jshell:run

# Or manually
jshell --class-path target/classes:$(mvn dependency:build-classpath -Dmdep.outputFile=/dev/stdout -q)
```

### Lombok Integration

The project uses Lombok for reducing boilerplate code:

```java
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyClass {
    private String name;
    private int value;
}
```

## 📦 Dependencies

### Core Dependencies
- **Java 21** - Modern Java features and performance
- **Apache Commons Collections 4** - Enhanced collection utilities
- **Eclipse Collections 13.0.0** - High-performance collections with functional programming APIs
- **Lombok** - Boilerplate code reduction
- **Jansi** - Console colors and ANSI escape sequences

### Test Dependencies
- **JUnit 5** - Modern testing framework
- **AssertJ** - Fluent assertion library

### Build Plugins
- **Maven Compiler Plugin 3.13.0** - Java 21 compilation
- **Maven Surefire Plugin 3.2.5** - Test execution
- **JShell Maven Plugin** - Interactive development

## 🎯 Archetype Details

When you build the archetype, it creates:

**Archetype Information:**
```xml
<groupId>org.buildozers</groupId>
<artifactId>mvn-java-21-archetype</artifactId>
<version>1.0-SNAPSHOT</version>
```

**Generated Projects Include:**
- ☕ Java 21 configuration
- 🏗️ Optimized Maven setup
- 🧪 JUnit 5 test structure
- 📦 Lombok integration
- 🔧 Development utilities
- 📜 Shell scripts for common tasks

## 🌟 Features

### Java 21 Optimizations
- **Modern syntax** support (pattern matching, records, etc.)
- **Performance improvements** from latest JVM
- **Enhanced APIs** and standard library features

### Developer Experience
- 🎨 **Rich CLI interfaces** with UTF-8 icons
- 📖 **Comprehensive documentation** and help systems
- 🔍 **Input validation** and error handling
- ⚡ **Fast feedback** loops

### Best Practices
- 📋 **Structured project layout**
- 🧪 **Test-driven development** setup
- 🔧 **Configuration management**
- 📦 **Dependency management**

## 💡 Usage Examples

### Creating a Microservice

```bash
# Generate microservice project
./bin/generate.sh com.company user-service 1.0.0

cd user-service

# Build and test
mvn clean test

# Run the service
mvn exec:java
```

### Creating a CLI Application

```bash
# Generate CLI project
./bin/generate.sh org.tools my-cli-tool 0.1.0

cd my-cli-tool

# Add CLI dependencies to pom.xml
# Implement your CLI logic
# Build executable JAR
mvn clean package
```

### Library Development

```bash
# Generate library project
./bin/generate.sh com.mylib awesome-utils 1.0.0-SNAPSHOT

cd awesome-utils

# Develop your library
# Write comprehensive tests
# Publish to repository
mvn clean deploy
```

## 🔧 Customization

### Modifying the Template

1. Update the source project as needed
2. Rebuild the archetype:
   ```bash
   ./bin/build-and-install-archetype.sh --clean
   ```
3. Generate new projects with updated template

### Adding Dependencies

Edit `pom.xml` and rebuild the archetype:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter</artifactId>
    <version>3.1.0</version>
</dependency>
```

### Script Customization

Both scripts support customization:
- Modify constants at the top of each script
- Add new command-line options
- Extend functionality as needed

## 🐛 Troubleshooting

### Common Issues

**Archetype not found:**
```bash
# Verify installation
mvn dependency:get -DgroupId=org.buildozers \
    -DartifactId=mvn-java-21-archetype \
    -Dversion=1.0-SNAPSHOT
```

**Java version issues:**
```bash
# Check Java version
java --version
mvn --version

# Ensure JAVA_HOME is set correctly
echo $JAVA_HOME
```

**Permission issues:**
```bash
# Make scripts executable
chmod +x bin/*.sh
```

### Getting Help

- 📖 Use `--help` flag on any script
- 🔍 Check Maven output for detailed error messages
- 🧪 Run with `--verbose` for debugging information

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Update documentation
6. Submit a pull request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- ☕ **OpenJDK** team for Java 21
- 🏗️ **Apache Maven** project
- 📦 **Project Lombok** team
- 🧪 **JUnit** team
- 🔧 **Apache Commons** project

---

**Happy coding with Java 21! 🎉**
