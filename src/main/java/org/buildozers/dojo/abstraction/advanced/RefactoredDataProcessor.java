package org.buildozers.dojo.abstraction.advanced;

/**
 * Refactored data processor that demonstrates proper separation of concerns.
 * This class is intended to be implemented by deshis (students) as an exercise
 * to break down the monolithic architecture into smaller, focused components.
 * 
 * Should implement the DataProcessor interface to ensure compatibility.
 * TODO: Add "implements DataProcessor" once interface compilation is resolved.
 */
public class RefactoredDataProcessor {

    /**
     * Process file data with the specified parameters.
     * 
     * @param filename the name of the file to process
     * @param format the format of the file (CSV, JSON, XML)
     * @param validate whether to validate the file against schema
     * @param categoryFilter the category to filter by (null for all categories)
     * @param aggregationType the type of aggregation (SUM, COUNT)
     * @return the result of the aggregation
     * @throws RuntimeException when method is not implemented
     */
    public long processFileData(String filename, String format, boolean validate, 
                               String categoryFilter, String aggregationType) {
        throw new RuntimeException("unimplemented method");
    }

}
