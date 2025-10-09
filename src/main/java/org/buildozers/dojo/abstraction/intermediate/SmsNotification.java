package org.buildozers.dojo.abstraction.intermediate;

/**
 * Legacy notification system - SmsNotification with duplication
 * This represents the "before" state for the intermediate kata challenge
 */
public class SmsNotification extends NotificationService {
    private String phoneNumber;
    
    public SmsNotification(String message, String phoneNumber, String priority) {
        super(message, priority, "SMS");
        this.phoneNumber = phoneNumber;
    }

    @Override
    protected boolean specificValidation() {
        // Specific validation
        if (phoneNumber == null || !phoneNumber.matches("\\+?[1-9]\\d{1,14}")) {
            logError("Invalid phone number: " + phoneNumber);
            return false;
        }
        return true;
    }

    @Override
    protected boolean executeNotification(){
        // Send notification
        logAttempt();
        System.out.println("📱 Sending SMS to: " + phoneNumber);
        System.out.println("Message: " + message);
        logSuccess();
        
        return true;
    }
    
    @Override
    protected void logAttempt() {
        System.out.println("[SMS] Attempting to send " + priority + " priority message to: " + phoneNumber);
    }
    
}
