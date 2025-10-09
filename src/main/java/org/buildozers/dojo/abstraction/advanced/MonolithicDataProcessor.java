package org.buildozers.dojo.abstraction.advanced;



import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.IOUtils;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Monolithic data processor - the "before" state showing a complex system
 * that needs to be broken down using advanced abstraction patterns
 * 
 * Now enhanced to use Apache Commons IO for simplified resource loading
 * from the classpath instead of manual BufferedReader/InputStreamReader
 * handling.
 */
public class MonolithicDataProcessor implements DataProcessor {

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
    @Override
    public long processFileData(String filename, String format, boolean validate,
            String categoryFilter, String aggregationType) {

        try {
            // File reading logic mixed with everything else
            if (format.equals("CSV")) {
                try {
                    // Use Apache Commons CSV for proper CSV parsing
                    String csvData = loadTextFile(filename);
                    CSVFormat csvFormat = CSVFormat.DEFAULT.builder().setHeader().setSkipHeaderRecord(true).build();
                    CSVParser parser = CSVParser.parse(csvData, csvFormat);

                    if (validate) {
                        // Validation using Apache Commons CSV
                        for (CSVRecord csvRecord : parser) {
                            if (!csvRecord.isConsistent()) {
                                throw new DataProcessingException("CSV record inconsistency found at line " + csvRecord.getRecordNumber());
                            }
                            // Validate required columns exist
                            if (!csvRecord.isMapped("id") || !csvRecord.isMapped("value") ||
                                    !csvRecord.isMapped("category") || !csvRecord.isMapped("region")) {
                                throw new DataProcessingException("CSV file is missing required columns (id, value, category, region)");
                            }
                        }

                        // Re-parse for further processing since parser is consumed
                        parser = CSVParser.parse(csvData, csvFormat);
                    }

                    // Create temporary data structure to store filtered records
                    List<CSVRecord> filteredRecords = new ArrayList<>();

                    if (categoryFilter != null && !categoryFilter.isEmpty()) {
                        // Filtering using Apache Commons CSV - store matches in temporary structure
                        for (CSVRecord csvRecord : parser) {
                            String category = csvRecord.get("category");
                            if (category.toLowerCase().contains(categoryFilter.toLowerCase())) {
                                filteredRecords.add(csvRecord);
                            }
                        }
                    } else {
                        // No filter - store all records in temporary structure
                        for (CSVRecord csvRecord : parser) {
                            filteredRecords.add(csvRecord);
                        }
                    }

                    // Use temporary data structure for aggregation
                    if ("SUM".equals(aggregationType)) {
                        long sum = 0;
                        for (CSVRecord csvRecord : filteredRecords) {
                            try {
                                sum += Integer.parseInt(csvRecord.get("value"));
                            } catch (NumberFormatException e) {
                                // Continue processing, skip invalid values
                            }
                        }
                        return sum;
                    } else if ("COUNT".equals(aggregationType)) {
                        return filteredRecords.size();
                    }

                } catch (IOException e) {
                    throw new DataProcessingException("Failed to parse CSV file: " + filename, e);
                }

            } else if (format.equals("JSON")) {
                String jsonData = loadTextFile(filename);

                try {
                    // Parse JSON using proper JSON library
                    JSONObject jsonObject = new JSONObject(jsonData);

                    // validation
                    if (validate) {
                        if (!validateJsonAgainstSchema(jsonObject)) {
                            throw new DataProcessingException("JSON validation failed against schema");
                        }
                    }

                    JSONArray dataArray = jsonObject.getJSONArray("data");

                    // Create temporary data structure to store filtered JSON records
                    List<JSONObject> filteredJsonRecords = new ArrayList<>();

                    // Filtering
                    if (categoryFilter != null && !categoryFilter.isEmpty()) {
                        for (int i = 0; i < dataArray.length(); i++) {
                            JSONObject jsonRecord = dataArray.getJSONObject(i);
                            String category = jsonRecord.getString("category");
                            if (category.toLowerCase().contains(categoryFilter.toLowerCase())) {
                                filteredJsonRecords.add(jsonRecord);
                            }
                        }
                    } else {
                        // No filter - store all records in temporary structure
                        for (int i = 0; i < dataArray.length(); i++) {
                            filteredJsonRecords.add(dataArray.getJSONObject(i));
                        }
                    }

                    // Aggregation
                    if ("SUM".equals(aggregationType)) {
                        long sum = 0;
                        for (JSONObject jsonRecord : filteredJsonRecords) {
                            sum += jsonRecord.getInt("value");
                        }
                        return sum;
                    } else if ("COUNT".equals(aggregationType)) {
                        return filteredJsonRecords.size();
                    }

                } catch (JSONException e) {
                    throw new DataProcessingException("Failed to parse JSON file: " + filename, e);
                }

            } else if (format.equals("XML")) {
                String xmlData = loadTextFile(filename);

                if (validate) {
                    if (!validateXmlAgainstXsd(xmlData)) {
                        throw new DataProcessingException("XML validation failed against XSD schema");
                    }
                }

                // Parse XML into DOM for proper processing
                try {
                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                    factory.setNamespaceAware(true); // Important for namespaced XML
                    DocumentBuilder builder = factory.newDocumentBuilder();
                    Document document = builder.parse(new InputSource(new StringReader(xmlData)));

                    // Get all record elements
                    NodeList recordNodes = document.getElementsByTagNameNS(XML_NAMESPACE, "record");

                    // Create temporary data structure to store filtered XML elements
                    List<Element> filteredXmlElements = new ArrayList<>();

                    // Filtering
                    if (categoryFilter != null && !categoryFilter.isEmpty()) {
                        for (int i = 0; i < recordNodes.getLength(); i++) {
                            Element recordElement = (Element) recordNodes.item(i);

                            // Get category element
                            NodeList categoryNodes = recordElement
                                    .getElementsByTagNameNS(XML_NAMESPACE, "category");
                            if (categoryNodes.getLength() > 0) {
                                String category = categoryNodes.item(0).getTextContent();
                                if (categoryFilter.equals(category)) {
                                    filteredXmlElements.add(recordElement);
                                }
                            }
                        }
                    } else {
                        // No filter - store all elements in temporary structure
                        for (int i = 0; i < recordNodes.getLength(); i++) {
                            filteredXmlElements.add((Element) recordNodes.item(i));
                        }
                    }

                    // Aggregation
                    if ("SUM".equals(aggregationType)) {
                        long sum = 0;
                        for (Element recordElement : filteredXmlElements) {
                            String valueText = getElementText(recordElement, "value");
                            try {
                                sum += Integer.parseInt(valueText);
                            } catch (NumberFormatException e) {
                                // Continue processing, skip invalid values
                            }
                        }
                        return sum;
                    } else if ("COUNT".equals(aggregationType)) {
                        return filteredXmlElements.size();
                    }

                } catch (ParserConfigurationException | SAXException e) {
                    throw new DataProcessingException("Failed to parse XML file: " + filename, e);
                }
            }
        } catch (IOException e) {
            throw new DataProcessingException("Failed to load file: " + filename, e);
        }

        throw new UnsupportedAggregationException(aggregationType);
    }

    /**
     * Loads a text file from the classpath as a single string.
     * Uses Apache Commons IO for simplified resource loading.
     * 
     * This helper method is used for loading JSON and XML files that need to be
     * processed as complete strings rather than line-by-line. Like the CSV loader,
     * this demonstrates reusable file loading logic that's embedded in the
     * monolith.
     * 
     * @param filename the name of the text file to load from the classpath
     * @return the complete file content as a single string
     * @throws IOException if the file cannot be found or read
     */
    private String loadTextFile(String filename) throws IOException {
        // Using Apache Commons IO to load resource as string
        String resourcePath = "org/buildozers/dojo/abstraction/advanced/" + filename;
        try (var inputStream = getClass().getClassLoader().getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                throw new IOException("File not found: " + filename);
            }
            return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        }
    }

    /**
     * Validates XML content against an XSD schema loaded from the classpath.
     * 
     * This method demonstrates proper XML validation using XSD (XML Schema
     * Definition)
     * instead of simple string-based checks. It loads the schema file, creates a
     * validator, and checks the XML content for compliance with the defined
     * structure,
     * data types, and constraints.
     * 
     * The validation includes:
     * - Structure validation (required elements, proper nesting)
     * - Data type validation (integers, strings, etc.)
     * - Enumeration validation (only allowed values for regions and categories)
     * - Namespace compliance
     * 
     * @param xmlContent the XML content to validate as a string
     * @return true if the XML is valid according to the XSD schema, false otherwise
     */
    private boolean validateXmlAgainstXsd(String xmlContent) {
        try {
            // Load XSD schema from classpath using loadTextFile method
            String schemaContent = loadTextFile("data-schema.xsd");

            // Create schema factory and load schema
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            // Disable access to external entities for security (prevents XXE attacks)
            factory.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            factory.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
            Schema schema = factory.newSchema(new StreamSource(new StringReader(schemaContent)));

            // Create validator and validate XML
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new StringReader(xmlContent)));

            return true; // Validation successful

        } catch (SAXException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Validates JSON content against a JSON Schema using Everit JSON Schema
     * library.
     * Loads the schema from the classpath and performs comprehensive validation
     * including data types, required fields, enumerations, and structural
     * constraints.
     * 
     * This method demonstrates JSON Schema validation similar to XML XSD
     * validation,
     * but like other validation logic, it's embedded in the monolithic class
     * rather than being a reusable component.
     * 
     * @param jsonContent the JSON content to validate
     * @return true if validation passes, false otherwise
     */
    private boolean validateJsonAgainstSchema(JSONObject jsonObject) {
        try {
            // Load JSON Schema from classpath using loadTextFile method
            String schemaContent = loadTextFile("data-schema.json");
            JSONObject schemaJson = new JSONObject(schemaContent);

            // Create schema loader and load schema
            org.everit.json.schema.Schema schema = SchemaLoader.load(schemaJson);

            // Execute the validation
            schema.validate(jsonObject);

            return true; // Validation successful
        } catch (ValidationException e) {
            return false;
        } catch (IOException e) {
            return false;
        } catch (JSONException e) {
            return false;
        }
    }

    /**
     * Helper method to extract text content from a child element by tag name.
     * Uses namespace-aware lookup for elements within the defined namespace.
     * 
     * This demonstrates DOM navigation which is more robust than string parsing
     * but is embedded within the monolithic class rather than being extracted
     * as a reusable utility.
     * 
     * @param parentElement the parent element to search within
     * @param tagName       the local name of the child element to find
     * @return the text content of the first matching child element, or empty string
     *         if not found
     */
    private String getElementText(Element parentElement, String tagName) {
        NodeList nodes = parentElement.getElementsByTagNameNS(XML_NAMESPACE, tagName);
        if (nodes.getLength() > 0) {
            return nodes.item(0).getTextContent();
        }
        return "";
    }
}
