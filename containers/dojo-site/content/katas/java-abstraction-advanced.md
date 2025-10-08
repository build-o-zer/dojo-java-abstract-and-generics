---
title: "Advanced Abstraction Patterns - Refactoring the Monolith"
subject: "Refactoring Monolithic Code using Advanced Abstraction Patterns"
goal: "Transform a complex monolithic class into a clean, abstracted architecture"
level: "Advanced"
duration: "45-60 minutes"
category: "Abstraction"
difficulty: "advanced"
concepts:
  - "Monolithic refactoring"
  - "Advanced abstraction patterns"
  - "Strategy pattern"
  - "Template method pattern"
  - "Factory pattern"
prerequisites:
  - "Abstract classes mastery"
  - "Design patterns knowledge"
  - "Complex refactoring experience"
estimated_time: 60
ninja_belt: "red"
---


## 道場での対話 (Dojo Dialogue)

### Scene: The Legacy Code Challenge

*The deshi stares at a massive Java file on their screen, overwhelmed by its complexity. The sensei approaches, having witnessed this struggle countless times.*

**Sensei:** "I see the weight of legacy code upon your shoulders, advanced deshi. What troubles you?"

**Deshi:** "Sensei, I have inherited this `MonolithicDataProcessor` class. It processes CSV, JSON, and XML data with validation, filtering, and aggregation. But it's over 400 lines of tangled logic! Every small change risks breaking something else, and adding new features is becoming impossible."

**Sensei:** "Ah, the classic monolith - where all concerns live as one. This is not code, but chaos. However, within this chaos lies an opportunity to practice the highest forms of abstraction mastery. Come, let me show you the path from monolith to mastery."

---

## The Teaching

**Sensei:** "First, observe the monolithic beast you must tame. Look at the current `MonolithicDataProcessor`:"

```java
public class MonolithicDataProcessor {
    static final String XML_NAMESPACE = "http://buildozers.org/dojo/data";
    
    public void processFileData(String filename, String format, boolean validate, 
                               String categoryFilter, String aggregationType) {
        // 450+ lines of deeply mixed concerns:
        
        if (format.equals("CSV")) {
            // Apache Commons CSV parsing with temporary data structures
            String csvData = loadTextFile(filename);
            CSVFormat csvFormat = CSVFormat.DEFAULT.builder().setHeader().setSkipHeaderRecord(true).build();
            CSVParser parser = CSVParser.parse(csvData, csvFormat);
            List<CSVRecord> filteredRecords = new ArrayList<>();  // Temporary storage
            // Validation, filtering with temp storage, aggregation all mixed in...
        } else if (format.equals("JSON")) {
            // org.json parsing with JSONObject, JSONArray, and Everit JSON Schema validation
            JSONObject jsonObject = new JSONObject(jsonData);
            if (validate) validateJsonAgainstSchema(jsonObject);  // JSON Schema validation
            List<JSONObject> filteredJsonRecords = new ArrayList<>();  // Temporary storage
            // More mixed validation, filtering, aggregation...
        } else if (format.equals("XML")) {
            // DOM parsing with XSD validation and temporary data structures
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            if (validate) validateXmlAgainstXsd(xmlData);  // XSD schema validation
            List<Element> filteredXmlElements = new ArrayList<>();  // Temporary storage
            // Even more tangled logic...
        }
        
        // Enhanced features now include:
        // - Apache Commons IO for unified file loading (loadTextFile method)
        // - JSON Schema validation using Everit library
        // - XSD validation with security (XXE prevention)
        // - Temporary data structures for filtered results before aggregation
        // - Consistent namespace handling via XML_NAMESPACE constant
    }
    
    private String loadTextFile(String filename) throws IOException { /*...*/ }
    private boolean validateJsonAgainstSchema(JSONObject jsonObject) { /*...*/ }
    private boolean validateXmlAgainstXsd(String xmlContent) { /*...*/ }
}
```

**Deshi:** "Sensei, this is exactly my problem! I count three different parsing libraries, duplicate validation logic, inconsistent error handling, and the method has grown to over 400 lines. How can I possibly add new features or formats?"

**Sensei:** "You have identified the symptoms of the monolithic disease. But within this chaos lies the wisdom to build something beautiful. We shall apply advanced abstraction patterns to separate these tangled concerns into elegant, reusable components."

### The Monolithic Problem Visualization

*Sensei sketches the current chaotic architecture:*

```mermaid
classDiagram
    class MonolithicDataProcessor {
        <<monolith>>
        +String XML_NAMESPACE$
        +processFileData(filename, format, validate, categoryFilter, aggregationType) void
        -loadTextFile(filename) String
        -validateJsonAgainstSchema(jsonObject) boolean
        -validateXmlAgainstXsd(xmlContent) boolean
        -parseCSV() List~CSVRecord~
        -parseJSON() JSONObject
        -parseXML() Document
        -validateCSV() boolean
        -validateJSON() boolean
        -validateXML() boolean
        -filterCSV() List~CSVRecord~
        -filterJSON() List~JSONObject~
        -filterXML() List~Element~
        -aggregateCSV() Map
        -aggregateJSON() Map
        -aggregateXML() Map
    }
    
    class ApacheCommonsCSV {
        <<external-library>>
        +CSVParser
        +CSVRecord
        +CSVFormat
    }
    
    class OrgJSON {
        <<external-library>>
        +JSONObject
        +JSONArray
    }
    
    class EveritJSONSchema {
        <<external-library>>
        +Schema
        +ValidationException
    }
    
    class DOMParsing {
        <<external-library>>
        +DocumentBuilder
        +Document
        +Element
    }
    
    class XSDValidation {
        <<external-library>>
        +SchemaFactory
        +Validator
    }
    
    MonolithicDataProcessor ..> ApacheCommonsCSV : uses
    MonolithicDataProcessor ..> OrgJSON : uses
    MonolithicDataProcessor ..> EveritJSONSchema : uses
    MonolithicDataProcessor ..> DOMParsing : uses
    MonolithicDataProcessor ..> XSDValidation : uses
    
    note for MonolithicDataProcessor "🔥 PROBLEMS:\n• 450+ lines of mixed concerns\n• Three different parsing technologies\n• Duplicate validation patterns\n• Inconsistent error handling\n• Impossible to test in isolation\n• Resource management issues\n• Tightly coupled to specific libraries"
    
    note for ApacheCommonsCSV "🔴 CSV Processing\n• Temporary List~CSVRecord~\n• Custom validation logic\n• Manual field extraction"
    
    note for OrgJSON "🔴 JSON Processing\n• Schema validation with Everit\n• Temporary List~JSONObject~\n• Different error handling"
    
    note for DOMParsing "🔴 XML Processing\n• XSD validation\n• XXE security handling\n• Temporary List~Element~\n• Namespace management"
```

**Deshi:** "The diagram reveals the true scope of the problem! The monolith is not just large - it's a tangled web of dependencies and responsibilities."

**Sensei:** "Indeed. Each format brings its own libraries, its own validation approach, its own temporary storage patterns. This is not architecture - this is archaeology! But from this ancient code, we shall build something elegant."

---

## The Practice (実践)

**Sensei:** "We shall build a flexible pipeline using several abstraction patterns combined."

### Target Architecture - Clean Abstraction Patterns

*Sensei reveals the elegant solution architecture:*

```mermaid
classDiagram
    class DataSource~T~ {
        <<abstract>>
        #String identifier
        #Map~String, Object~ configuration
        +DataSource(identifier)
        +loadData()* List~T~
        +isHealthy()* boolean
        +configure(key, value) void
        #logLoad(recordCount) void
        #getSourceType()* String
    }
    
    class DataTransformer~TInput, TOutput~ {
        <<abstract>>
        #String name
        #boolean enabled
        +DataTransformer(name)
        +transform(data) List~TOutput~ ⭐final
        #performTransformation(data)* List~TOutput~
        #getTransformationType()* String
        -logStart(inputSize) void
        -logComplete(outputSize, duration) void
        +setEnabled(enabled) void
    }
    
    class ProcessingPipeline~TInput, TOutput~ {
        <<abstract>>
        #List~DataTransformer~ transformers
        #DataSource~TInput~ dataSource
        #String pipelineName
        +ProcessingPipeline(pipelineName, dataSource)
        +execute() List~TOutput~ ⭐final
        #processData(rawData)* List~TOutput~
        #handleError(exception)* void
        #cleanup()* void
        #addTransformer(transformer) void
    }
    
    class CSVDataSource {
        -CSVFormat csvFormat
        +loadData() List~CSVRecord~
        +isHealthy() boolean
        #getSourceType() String
    }
    
    class JSONDataSource {
        -JSONObject rootObject
        +loadData() List~JSONObject~
        +isHealthy() boolean
        #getSourceType() String
    }
    
    class XMLDataSource {
        -DocumentBuilder builder
        +loadData() List~Element~
        +isHealthy() boolean
        #getSourceType() String
    }
    
    class ValidationTransformer~T~ {
        -Schema validationSchema
        #performTransformation(data) List~T~
        #getTransformationType() String
    }
    
    class FilterTransformer~T~ {
        -Predicate~T~ filterCriteria
        #performTransformation(data) List~T~
        #getTransformationType() String
    }
    
    class AggregationTransformer~T~ {
        -AggregationType type
        #performTransformation(data) List~T~
        #getTransformationType() String
    }
    
    class DataProcessingPipeline {
        +DataProcessingPipeline(name, dataSource)
        #processData(rawData) List~ProcessedRecord~
        #handleError(exception) void
        #cleanup() void
    }
    
    DataSource~T~ <|-- CSVDataSource : extends
    DataSource~T~ <|-- JSONDataSource : extends
    DataSource~T~ <|-- XMLDataSource : extends
    
    DataTransformer~TInput, TOutput~ <|-- ValidationTransformer~T~ : extends
    DataTransformer~TInput, TOutput~ <|-- FilterTransformer~T~ : extends
    DataTransformer~TInput, TOutput~ <|-- AggregationTransformer~T~ : extends
    
    ProcessingPipeline~TInput, TOutput~ <|-- DataProcessingPipeline : extends
    
    ProcessingPipeline~TInput, TOutput~ o-- DataSource~T~ : uses
    ProcessingPipeline~TInput, TOutput~ o-- DataTransformer~TInput, TOutput~ : uses
    
    note for DataSource~T~ "🟢 BENEFITS:\n• Generic type safety\n• Pluggable data sources\n• Consistent interface\n• Health monitoring\n• Configuration support"
    
    note for DataTransformer~TInput, TOutput~ "✨ TEMPLATE METHOD:\n• ⭐ Final workflow method\n• Built-in logging & timing\n• Enable/disable capability\n• Type-safe transformations\n• Single responsibility"
    
    note for ProcessingPipeline~TInput, TOutput~ "🚀 PIPELINE PATTERN:\n• ⭐ Final execution flow\n• Error handling strategy\n• Resource cleanup\n• Transformer composition\n• Exception safety"
```

**Deshi:** "Sensei! The transformation is remarkable! The monolithic chaos has become an elegant symphony of abstractions. Each class has a single, clear purpose."

**Sensei:** "Observe how generics provide type safety without sacrificing flexibility. The `DataSource<T>` can work with any data type, while each concrete implementation specifies exactly what it produces. The Template Method pattern ensures consistent behavior across all transformers."

### Pattern 1: Abstract Data Source

```java
public abstract class DataSource<T> {
    protected String identifier;
    protected Map<String, Object> configuration;
    
    public DataSource(String identifier) {
        this.identifier = identifier;
        this.configuration = new HashMap<>();
    }
    
    public abstract List<T> loadData() throws DataSourceException;
    public abstract boolean isHealthy();
    public abstract void configure(String key, Object value);
    
    protected void logLoad(int recordCount) {
        System.out.println("[" + getSourceType() + "] Loaded " + recordCount + " records from: " + identifier);
    }
    
    protected abstract String getSourceType();
}
```

<details>
<summary><button>🧙‍♂️ Advice from Sensei</button></summary>

**Sensei explains:** "Notice how we use generics with abstract classes. This allows type safety while maintaining abstraction. The `DataSource<T>` can work with any data type, but each concrete implementation can specify its own type."

</details>

### Pattern 2: Abstract Data Transformer

```java
public abstract class DataTransformer<TInput, TOutput> {
    protected String name;
    protected boolean enabled;
    
    public DataTransformer(String name) {
        this.name = name;
        this.enabled = true;
    }
    
    public final List<TOutput> transform(List<TInput> data) {
        if (!enabled) {
            return Collections.emptyList();
        }
        
        logStart(data.size());
        long startTime = System.currentTimeMillis();
        
        List<TOutput> result = performTransformation(data);
        
        long duration = System.currentTimeMillis() - startTime;
        logComplete(result.size(), duration);
        
        return result;
    }
    
    protected abstract List<TOutput> performTransformation(List<TInput> data);
    protected abstract String getTransformationType();
    
    private void logStart(int inputSize) {
        System.out.println("[" + getTransformationType() + "] Starting transformation '" + name + "' on " + inputSize + " records");
    }
    
    private void logComplete(int outputSize, long duration) {
        System.out.println("[" + getTransformationType() + "] Completed '" + name + "' - Output: " + outputSize + " records (" + duration + "ms)");
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
```

<details>
<summary><button>🧙‍♂️ Advice from Sensei</button></summary>

**Sensei advises:** "The Template Method pattern shines here. The `transform()` method defines the common workflow (logging, timing) while allowing subclasses to focus only on the actual transformation logic. This separation of concerns is crucial in complex systems."

</details>

### Pattern 3: Processing Pipeline

```java
public abstract class ProcessingPipeline<TInput, TOutput> {
    protected List<DataTransformer<?, ?>> transformers;
    protected DataSource<TInput> dataSource;
    protected String pipelineName;
    
    public ProcessingPipeline(String pipelineName, DataSource<TInput> dataSource) {
        this.pipelineName = pipelineName;
        this.dataSource = dataSource;
        this.transformers = new ArrayList<>();
    }
    
    public final List<TOutput> execute() throws ProcessingException {
        System.out.println("=== Starting Pipeline: " + pipelineName + " ===");
        
        try {
            List<TInput> rawData = dataSource.loadData();
            return processData(rawData);
        } catch (Exception e) {
            handleError(e);
            throw new ProcessingException("Pipeline execution failed", e);
        } finally {
            cleanup();
        }
    }
    
    protected abstract List<TOutput> processData(List<TInput> rawData);
    protected abstract void handleError(Exception e);
    protected abstract void cleanup();
    
    protected void addTransformer(DataTransformer<?, ?> transformer) {
        transformers.add(transformer);
    }
}
```

### Pipeline Execution Flow

*Sensei demonstrates the elegant workflow orchestration:*

```mermaid
sequenceDiagram
    participant Client
    participant Pipeline as ProcessingPipeline
    participant DataSource as DataSource~T~
    participant Validator as ValidationTransformer
    participant Filter as FilterTransformer
    participant Aggregator as AggregationTransformer
    
    Client->>Pipeline: execute()
    Note over Pipeline: === Starting Pipeline ===
    
    Pipeline->>DataSource: loadData()
    DataSource-->>Pipeline: List~RawData~
    Note over DataSource: Health check & logging
    
    Pipeline->>Pipeline: processData(rawData)
    
    loop For each transformer
        Pipeline->>Validator: transform(data)
        Note over Validator: Template Method Pattern
        Validator->>Validator: logStart(size)
        Validator->>Validator: performTransformation(data)
        Note over Validator: Schema validation logic
        Validator->>Validator: logComplete(size, duration)
        Validator-->>Pipeline: List~ValidatedData~
        
        Pipeline->>Filter: transform(validatedData)
        Filter->>Filter: logStart(size)
        Filter->>Filter: performTransformation(data)
        Note over Filter: Category/region filtering
        Filter->>Filter: logComplete(size, duration)
        Filter-->>Pipeline: List~FilteredData~
        
        Pipeline->>Aggregator: transform(filteredData)
        Aggregator->>Aggregator: logStart(size)
        Aggregator->>Aggregator: performTransformation(data)
        Note over Aggregator: SUM/COUNT aggregation
        Aggregator->>Aggregator: logComplete(size, duration)
        Aggregator-->>Pipeline: List~AggregatedData~
    end
    
    Pipeline->>Pipeline: cleanup()
    Note over Pipeline: Resource cleanup
    
    Pipeline-->>Client: List~ProcessedResult~
    
    Note over Client, Aggregator: 🟢 Benefits:<br/>• Type safety throughout<br/>• Consistent logging<br/>• Error handling<br/>• Resource management<br/>• Pluggable components
```

**Deshi:** "The sequence diagram reveals the beauty of the Template Method pattern! Each transformer follows the same workflow, but implements its own specific logic."

**Sensei:** "Yes, and notice how type safety flows through the entire pipeline. The generic parameters ensure that data transformations are validated at compile time, preventing runtime type errors that plagued the monolithic version."

---

## Common Pitfalls (落とし穴)

**Sensei warns:** "Advanced abstraction brings power, but also responsibility. Avoid these traps:"

1. **Over-engineering simple solutions**
   ```java
   // Don't create complex abstractions for simple, one-off tasks
   // Save abstraction for reusable, complex scenarios
   ```

2. **Generic type erasure confusion**
   ```java
   // Be careful with generic inheritance
   public class BadProcessor extends DataTransformer<Object, Object> {
       // Type safety is lost!
   }
   ```

3. **Abstract dependency management**
   ```java
   // Don't let abstract classes become tightly coupled
   // Use dependency injection or factory patterns
   ```

---

## The Challenge (挑戦)

**Sensei:** "Now, ultimate deshi, demonstrate mastery by refactoring the `MonolithicDataProcessor` into a clean, abstracted architecture."

### Transformation Overview - Before vs After

*Sensei presents the complete transformation journey:*

#### 🔥 BEFORE: Monolithic Chaos

```mermaid
graph TD
    Monolith[MonolithicDataProcessor<br/>450+ lines of tangled logic]
    
    Monolith --> CSV_Code[CSV Processing<br/>- Apache Commons CSV<br/>- Manual validation<br/>- Temp List~CSVRecord~ storage]
    Monolith --> JSON_Code[JSON Processing<br/>- org.json + Everit Schema<br/>- Different validation approach<br/>- Temp List~JSONObject~ storage]
    Monolith --> XML_Code[XML Processing<br/>- DOM + XSD validation<br/>- XXE security handling<br/>- Temp List~Element~ storage]
    
    CSV_Code --> Problems[❌ CRITICAL PROBLEMS:<br/>• Massive code duplication<br/>• Mixed concerns everywhere<br/>• Impossible to test in isolation<br/>• Tight coupling to libraries<br/>• Memory waste with temp storage<br/>• Inconsistent error handling]
    JSON_Code --> Problems
    XML_Code --> Problems
    
    Problems --> Impact[💥 IMPACT:<br/>• Every change risks breaking everything<br/>• Adding new formats requires<br/>  modifying the core method<br/>• Bug fixes multiply across formats<br/>• Testing requires full integration<br/>• Performance degrades over time]
    
    style Monolith fill:#ff6b6b,stroke:#d63031,stroke-width:4px
    style Problems fill:#ff7675,stroke:#d63031,stroke-width:2px
    style Impact fill:#fd79a8,stroke:#e84393,stroke-width:2px
    style CSV_Code fill:#fab1a0,stroke:#e17055
    style JSON_Code fill:#fab1a0,stroke:#e17055
    style XML_Code fill:#fab1a0,stroke:#e17055
```

#### ✨ AFTER: Clean Architecture with Abstraction Patterns

```mermaid
graph TD
    Pipeline[ProcessingPipeline~TInput,TOutput~<br/>🎯 Template Method Pattern<br/>⭐ Orchestrates entire workflow]
    
    subgraph "🔌 Pluggable Data Sources"
        CSV_DS[CSVDataSource<br/>📄 Apache Commons CSV<br/>✅ Health monitoring<br/>⚙️ Configurable format]
        JSON_DS[JSONDataSource<br/>📋 org.json parsing<br/>✅ Schema validation<br/>⚙️ Flexible structure]
        XML_DS[XMLDataSource<br/>📰 DOM + XSD validation<br/>✅ XXE protection<br/>⚙️ Namespace support]
    end
    
    subgraph "🔄 Composable Transformers"
        Validator[ValidationTransformer~T~<br/>🛡️ Schema-based validation<br/>📊 Built-in logging & timing<br/>🔧 Enable/disable capability]
        Filter[FilterTransformer~T~<br/>🎯 Predicate-based filtering<br/>📊 Performance metrics<br/>🔧 Chainable operations]
        Aggregator[AggregationTransformer~T~<br/>📈 SUM/COUNT/AVG operations<br/>📊 Group-by support<br/>🔧 Type-safe aggregation]
    end
    
    Pipeline --> CSV_DS
    Pipeline --> JSON_DS
    Pipeline --> XML_DS
    Pipeline --> Validator
    Pipeline --> Filter
    Pipeline --> Aggregator
    
    CSV_DS --> Benefits[✅ MAJOR BENEFITS:<br/>• Single Responsibility Principle<br/>• Compile-time type safety<br/>• Independent unit testing<br/>• Pluggable components<br/>• Efficient resource usage<br/>• Consistent error handling<br/>• Easy to extend & maintain]
    JSON_DS --> Benefits
    XML_DS --> Benefits
    Validator --> Benefits
    Filter --> Benefits
    Aggregator --> Benefits
    
    Benefits --> NewCapabilities[🚀 NEW CAPABILITIES:<br/>• Add formats without touching core<br/>• Mix & match transformers<br/>• Pipeline reusability<br/>• Parallel processing ready<br/>• Configuration-driven workflows<br/>• Comprehensive error recovery]
    
    style Pipeline fill:#00b894,stroke:#00a085,stroke-width:4px
    style Benefits fill:#55a3ff,stroke:#0984e3,stroke-width:2px
    style NewCapabilities fill:#6c5ce7,stroke:#5f3dc4,stroke-width:2px
    style CSV_DS fill:#a8e6cf,stroke:#00b894
    style JSON_DS fill:#a8e6cf,stroke:#00b894
    style XML_DS fill:#a8e6cf,stroke:#00b894
    style Validator fill:#ffd3a5,stroke:#fdcb6e
    style Filter fill:#ffd3a5,stroke:#fdcb6e
    style Aggregator fill:#ffd3a5,stroke:#fdcb6e
```

**Deshi:** "The visual transformation is striking! The monolithic tangle becomes a clean, organized architecture where each component has a clear purpose."

**Sensei:** "Indeed. Notice how the abstract patterns eliminate duplication while maintaining flexibility. Each data source focuses only on loading its specific format, each transformer does one thing well, and the pipeline orchestrates it all with consistent error handling and resource management."

### Current State Analysis

The existing `MonolithicDataProcessor` class demonstrates multiple anti-patterns:

```java
// Current problems in MonolithicDataProcessor:
public class MonolithicDataProcessor {
    // 🔥 PROBLEMS IDENTIFIED:
    
    // 1. Mixed Data Parsing Technologies
    - CSV: Apache Commons CSV (CSVParser, CSVRecord) with temporary List<CSVRecord>
    - JSON: org.json library (JSONObject, JSONArray) + Everit JSON Schema validation with List<JSONObject>
    - XML: DOM parsing with XSD validation + XXE security + List<Element> temporary storage
    
    // 2. Enhanced but Still Duplicate Logic Patterns
    - Validation: CSV column checks, JSON Schema (Everit), XSD validation (secure)
    - Filtering: Three different approaches with temporary data structures
    - Aggregation: (SUM, COUNT) now operates on filtered temporary collections
    - File Loading: Unified loadTextFile() method using Apache Commons IO
    
    // 3. Monolithic Method (450+ lines - even worse!)
    - processFileData() handles EVERYTHING including temp storage management
    - validateJsonAgainstSchema() and validateXmlAgainstXsd() helper methods embedded
    - Impossible to test individual components in isolation
    - Adding new formats requires modifying core method and adding more validation helpers
    
    // 4. Inconsistent Error Handling (now with more exception types)
    - IOException, JSONException, SAXException, ValidationException all handled differently
    - No unified error recovery strategy across different validation approaches
    
    // 5. Resource Management Issues (partially improved but still problematic)
    - Temporary data structures created but not reusable across operations
    - Memory inefficient: stores filtered results before aggregation
    - Schema files loaded on every validation call
    - No connection pooling for future database sources
    
    // 6. NEW ISSUES FROM ENHANCEMENTS:
    - XML_NAMESPACE constant reduces duplication but shows more hardcoded dependencies
    - Three different validation libraries (CSV validation, Everit JSON Schema, XSD)
    - Temporary storage pattern repeated but not abstracted
    - Security improvements (XXE prevention) embedded in monolithic validation method
}
```

### Current Data Files

The `MonolithicDataProcessor` works with three data files (located in `src/main/resources/org/buildozers/dojo/abstraction/advanced/`):

**data.csv**: 10 records with id, value, region, category
```csv
id,value,region,category
1,100,North,Electronics
2,150,South,Clothing
3,200,North,Electronics
4,75,East,Books
5,300,West,Electronics
...
```

**data.json**: Same data in JSON format
```json
{
  "data": [
    {"id": 1, "value": 100, "region": "North", "category": "Electronics"},
    {"id": 2, "value": 150, "region": "South", "category": "Clothing"},
    ...
  ]
}
```

**data-schema.json**: JSON Schema (Draft 07) for validation using Everit library
```json
{
  "$schema": "http://json-schema.org/draft-07/schema#",
  "type": "object",
  "properties": {
    "data": {
      "type": "array",
      "items": {
        "type": "object",
        "properties": {
          "id": {"type": "integer", "minimum": 1},
          "value": {"type": "integer", "minimum": 0},
          "region": {"type": "string", "enum": ["North", "South", "East", "West"]},
          "category": {"type": "string", "enum": ["Electronics", "Clothing", "Books"]}
        },
        "required": ["id", "value", "region", "category"]
      }
    }
  },
  "required": ["data"]
}
```

**data.xml**: Same data with XSD validation support and namespace
```xml
<data:root xmlns:data="http://buildozers.org/dojo/data">
  <data:record>
    <data:id>1</data:id>
    <data:value>100</data:value>
    <data:region>North</data:region>
    <data:category>Electronics</data:category>
  </data:record>
  ...
</data:root>
```

**data.xsd**: XML Schema Definition for secure validation
```xml
<?xml version="1.0" encoding="UTF-8"?>
<xs:schema xmlns:xs="http://www.w3.org/2001/XMLSchema"
           targetNamespace="http://buildozers.org/dojo/data"
           xmlns:data="http://buildozers.org/dojo/data">
  
  <xs:element name="root">
    <xs:complexType>
      <xs:sequence>
        <xs:element name="record" maxOccurs="unbounded">
          <xs:complexType>
            <xs:sequence>
              <xs:element name="id" type="xs:positiveInteger"/>
              <xs:element name="value" type="xs:nonNegativeInteger"/>
              <xs:element name="region" type="data:regionType"/>
              <xs:element name="category" type="data:categoryType"/>
            </xs:sequence>
          </xs:complexType>
        </xs:element>
      </xs:sequence>
    </xs:complexType>
  </xs:element>
  
  <xs:simpleType name="regionType">
    <xs:restriction base="xs:string">
      <xs:enumeration value="North"/>
      <xs:enumeration value="South"/>
      <xs:enumeration value="East"/>
      <xs:enumeration value="West"/>
    </xs:restriction>
  </xs:simpleType>
  
  <xs:simpleType name="categoryType">
    <xs:restriction base="xs:string">
      <xs:enumeration value="Electronics"/>
      <xs:enumeration value="Clothing"/>
      <xs:enumeration value="Books"/>
    </xs:restriction>
  </xs:simpleType>
</xs:schema>
```

### Your Mission

Refactor this monolith into a clean architecture that can:

1. **Support Current Features**: CSV, JSON, XML processing with validation, filtering, and aggregation
2. **Enable Easy Extension**: Add new data formats without changing existing code
3. **Separate Concerns**: Distinct components for parsing, validation, filtering, aggregation
4. **Maintain Functionality**: All current test cases must continue to pass
5. **Improve Maintainability**: Each component should be independently testable

### Expected Behavior to Preserve

- **Filtering with Temporary Storage**: Electronics (4 records), Clothing (3 records), Books (3 records)
  - Current implementation uses temporary data structures (`List<CSVRecord>`, `List<JSONObject>`, `List<Element>`)
  - Filtered results are stored before aggregation operations
- **SUM Aggregation**: Electronics=820, Clothing=455, Books=250, All=1525
- **COUNT Aggregation**: Electronics=4, Clothing=3, Books=3, All=10
- **Validation**: CSV column validation, JSON Schema validation (Everit library), XML XSD validation (with XXE security)
- **File Loading**: Unified loadTextFile() method using Apache Commons IO
- **Security**: XML parsing with external entity prevention (XXE protection)

<details>
<summary><button>🎯 Solution from Sensei - Step 1: Data Sources</button></summary>

```java
// DataRecord.java
package org.buildozers.dojo.abstraction;

public class DataRecord {
    private final Map<String, Object> fields;
    
    public DataRecord() {
        this.fields = new HashMap<>();
    }
    
    public void setField(String key, Object value) {
        fields.put(key, value);
    }
    
    public Object getField(String key) {
        return fields.get(key);
    }
    
    public String getString(String key) {
        Object value = fields.get(key);
        return value != null ? value.toString() : null;
    }
    
    public Integer getInteger(String key) {
        Object value = fields.get(key);
        if (value instanceof Integer) return (Integer) value;
        if (value instanceof String) {
            try { return Integer.parseInt((String) value); }
            catch (NumberFormatException e) { return null; }
        }
        return null;
    }
    
    public Set<String> getFieldNames() {
        return fields.keySet();
    }
    
    @Override
    public String toString() {
        return "DataRecord{" + fields + "}";
    }
}

// CsvDataSource.java
package org.buildozers.dojo.abstraction;

import java.io.*;
import java.util.*;

public class CsvDataSource extends DataSource<DataRecord> {
    private String[] headers;
    
    public CsvDataSource(String filename) {
        super(filename);
    }
    
    @Override
    public List<DataRecord> loadData() throws DataSourceException {
        List<DataRecord> records = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(identifier))) {
            String headerLine = reader.readLine();
            if (headerLine != null) {
                headers = headerLine.split(",");
            }
            
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                DataRecord record = new DataRecord();
                
                for (int i = 0; i < headers.length && i < values.length; i++) {
                    record.setField(headers[i].trim(), values[i].trim());
                }
                records.add(record);
            }
            
            logLoad(records.size());
            return records;
            
        } catch (IOException e) {
            throw new DataSourceException("Failed to load CSV data", e);
        }
    }
    
    @Override
    protected String getSourceType() {
        return "CSV";
    }
    
    @Override
    public boolean isHealthy() {
        return new File(identifier).exists();
    }
    
    @Override
    public void configure(String key, Object value) {
        configuration.put(key, value);
    }
}
```

</details>

<details>
<summary><button>🎯 Solution from Sensei - Step 2: Transformers</button></summary>

```java
// ValidationTransformer.java
package org.buildozers.dojo.abstraction;

import java.util.*;
import java.util.function.Predicate;

public class ValidationTransformer extends DataTransformer<DataRecord, DataRecord> {
    private Map<String, Predicate<Object>> validationRules;
    
    public ValidationTransformer(String name) {
        super(name);
        this.validationRules = new HashMap<>();
    }
    
    public void addRule(String fieldName, Predicate<Object> rule) {
        validationRules.put(fieldName, rule);
    }
    
    @Override
    protected List<DataRecord> performTransformation(List<DataRecord> data) {
        List<DataRecord> validRecords = new ArrayList<>();
        
        for (DataRecord record : data) {
            if (isValid(record)) {
                validRecords.add(record);
            }
        }
        
        return validRecords;
    }
    
    private boolean isValid(DataRecord record) {
        for (Map.Entry<String, Predicate<Object>> rule : validationRules.entrySet()) {
            String fieldName = rule.getKey();
            Predicate<Object> validator = rule.getValue();
            Object fieldValue = record.getField(fieldName);
            
            if (!validator.test(fieldValue)) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    protected String getTransformationType() {
        return "VALIDATION";
    }
}

// AggregationTransformer.java
package org.buildozers.dojo.abstraction;

import java.util.*;

public class AggregationTransformer extends DataTransformer<DataRecord, DataRecord> {
    private String groupByField;
    private String aggregateField;
    private AggregationType aggregationType;
    
    public enum AggregationType { SUM, COUNT, AVERAGE, MAX, MIN }
    
    public AggregationTransformer(String name, String groupByField, String aggregateField, AggregationType type) {
        super(name);
        this.groupByField = groupByField;
        this.aggregateField = aggregateField;
        this.aggregationType = type;
    }
    
    @Override
    protected List<DataRecord> performTransformation(List<DataRecord> data) {
        Map<String, List<DataRecord>> groups = new HashMap<>();
        
        // Group by field
        for (DataRecord record : data) {
            String groupKey = record.getString(groupByField);
            if (groupKey != null) {
                groups.computeIfAbsent(groupKey, k -> new ArrayList<>()).add(record);
            }
        }
        
        // Aggregate each group
        List<DataRecord> results = new ArrayList<>();
        for (Map.Entry<String, List<DataRecord>> group : groups.entrySet()) {
            DataRecord aggregatedRecord = aggregateGroup(group.getKey(), group.getValue());
            results.add(aggregatedRecord);
        }
        
        return results;
    }
    
    private DataRecord aggregateGroup(String groupKey, List<DataRecord> records) {
        DataRecord result = new DataRecord();
        result.setField(groupByField, groupKey);
        
        switch (aggregationType) {
            case COUNT:
                result.setField(aggregateField + "_count", records.size());
                break;
            case SUM:
                int sum = records.stream()
                    .mapToInt(r -> r.getInteger(aggregateField) != null ? r.getInteger(aggregateField) : 0)
                    .sum();
                result.setField(aggregateField + "_sum", sum);
                break;
            // Add other aggregation types as needed
        }
        
        return result;
    }
    
    @Override
    protected String getTransformationType() {
        return "AGGREGATION";
    }
}
```

</details>

<details>
<summary><button>🎯 Solution from Sensei - Step 3: Complete Pipeline</button></summary>

```java
// DataProcessingPipeline.java
package org.buildozers.dojo.abstraction;

import java.util.*;

public class DataProcessingPipeline extends ProcessingPipeline<DataRecord, DataRecord> {
    
    public DataProcessingPipeline(String name, DataSource<DataRecord> dataSource) {
        super(name, dataSource);
    }
    
    @Override
    @SuppressWarnings("unchecked")
    protected List<DataRecord> processData(List<DataRecord> rawData) {
        List<DataRecord> currentData = rawData;
        
        for (DataTransformer<?, ?> transformer : transformers) {
            if (transformer instanceof DataTransformer<DataRecord, DataRecord>) {
                DataTransformer<DataRecord, DataRecord> typedTransformer = 
                    (DataTransformer<DataRecord, DataRecord>) transformer;
                currentData = typedTransformer.transform(currentData);
            }
        }
        
        return currentData;
    }
    
    @Override
    protected void handleError(Exception e) {
        System.err.println("Pipeline '" + pipelineName + "' failed: " + e.getMessage());
        e.printStackTrace();
    }
    
    @Override
    protected void cleanup() {
        System.out.println("=== Pipeline '" + pipelineName + "' completed ===\n");
    }
    
    public DataProcessingPipeline addValidation(String name) {
        ValidationTransformer validator = new ValidationTransformer(name);
        // Add some default validation rules
        validator.addRule("id", value -> value != null && !value.toString().isEmpty());
        addTransformer(validator);
        return this;
    }
    
    public DataProcessingPipeline addAggregation(String groupBy, String aggregateField, AggregationTransformer.AggregationType type) {
        AggregationTransformer aggregator = new AggregationTransformer(
            type.name().toLowerCase() + "_by_" + groupBy, groupBy, aggregateField, type);
        addTransformer(aggregator);
        return this;
    }
}

// Custom exceptions
package org.buildozers.dojo.abstraction;

public class DataSourceException extends Exception {
    public DataSourceException(String message, Throwable cause) {
        super(message, cause);
    }
}

public class ProcessingException extends Exception {
    public ProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}

// AdvancedDataProcessingTest.java
package org.buildozers.dojo.abstraction;

import java.io.*;
import java.util.*;

public class AdvancedDataProcessingTest {
    public static void main(String[] args) throws Exception {
        // Create sample CSV file
        createSampleData("sample_data.csv");
        
        // Build and execute pipeline
        DataProcessingPipeline pipeline = new DataProcessingPipeline("Sales Analysis", 
                                                                    new CsvDataSource("sample_data.csv"))
            .addValidation("basic_validation")
            .addAggregation("region", "amount", AggregationTransformer.AggregationType.SUM);
        
        List<DataRecord> results = pipeline.execute();
        
        System.out.println("Final Results:");
        for (DataRecord record : results) {
            System.out.println(record);
        }
    }
    
    private static void createSampleData(String filename) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("id,region,amount,product");
            writer.println("1,North,100,Widget");
            writer.println("2,South,150,Gadget");
            writer.println("3,North,200,Widget");
            writer.println("4,East,75,Tool");
            writer.println("5,South,125,Gadget");
        }
    }
}
```

</details>

### Current Monolithic Behavior (Before Refactoring)

The existing `MonolithicDataProcessor` works but with poor architecture:

```text
=== Monolithic Data Processing ===
Reading CSV file: data.csv
Validating CSV data...
Validation passed for 10 records
Filtering CSV data (showing Electronics only)...
Filtered: ID=1, Value=100, Region=North, Category=Electronics
Filtered: ID=3, Value=200, Region=North, Category=Electronics
Filtered: ID=5, Value=300, Region=West, Category=Electronics
Filtered: ID=8, Value=220, Region=East, Category=Electronics
Aggregating CSV data (sum of values)...
Total sum: 820
Processing complete!
```

### Target Architecture (After Refactoring)

Your refactored solution should provide the same functionality but with clean separation:

```text
=== Advanced Data Processing Pipeline ===
[DataSource] CsvDataSource initialized: data.csv
[Validator] SchemaValidator applied: 10 records validated
[Filter] CategoryFilter applied: 4 records match 'Electronics'
[Aggregator] SumAggregator applied: Total sum = 820
[Pipeline] Processing completed successfully

Results:
- Format: CSV (Apache Commons CSV)
- Records Processed: 4/10 (filtered)
- Aggregation: SUM = 820
- Processing Time: 15ms
```

---

## Reflection (反省)

**Sensei:** "What insights have you gained from confronting the monolithic beast, advanced deshi?"

**Deshi:** "Sensei, I now understand that monolithic code is not evil by nature - it often starts as a simple solution. But as requirements grow, without proper abstraction patterns, it becomes a tangled web where every change threatens the whole system. The real skill lies in recognizing when to apply abstraction and which patterns serve each purpose."

**Sensei:** "Wisdom speaks through you! You have learned that refactoring is not just code cleanup - it is architectural surgery. Each abstraction layer must earn its place by solving a real problem: data source variations, transformation reusability, error handling consistency, or testing isolation."

### Key Refactoring Insights

- 🏗️ **Monoliths grow naturally** - Simple solutions become complex over time
- 🔍 **Identify patterns in chaos** - Look for repeated logic across different sections  
- 📦 **Extract by responsibility** - Each class should have one clear purpose
- 🔗 **Use interfaces for flexibility** - Abstract the varying parts (data sources, transformations)
- 🎯 **Preserve behavior first** - Functionality must remain unchanged during refactoring
- ⚡ **Refactor incrementally** - Small, safe steps prevent breaking changes
- 🧪 **Enable testing isolation** - Each component should be independently testable

### Before vs After Architecture

**Before (Monolithic)**:
```
MonolithicDataProcessor
├── 400+ lines of mixed logic
├── CSV + JSON + XML parsing all in one method
├── Validation scattered throughout
├── Filtering duplicated per format
├── Aggregation repeated everywhere
└── Error handling inconsistent
```

**After (Abstracted)**:
```
DataProcessingPipeline
├── DataSource<T> (Strategy Pattern)
│   ├── CsvDataSource (Apache Commons CSV)
│   ├── JsonDataSource (org.json)
│   └── XmlDataSource (DOM + XSD)
├── DataTransformer<T,R> (Template Method Pattern)
│   ├── ValidationTransformer
│   ├── FilterTransformer  
│   └── AggregationTransformer
└── ProcessingEngine (Facade Pattern)
    └── Unified error handling & pipeline orchestration
```

---

## Next Steps

Master your abstraction journey with:
- [Java Kata: Interface vs Abstract Class](./java-interfaces-vs-abstract.md)
- [Java Kata: Design Pattern Deep Dive](./java-design-patterns-advanced.md)

---

*"The highest form of abstraction is not the removal of details, but the perfect organization of complexity into harmonious simplicity."* - Master Architect's Wisdom
