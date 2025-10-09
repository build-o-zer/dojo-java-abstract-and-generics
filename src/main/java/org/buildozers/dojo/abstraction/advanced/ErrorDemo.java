package org.buildozers.dojo.abstraction.advanced;

import static java.lang.System.out;
import static org.fusesource.jansi.Ansi.ansi;
import static org.fusesource.jansi.Ansi.Color.CYAN;
import static org.fusesource.jansi.Ansi.Color.GREEN;
import static org.fusesource.jansi.Ansi.Color.MAGENTA;
import static org.fusesource.jansi.Ansi.Color.RED;
import static org.fusesource.jansi.Ansi.Color.WHITE;
import static org.fusesource.jansi.Ansi.Color.YELLOW;

import org.fusesource.jansi.AnsiConsole;

public class ErrorDemo {
    public static void main(String[] args) {
        // Enable JANSI for Windows compatibility
        AnsiConsole.systemInstall();
        
        try {
            DataProcessor processor = new MonolithicDataProcessor();
            
            out.println(ansi().fgBright(CYAN).a("🧪 ").reset().fgBright(YELLOW).a(MainProg.DOUBLE_BAR).reset());
            out.println(ansi().fgBright(MAGENTA).a("⚠️  Error Handling Demo").reset());
            out.println(ansi().fgBright(CYAN).a("🧪 ").reset().fgBright(YELLOW).a(MainProg.DOUBLE_BAR).reset());
            
            out.println(ansi().fgBright(GREEN).a("🔍 Testing with non-existent file:").reset());
            long errorResult = processor.processFileData("nonexistent.csv", "CSV", true, "Electronics", "SUM");
            printResult("📄 CSV", errorResult);
            
            out.println(ansi().fgBright(GREEN).a("🔍 Testing unsupported aggregation type:").reset());
            long unsupportedResult = processor.processFileData("data.csv", "CSV", false, "Electronics", "AVERAGE");
            printUnsupportedResult("📄 CSV", unsupportedResult);
            
            out.println(ansi().fgBright(GREEN).a("✅ Error handling demo completed! 🎯").reset());
        } finally {
            // Clean up JANSI
            AnsiConsole.systemUninstall();
        }
    }
    
    private static void printResult(String format, long result) {
        if (result == -1) {
            out.println(ansi().fgBright(RED).a("❌ ").a(format).a(" Error: File not found or processing failed").reset());
        } else {
            out.println(ansi().fgBright(CYAN).a("💰 ").a(format).a(" Sum Result: ").fgBright(WHITE).a(result).reset());
        }
    }
    
    private static void printUnsupportedResult(String format, long result) {
        if (result == 0) {
            out.println(ansi().fgBright(YELLOW).a("⚠️  ").a(format).a(" Warning: Unsupported aggregation type, returned: ").fgBright(WHITE).a(result).reset());
        } else {
            out.println(ansi().fgBright(CYAN).a("💰 ").a(format).a(" Result: ").fgBright(WHITE).a(result).reset());
        }
    }
}
