package org.buildozers.dojo.abstraction.advanced;

import static java.lang.System.out;

public class MainProg {

    /**
     * Main method to demonstrate the monolithic data processor in action.
     * 
     * This method shows the problems with the monolithic approach by running
     * the same processing logic on three different data formats (CSV, JSON, XML).
     * Notice how all the complexity is hidden within the single processFileData
     * method,
     * making it impossible to test individual components or reuse specific
     * functionality.
     * 
     * This serves as the "before" state for learners to refactor using advanced
     * abstraction patterns like Strategy, Template Method, Factory, and others.
     * 
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        MonolithicDataProcessor processor = new MonolithicDataProcessor();
    
        // Problems with this approach:
        // 1. Everything is mixed together
        // 2. Adding new data sources requires modifying this method
        // 3. Testing individual parts is nearly impossible
        // 4. Code duplication everywhere
        // 5. Violates Single Responsibility Principle
    
        out.println(MonolithicDataProcessor.DOUBLE_BAR);
        out.println("MonolithicDataProcessor ...");
        out.println(MonolithicDataProcessor.DOUBLE_BAR);
    
        out.println("Testing with CSV data (SUM aggregation, Electronics filter):");
        processor.processFileData("data.csv", "CSV", true, "Electronics", "SUM");
    
        out.println(MonolithicDataProcessor.SIMPLE_BAR);
    
        out.println("Testing with JSON data (SUM aggregation, Electronics filter):");
        processor.processFileData("data.json", "JSON", true, "Electronics", "SUM");
    
        out.println(MonolithicDataProcessor.SIMPLE_BAR);
    
        out.println("Testing with XML data (SUM aggregation, Electronics filter):");
        processor.processFileData("data.xml", "XML", true, "Electronics", "SUM");
    
        out.println(MonolithicDataProcessor.DOUBLE_BAR);
        out.println("Testing with different filter (COUNT, Clothing)");
        out.println(MonolithicDataProcessor.DOUBLE_BAR);
        out.println("\nCSV - Clothing filter:");
        processor.processFileData("data.csv", "CSV", false, "Clothing", "COUNT");
    
        out.println("\nJSON - Clothing filter:");
        processor.processFileData("data.json", "JSON", false, "Clothing", "COUNT");
    
        out.println("\nXML - Clothing filter:");
        processor.processFileData("data.xml", "XML", false, "Clothing", "COUNT");
    }

}
