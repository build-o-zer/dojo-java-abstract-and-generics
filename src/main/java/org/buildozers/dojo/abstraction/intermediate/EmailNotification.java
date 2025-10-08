package org.buildozers.dojo.abstraction.intermediate;

/**
 * Legacy notification system - EmailNotification with duplication
 * This represents the "before" state for the intermediate kata challenge
 */
public class EmailNotification {
    private String message;
    private String email;
    private String priority;
    
    public EmailNotification(String message, String email, String priority) {
        this.message = message;
        this.email = email;
        this.priority = priority;
    }
    
    public boolean sendNotification() {
        // Common validation (duplicated across notification types)
        if (message == null || message.trim().isEmpty()) {
            logError("Message cannot be empty");
            return false;
        }
        if (message.length() > 1000) {
            logError("Message too long. Max: 1000 characters");
            return false;
        }
        
        // Specific validation
        if (email == null || !email.contains("@")) {
            logError("Invalid email address: " + email);
            return false;
        }
        
        // Send notification
        logAttempt();
        System.out.println("📧 Sending email to: " + email);
        System.out.println("Subject: " + priority.toUpperCase() + " Notification");
        System.out.println("Body: " + message);
        logSuccess();
        
        return true;
    }
    
    private void logAttempt() {
        System.out.println("[EMAIL] Attempting to send " + priority + " priority message to: " + email);
    }
    
    private void logSuccess() {
        System.out.println("[EMAIL] Message delivered successfully");
    }
    
    private void logError(String error) {
        System.err.println("[EMAIL] ERROR: " + error);
    }
}
