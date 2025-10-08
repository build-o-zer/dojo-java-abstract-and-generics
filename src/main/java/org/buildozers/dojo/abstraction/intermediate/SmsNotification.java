package org.buildozers.dojo.abstraction.intermediate;

/**
 * Legacy notification system - SmsNotification with duplication
 * This represents the "before" state for the intermediate kata challenge
 */
public class SmsNotification {
    private String message;
    private String phoneNumber;
    private String priority;
    
    public SmsNotification(String message, String phoneNumber, String priority) {
        this.message = message;
        this.phoneNumber = phoneNumber;
        this.priority = priority;
    }
    
    public boolean sendNotification() {
        // Common validation (DUPLICATED!)
        if (message == null || message.trim().isEmpty()) {
            logError("Message cannot be empty");
            return false;
        }
        if (message.length() > 160) { // SMS has shorter limit
            logError("Message too long. Max: 160 characters");
            return false;
        }
        
        // Specific validation
        if (phoneNumber == null || !phoneNumber.matches("\\+?[1-9]\\d{1,14}")) {
            logError("Invalid phone number: " + phoneNumber);
            return false;
        }
        
        // Send notification
        logAttempt();
        System.out.println("📱 Sending SMS to: " + phoneNumber);
        System.out.println("Message: " + message);
        logSuccess();
        
        return true;
    }
    
    private void logAttempt() {
        System.out.println("[SMS] Attempting to send " + priority + " priority message to: " + phoneNumber);
    }
    
    private void logSuccess() {
        System.out.println("[SMS] Message delivered successfully");
    }
    
    private void logError(String error) {
        System.err.println("[SMS] ERROR: " + error);
    }
}
