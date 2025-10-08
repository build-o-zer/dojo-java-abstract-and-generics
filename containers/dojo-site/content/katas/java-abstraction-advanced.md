# Java Kata: Advanced Abstraction Patterns

**Subject:** Complex Abstraction Patterns and Design  
**Goal:** Master advanced abstraction techniques and design patterns  
**Level:** Advanced  
**Duration:** 35-45 minutes  

---

## 道場での対話 (Dojo Dialogue)

### Scene: The Architecture Garden

*The deshi sits surrounded by UML diagrams and design documents, contemplating a complex system architecture. The sensei approaches with the serene confidence of a master architect.*

**Sensei:** "I observe deep contemplation in your expression, advanced deshi. What architectural challenge occupies your mind?"

**Deshi:** "Sensei, I must design a data processing pipeline that can handle different data sources (files, databases, APIs) and apply various transformations (validation, filtering, aggregation). The complexity grows with each requirement, and I fear creating a tangled mess."

**Sensei:** "Ah, you face the ultimate test of abstraction mastery. This requires not just abstract classes, but the wisdom to combine multiple patterns harmoniously. Let me guide you through the way of advanced abstraction."

---

## The Teaching

**Sensei:** "First, understand the complexity you face. A naive approach might look like this:"

```java
// Anti-pattern: Everything mixed together
public class DataProcessor {
    public void processFileData(String filename, String format, boolean validate, 
                               boolean filter, String aggregationType) {
        // 200+ lines of mixed concerns
        if (format.equals("CSV")) {
            // CSV reading logic
        } else if (format.equals("JSON")) {
            // JSON reading logic
        }
        
        if (validate) {
            // Validation logic
        }
        
        if (filter) {
            // Filtering logic
        }
        
        // ... more mixed logic
    }
}
```

**Deshi:** "Sensei, I can already see this becoming unmaintainable. Each new data source or transformation would require modifying this monolithic method."

**Sensei:** "Precisely! This is where advanced abstraction patterns come to our rescue. We shall use multiple layers of abstraction working in harmony."

---

## The Practice (実践)

**Sensei:** "We shall build a flexible pipeline using several abstraction patterns combined."

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

**Sensei:** "Now, ultimate deshi, demonstrate mastery by building a complete data processing system:"

### Setup

Build a system that can:
1. Read data from multiple sources (CSV files, JSON APIs, Database)
2. Apply multiple transformations (validation, filtering, aggregation)
3. Handle errors gracefully
4. Process data in a configurable pipeline

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

### Expected Result

```text
=== Starting Pipeline: Sales Analysis ===
[CSV] Loaded 5 records from: sample_data.csv
[VALIDATION] Starting transformation 'basic_validation' on 5 records
[VALIDATION] Completed 'basic_validation' - Output: 5 records (2ms)
[AGGREGATION] Starting transformation 'sum_by_region' on 5 records
[AGGREGATION] Completed 'sum_by_region' - Output: 3 records (5ms)
=== Pipeline 'Sales Analysis' completed ===

Final Results:
DataRecord{region=North, amount_sum=300}
DataRecord{region=South, amount_sum=275}
DataRecord{region=East, amount_sum=75}
```

---

## Reflection (反省)

**Sensei:** "What mastery have you achieved today, advanced deshi?"

**Deshi:** "I have learned that true abstraction mastery lies not in using abstract classes alone, but in orchestrating multiple patterns to create flexible, maintainable architectures. Each abstraction layer serves a specific purpose, and together they create something greater than the sum of their parts."

**Sensei:** "Excellently spoken! You have grasped the deepest wisdom: abstraction is not about hiding complexity, but about organizing it in a way that serves both present needs and future growth."

### Key Takeaways

- 🏗️ Complex systems benefit from multiple abstraction layers
- 🔗 Combine Template Method, Strategy, and other patterns
- 📦 Use generics with abstract classes for type safety
- 🔄 Build composable, reusable components
- 🎯 Separate concerns through well-defined abstractions
- ⚡ Design for extensibility without over-engineering

---

## Next Steps

Master your abstraction journey with:
- [Java Kata: Interface vs Abstract Class](./java-interfaces-vs-abstract.md)
- [Java Kata: Design Pattern Deep Dive](./java-design-patterns-advanced.md)

---

*"The highest form of abstraction is not the removal of details, but the perfect organization of complexity into harmonious simplicity."* - Master Architect's Wisdom
