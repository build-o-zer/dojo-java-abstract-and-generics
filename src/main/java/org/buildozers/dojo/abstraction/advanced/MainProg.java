package org.buildozers.dojo.abstraction.advanced;

import static java.lang.System.out;
import static org.fusesource.jansi.Ansi.ansi;
import static org.fusesource.jansi.Ansi.Color.BLUE;
import static org.fusesource.jansi.Ansi.Color.CYAN;
import static org.fusesource.jansi.Ansi.Color.GREEN;
import static org.fusesource.jansi.Ansi.Color.MAGENTA;
import static org.fusesource.jansi.Ansi.Color.RED;
import static org.fusesource.jansi.Ansi.Color.WHITE;
import static org.fusesource.jansi.Ansi.Color.YELLOW;

import java.util.function.LongSupplier;

import org.fusesource.jansi.AnsiConsole;

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
        // Enable JANSI for Windows compatibility
        AnsiConsole.systemInstall();
        
        try {
            DataProcessor processor = new MonolithicDataProcessor();
            long startTime = System.currentTimeMillis();
        
            // Problems with this approach:
            // 1. Everything is mixed together
            // 2. Adding new data sources requires modifying this method
            // 3. Testing individual parts is nearly impossible
            // 4. Code duplication everywhere
            // 5. Violates Single Responsibility Principle
        
            printHeader("MonolithicDataProcessor Demo");
            printProblems();
        
            // Electronics SUM tests
            printSectionHeader("SUM Aggregation Tests - Electronics Filter");
            
            out.println(ansi().fgBright(GREEN).a("Testing CSV data (SUM aggregation, Electronics filter):").reset());
            long csvResult = processWithIndicator(() -> processor.processFileData("data.csv", "CSV", true, "Electronics", "SUM"));
            printResult("CSV", csvResult);
        
            printSeparator();
        
            out.println(ansi().fgBright(GREEN).a("Testing JSON data (SUM aggregation, Electronics filter):").reset());
            long jsonResult = processWithIndicator(() -> processor.processFileData("data.json", "JSON", true, "Electronics", "SUM"));
            printResult("JSON", jsonResult);
        
            printSeparator();
        
            out.println(ansi().fgBright(GREEN).a("Testing XML data (SUM aggregation, Electronics filter):").reset());
            long xmlResult = processWithIndicator(() -> processor.processFileData("data.xml", "XML", true, "Electronics", "SUM"));
            printResult("XML", xmlResult);
        
            printSectionHeader("COUNT Aggregation Tests - Clothing Filter");
            
            out.println(ansi().fgBright(GREEN).a("CSV - Clothing filter:").reset());
            long csvCountResult = processWithIndicator(() -> processor.processFileData("data.csv", "CSV", false, "Clothing", "COUNT"));
            printCountResult("CSV", csvCountResult);
        
            out.println(ansi().fgBright(GREEN).a("JSON - Clothing filter:").reset());
            long jsonCountResult = processWithIndicator(() -> processor.processFileData("data.json", "JSON", false, "Clothing", "COUNT"));
            printCountResult("JSON", jsonCountResult);
        
            out.println(ansi().fgBright(GREEN).a("XML - Clothing filter:").reset());
            long xmlCountResult = processWithIndicator(() -> processor.processFileData("data.xml", "XML", false, "Clothing", "COUNT"));
            printCountResult("XML", xmlCountResult);
            
            long endTime = System.currentTimeMillis();
            
            // Print summary table
            printSummaryTable(csvResult, jsonResult, xmlResult, csvCountResult, jsonCountResult, xmlCountResult);
            
            printFooter(endTime - startTime);
        } finally {
            // Clean up JANSI
            AnsiConsole.systemUninstall();
        }
    }
    
    private static void printHeader(String title) {
        out.println(ansi().fgBright(YELLOW).a(DOUBLE_BAR).reset());
        out.println(ansi().fgBright(MAGENTA).a(title).reset());
        out.println(ansi().fgBright(YELLOW).a(DOUBLE_BAR).reset());
    }
    
    private static void printSectionHeader(String title) {
        out.println();
        out.println(ansi().fgBright(YELLOW).a(DOUBLE_BAR).reset());
        out.println(ansi().fgBright(MAGENTA).a(title).reset());
        out.println(ansi().fgBright(YELLOW).a(DOUBLE_BAR).reset());
    }
    
    private static void printSeparator() {
        out.println(ansi().fgBright(BLUE).a(SIMPLE_BAR).reset());
    }
    
    private static long processWithIndicator(LongSupplier processor) {
        out.print(ansi().fgBright(YELLOW).a("  Processing... ").reset());
        long result = processor.getAsLong();
        out.println(ansi().fgBright(GREEN).a("done").reset());
        return result;
    }
    
    private static void printProblems() {
        out.println(ansi().fgBright(RED).a("Problems with this monolithic approach:").reset());
        out.println(ansi().fgBright(YELLOW).a("  1. Everything is mixed together").reset());
        out.println(ansi().fgBright(YELLOW).a("  2. Adding new data sources requires modifying this method").reset());
        out.println(ansi().fgBright(YELLOW).a("  3. Testing individual parts is nearly impossible").reset());
        out.println(ansi().fgBright(YELLOW).a("  4. Code duplication everywhere").reset());
        out.println(ansi().fgBright(YELLOW).a("  5. Violates Single Responsibility Principle").reset());
        out.println();
    }
    
    private static void printSummaryTable(long csvSum, long jsonSum, long xmlSum, 
                                          long csvCount, long jsonCount, long xmlCount) {
        out.println();
        out.println(ansi().fgBright(YELLOW).a(MainProg.DOUBLE_BAR).reset());
        out.println(ansi().fgBright(MAGENTA).a("Results Summary").reset());
        out.println(ansi().fgBright(YELLOW).a(MainProg.DOUBLE_BAR).reset());
        
        out.println(ansi().fgBright(GREEN).a("Electronics SUM results:").reset());
        out.println("  CSV: " + ansi().fgBright(WHITE).a(csvSum).reset());
        out.println("  JSON: " + ansi().fgBright(WHITE).a(jsonSum).reset());
        out.println("  XML: " + ansi().fgBright(WHITE).a(xmlSum).reset());
        
        out.println();
        out.println(ansi().fgBright(BLUE).a("Clothing COUNT results:").reset());
        out.println("  CSV: " + ansi().fgBright(WHITE).a(csvCount).reset() + " items");
        out.println("  JSON: " + ansi().fgBright(WHITE).a(jsonCount).reset() + " items");
        out.println("  XML: " + ansi().fgBright(WHITE).a(xmlCount).reset() + " items");
    }
    
    private static void printFooter(long executionTime) {
        out.println(ansi().fgBright(YELLOW).a(MainProg.DOUBLE_BAR).reset());
        out.println(ansi().fgBright(GREEN).a("All tests completed successfully!").reset());
        out.println(ansi().fgBright(BLUE).a("Total execution time: ").fgBright(WHITE).a(executionTime).a(" ms").reset());
        out.println(ansi().fgBright(YELLOW).a(MainProg.DOUBLE_BAR).reset());
    }
    
    private static void printResult(String format, long result) {
        if (result == -1) {
            out.println(ansi().fgBright(RED).a("  Error: Processing failed").reset());
        } else {
            out.println(ansi().fgBright(CYAN).a("  ").a(format).a(" Sum Result: ").fgBright(WHITE).a(result).reset());
        }
    }
    
    private static void printCountResult(String format, long result) {
        if (result == -1) {
            out.println(ansi().fgBright(RED).a("  Error: Processing failed").reset());
        } else {
            out.println(ansi().fgBright(CYAN).a("  ").a(format).a(" Count Result: ").fgBright(WHITE).a(result).a(" items").reset());
        }
    }

    static final String DOUBLE_BAR = "\n" + "═".repeat(50) + "\n";
    static final String SIMPLE_BAR = "\n" + "─".repeat(50) + "\n";

}
