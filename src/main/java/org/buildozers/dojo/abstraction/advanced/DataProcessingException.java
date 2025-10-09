package org.buildozers.dojo.abstraction.advanced;

/**
 * Exception thrown when data processing fails due to various reasons
 * such as file not found, parsing errors, validation failures, etc.
 */
public class DataProcessingException extends RuntimeException {
    
    public DataProcessingException(String message) {
        super(message);
    }
    
    public DataProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
