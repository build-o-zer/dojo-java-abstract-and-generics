package org.buildozers.dojo.abstraction.advanced;

public interface DataProcessor {

    /**
     * XML namespace for validation
     */
    String XML_NAMESPACE = "http://buildozers.org/dojo/data";

    /**
     * This method demonstrates the problems of a monolithic approach:
     * - Mixed concerns (reading, processing, validation)
     * - Difficult to test individual parts
     * - Hard to add new data sources or transformations
     * - No reusability
     * 
     * Processes data files in different formats (CSV, JSON, XML) with optional
     * validation, filtering, and aggregation operations. All logic is mixed
     * together in one large method, making it hard to maintain and test.
     * 
     * @param filename        the name of the file to process (loaded from
     *                        classpath)
     * @param format          the format of the file ("CSV", "JSON", or "XML")
     * @param validate        whether to perform validation on the data
     * @param categoryFilter  the category name to filter by (e.g., "Electronics",
     *                        "Clothing", "Books"), or null/empty for no filtering
     * @param aggregationType the type of aggregation to perform ("SUM", "COUNT", or
     *                        other)
     * @return the aggregated result as a long value
     * @throws DataProcessingException if file processing fails
     * @throws UnsupportedAggregationException if aggregation type is not supported
     */
    long processFileData(String filename, String format, boolean validate,
            String categoryFilter, String aggregationType);

}