# Advanced Abstraction Data Files

This directory contains real data files used by the MonolithicDataProcessor to demonstrate the problems with monolithic design.

## Data Files

### data.csv
- CSV format with headers: id, value, region, category
- 10 data records with sample sales data
- Used to demonstrate CSV parsing and processing

### data.json
- JSON format with an array of data objects
- Same 10 records as CSV but in JSON structure
- Used to demonstrate JSON parsing and processing
- Now validated against JSON Schema (data-schema.json) using Everit library

### data.xml
- XML format with nested record elements
- Same 10 records as CSV/JSON but in XML structure
- Used to demonstrate XML parsing and processing
- **Now includes namespace and validated against XSD schema**

### data.xsd
- XML Schema Definition file for validating XML data
- Enforces data types, structure, and enumerated values
- Validates regions (North, South, East, West) and categories (Electronics, Clothing, Books)
- Demonstrates proper XML validation using XSD instead of simple string checks

### data-schema.json
- JSON Schema Definition file for validating JSON data (JSON Schema Draft 07)
- Enforces same data types, structure, and enumerated values as XSD
- Validates regions (North, South, East, West) and categories (Electronics, Clothing, Books)
- Uses Everit JSON Schema library for comprehensive validation
- Demonstrates professional JSON validation comparable to XML XSD approach

## Data Structure

Each record contains:
- **id**: Unique identifier (1-10)
- **value**: Numeric value (sales amount)
- **region**: Geographic region (North, South, East, West)
- **category**: Product category (Electronics, Clothing, Books)

## Usage

The MonolithicDataProcessor loads these files from the classpath using the ClassLoader:
- Files are located in `src/main/resources/org/buildozers/dojo/abstraction/advanced/`
- Loaded as resources to simulate real-world file processing
- Demonstrates the complexity and problems of monolithic data processing

## Problems Demonstrated

The monolithic approach shows:
1. Mixed concerns (reading, validation, filtering, aggregation)
2. Code duplication across different file formats
3. Difficulty in testing individual components
4. Tight coupling between file format and processing logic
5. Violation of Single Responsibility Principle

This serves as the "before" state for refactoring exercises using advanced abstraction patterns.
