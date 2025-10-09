package org.buildozers.dojo.abstraction.advanced;

/**
 * Exception thrown when an unsupported aggregation type is requested
 */
public class UnsupportedAggregationException extends RuntimeException {
    
    public UnsupportedAggregationException(String aggregationType) {
        super("Unsupported aggregation type: " + aggregationType + ". Supported types are: SUM, COUNT");
    }
}
