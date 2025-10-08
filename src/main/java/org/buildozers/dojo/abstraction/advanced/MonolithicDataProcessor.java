package org.buildozers.dojo.abstraction.advanced;

/**
 * Monolithic data processor - the "before" state showing a complex system
 * that needs to be broken down using advanced abstraction patterns
 */
public class MonolithicDataProcessor {
    
    /**
     * This method demonstrates the problems of a monolithic approach:
     * - Mixed concerns (reading, processing, validation)
     * - Difficult to test individual parts
     * - Hard to add new data sources or transformations
     * - No reusability
     */
    public void processFileData(String filename, String format, boolean validate, 
                               boolean filter, String aggregationType) {
        System.out.println("=== Monolithic Data Processing ===");
        
        // File reading logic mixed with everything else
        if (format.equals("CSV")) {
            System.out.println("Reading CSV file: " + filename);
            // Simulate CSV reading
            String[] data = {"record1,100,North", "record2,150,South", "record3,200,North"};
            
            if (validate) {
                System.out.println("Validating CSV data...");
                // Validation logic mixed in
                for (String dataRecord : data) {
                    if (dataRecord == null || dataRecord.isEmpty()) {
                        System.err.println("Invalid record found!");
                        return;
                    }
                }
            }
            
            if (filter) {
                System.out.println("Filtering CSV data...");
                // Filtering logic mixed in
            }
            
            if ("SUM".equals(aggregationType)) {
                System.out.println("Aggregating CSV data...");
                // Aggregation logic mixed in
            }
            
        } else if (format.equals("JSON")) {
            System.out.println("Reading JSON file: " + filename);
            // Different reading logic, but same validation/filtering/aggregation code!
            // LOTS OF DUPLICATION!
            
        } else if (format.equals("XML")) {
            System.out.println("Reading XML file: " + filename);
            // More duplication!
        }
        
        System.out.println("Processing complete (somehow...)");
    }
    
    public static void main(String[] args) {
        MonolithicDataProcessor processor = new MonolithicDataProcessor();
        
        System.out.println("Problems with this approach:");
        System.out.println("1. Everything is mixed together");
        System.out.println("2. Adding new data sources requires modifying this method");
        System.out.println("3. Testing individual parts is nearly impossible");
        System.out.println("4. Code duplication everywhere");
        System.out.println("5. Violates Single Responsibility Principle");
        System.out.println();
        
        processor.processFileData("data.csv", "CSV", true, true, "SUM");
    }
}
