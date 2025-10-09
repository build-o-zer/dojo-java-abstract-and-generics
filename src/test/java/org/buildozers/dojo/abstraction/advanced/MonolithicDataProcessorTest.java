package org.buildozers.dojo.abstraction.advanced;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("MonolithicDataProcessor Tests")
class MonolithicDataProcessorTest {

    private DataProcessor processor;

    @BeforeEach
    void setUp() {
        processor = new MonolithicDataProcessor();
    }

    @Nested
    @DisplayName("CSV Processing Tests")
    class CsvProcessingTests {

        @Test
        @DisplayName("Should process CSV file with SUM aggregation successfully")
        void shouldProcessCsvWithSumAggregation() {
            // Given
            String filename = "data.csv";
            String format = "CSV";
            boolean validate = true;
            String categoryFilter = "Electronics";
            String aggregationType = "SUM";

            // When
            long result = processor.processFileData(filename, format, validate, categoryFilter, aggregationType);

            // Then
            assertEquals(820L, result);
        }

        @Test
        @DisplayName("Should process CSV file with COUNT aggregation successfully")
        void shouldProcessCsvWithCountAggregation() {
            // Given
            String filename = "data.csv";
            String format = "CSV";
            boolean validate = false;
            String categoryFilter = "Clothing";
            String aggregationType = "COUNT";

            // When
            long result = processor.processFileData(filename, format, validate, categoryFilter, aggregationType);

            // Then
            assertEquals(3L, result);
        }

        @Test
        @DisplayName("Should throw DataProcessingException for non-existent CSV file")
        void shouldThrowExceptionForNonExistentCsvFile() {
            // Given
            String filename = "nonexistent.csv";
            String format = "CSV";
            boolean validate = true;
            String categoryFilter = "Electronics";
            String aggregationType = "SUM";

            // When & Then
            DataProcessingException exception = assertThrows(DataProcessingException.class, () ->
                processor.processFileData(filename, format, validate, categoryFilter, aggregationType)
            );
            
            assertTrue(exception.getMessage().contains("Failed to parse"));
            assertTrue(exception.getCause() instanceof java.io.IOException);
        }
    }

    @Nested
    @DisplayName("JSON Processing Tests")
    class JsonProcessingTests {

        @Test
        @DisplayName("Should process JSON file with SUM aggregation successfully")
        void shouldProcessJsonWithSumAggregation() {
            // Given
            String filename = "data.json";
            String format = "JSON";
            boolean validate = true;
            String categoryFilter = "Electronics";
            String aggregationType = "SUM";

            // When
            long result = processor.processFileData(filename, format, validate, categoryFilter, aggregationType);

            // Then
            assertEquals(820L, result);
        }

        @Test
        @DisplayName("Should process JSON file with COUNT aggregation successfully")
        void shouldProcessJsonWithCountAggregation() {
            // Given
            String filename = "data.json";
            String format = "JSON";
            boolean validate = false;
            String categoryFilter = "Clothing";
            String aggregationType = "COUNT";

            // When
            long result = processor.processFileData(filename, format, validate, categoryFilter, aggregationType);

            // Then
            assertEquals(3L, result);
        }

        @Test
        @DisplayName("Should throw DataProcessingException for non-existent JSON file")
        void shouldThrowExceptionForNonExistentJsonFile() {
            // Given
            String filename = "nonexistent.json";
            String format = "JSON";
            boolean validate = true;
            String categoryFilter = "Electronics";
            String aggregationType = "SUM";

            // When & Then
            DataProcessingException exception = assertThrows(DataProcessingException.class, () ->
                processor.processFileData(filename, format, validate, categoryFilter, aggregationType)
            );
            
            assertTrue(exception.getMessage().contains("Failed to load file"));
            assertTrue(exception.getCause() instanceof java.io.IOException);
        }
    }

    @Nested
    @DisplayName("XML Processing Tests")
    class XmlProcessingTests {

        @Test
        @DisplayName("Should process XML file with SUM aggregation successfully")
        void shouldProcessXmlWithSumAggregation() {
            // Given
            String filename = "data.xml";
            String format = "XML";
            boolean validate = true;
            String categoryFilter = "Electronics";
            String aggregationType = "SUM";

            // When
            long result = processor.processFileData(filename, format, validate, categoryFilter, aggregationType);

            // Then
            assertEquals(820L, result);
        }

        @Test
        @DisplayName("Should process XML file with COUNT aggregation successfully")
        void shouldProcessXmlWithCountAggregation() {
            // Given
            String filename = "data.xml";
            String format = "XML";
            boolean validate = false;
            String categoryFilter = "Clothing";
            String aggregationType = "COUNT";

            // When
            long result = processor.processFileData(filename, format, validate, categoryFilter, aggregationType);

            // Then
            assertEquals(3L, result);
        }

        @Test
        @DisplayName("Should throw DataProcessingException for non-existent XML file")
        void shouldThrowExceptionForNonExistentXmlFile() {
            // Given
            String filename = "nonexistent.xml";
            String format = "XML";
            boolean validate = true;
            String categoryFilter = "Electronics";
            String aggregationType = "SUM";

            // When & Then
            DataProcessingException exception = assertThrows(DataProcessingException.class, () ->
                processor.processFileData(filename, format, validate, categoryFilter, aggregationType)
            );
            
            assertTrue(exception.getMessage().contains("Failed to load file"));
            assertTrue(exception.getCause() instanceof java.io.IOException);
        }
    }

    @Nested
    @DisplayName("Error Handling Tests")
    class ErrorHandlingTests {

        @Test
        @DisplayName("Should throw UnsupportedAggregationException for unsupported aggregation type")
        void shouldThrowExceptionForUnsupportedAggregationType() {
            // Given
            String filename = "data.csv";
            String format = "CSV";
            boolean validate = false;
            String categoryFilter = "Electronics";
            String aggregationType = "AVERAGE";

            // When & Then
            UnsupportedAggregationException exception = assertThrows(UnsupportedAggregationException.class, () ->
                processor.processFileData(filename, format, validate, categoryFilter, aggregationType)
            );
            
            assertTrue(exception.getMessage().contains("Unsupported aggregation type: AVERAGE"));
            assertTrue(exception.getMessage().contains("Supported types are: SUM, COUNT"));
        }

        @Test
        @DisplayName("Should throw UnsupportedAggregationException for null aggregation type")
        void shouldThrowExceptionForNullAggregationType() {
            // Given
            String filename = "data.csv";
            String format = "CSV";
            boolean validate = false;
            String categoryFilter = "Electronics";
            String aggregationType = null;

            // When & Then
            UnsupportedAggregationException exception = assertThrows(UnsupportedAggregationException.class, () ->
                processor.processFileData(filename, format, validate, categoryFilter, aggregationType)
            );
            
            assertTrue(exception.getMessage().contains("Unsupported aggregation type: null"));
        }

        @Test
        @DisplayName("Should throw UnsupportedAggregationException for empty aggregation type")
        void shouldThrowExceptionForEmptyAggregationType() {
            // Given
            String filename = "data.csv";
            String format = "CSV";
            boolean validate = false;
            String categoryFilter = "Electronics";
            String aggregationType = "";

            // When & Then
            UnsupportedAggregationException exception = assertThrows(UnsupportedAggregationException.class, () ->
                processor.processFileData(filename, format, validate, categoryFilter, aggregationType)
            );
            
            assertTrue(exception.getMessage().contains("Unsupported aggregation type: "));
        }
    }

    @Nested
    @DisplayName("Filter Tests")
    class FilterTests {

        @Test
        @DisplayName("Should process all records when no category filter is provided")
        void shouldProcessAllRecordsWithoutFilter() {
            // Given
            String filename = "data.csv";
            String format = "CSV";
            boolean validate = false;
            String categoryFilter = null;
            String aggregationType = "COUNT";

            // When
            long result = processor.processFileData(filename, format, validate, categoryFilter, aggregationType);

            // Then
            assertTrue(result > 3); // Should have more records than just Clothing items
        }

        @Test
        @DisplayName("Should process all records when empty category filter is provided")
        void shouldProcessAllRecordsWithEmptyFilter() {
            // Given
            String filename = "data.csv";
            String format = "CSV";
            boolean validate = false;
            String categoryFilter = "";
            String aggregationType = "COUNT";

            // When
            long result = processor.processFileData(filename, format, validate, categoryFilter, aggregationType);

            // Then
            assertTrue(result > 3); // Should have more records than just Clothing items
        }

        @Test
        @DisplayName("Should return 0 when no records match the filter")
        void shouldReturnZeroWhenNoRecordsMatch() {
            // Given
            String filename = "data.csv";
            String format = "CSV";
            boolean validate = false;
            String categoryFilter = "NonExistentCategory";
            String aggregationType = "COUNT";

            // When
            long result = processor.processFileData(filename, format, validate, categoryFilter, aggregationType);

            // Then
            assertEquals(0L, result);
        }
    }

    @Nested
    @DisplayName("Validation Tests")
    class ValidationTests {

        @Test
        @DisplayName("Should pass validation for valid CSV file")
        void shouldPassValidationForValidCsvFile() {
            // Given
            String filename = "data.csv";
            String format = "CSV";
            boolean validate = true;
            String categoryFilter = "Electronics";
            String aggregationType = "SUM";

            // When & Then - Should not throw exception
            assertDoesNotThrow(() ->
                processor.processFileData(filename, format, validate, categoryFilter, aggregationType)
            );
        }

        @Test
        @DisplayName("Should pass validation for valid JSON file")
        void shouldPassValidationForValidJsonFile() {
            // Given
            String filename = "data.json";
            String format = "JSON";
            boolean validate = true;
            String categoryFilter = "Electronics";
            String aggregationType = "SUM";

            // When & Then - Should not throw exception
            assertDoesNotThrow(() ->
                processor.processFileData(filename, format, validate, categoryFilter, aggregationType)
            );
        }

        @Test
        @DisplayName("Should pass validation for valid XML file")
        void shouldPassValidationForValidXmlFile() {
            // Given
            String filename = "data.xml";
            String format = "XML";
            boolean validate = true;
            String categoryFilter = "Electronics";
            String aggregationType = "SUM";

            // When & Then - Should not throw exception
            assertDoesNotThrow(() ->
                processor.processFileData(filename, format, validate, categoryFilter, aggregationType)
            );
        }
    }

    @Nested
    @DisplayName("Cross-format Consistency Tests")
    class CrossFormatConsistencyTests {

        @Test
        @DisplayName("Should return same SUM result across all formats for same filter")
        void shouldReturnSameSumResultAcrossFormats() {
            // Given
            String categoryFilter = "Electronics";
            String aggregationType = "SUM";
            boolean validate = true;

            // When
            long csvResult = processor.processFileData("data.csv", "CSV", validate, categoryFilter, aggregationType);
            long jsonResult = processor.processFileData("data.json", "JSON", validate, categoryFilter, aggregationType);
            long xmlResult = processor.processFileData("data.xml", "XML", validate, categoryFilter, aggregationType);

            // Then
            assertEquals(csvResult, jsonResult);
            assertEquals(jsonResult, xmlResult);
            assertEquals(820L, csvResult);
        }

        @Test
        @DisplayName("Should return same COUNT result across all formats for same filter")
        void shouldReturnSameCountResultAcrossFormats() {
            // Given
            String categoryFilter = "Clothing";
            String aggregationType = "COUNT";
            boolean validate = false;

            // When
            long csvResult = processor.processFileData("data.csv", "CSV", validate, categoryFilter, aggregationType);
            long jsonResult = processor.processFileData("data.json", "JSON", validate, categoryFilter, aggregationType);
            long xmlResult = processor.processFileData("data.xml", "XML", validate, categoryFilter, aggregationType);

            // Then
            assertEquals(csvResult, jsonResult);
            assertEquals(jsonResult, xmlResult);
            assertEquals(3L, csvResult);
        }
    }
}
