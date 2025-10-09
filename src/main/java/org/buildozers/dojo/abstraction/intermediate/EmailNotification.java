package org.buildozers.dojo.abstraction.intermediate;

/**
 * Legacy notification system - EmailNotification with duplication
 * This represents the "before" state for the intermediate kata challenge
 */
public class EmailNotification extends NotificationService {
    private String email;
    
    public EmailNotification(String message, String email, String priority) {
        super(message, priority, "Email");
        this.email = email;
    }
    
    @Override
    protected boolean specificValidation() {
        if (email == null || !email.contains("@")) {
            logError("Invalid email address: " + email);
            return false;
        }
        return true;
    }

    @Override
    protected boolean executeNotification() {
        // Send notification
        logAttempt();
        System.out.println("📧 Sending email to: " + email);
        System.out.println("Subject: " + priority.toUpperCase() + " Notification");
        System.out.println("Body: " + message);
        logSuccess();
        
        return true;
    }
    
    @Override
    protected void logAttempt() {
        System.out.println("[EMAIL] Attempting to send " + priority + " priority message to: " + email);
    }
    

}
