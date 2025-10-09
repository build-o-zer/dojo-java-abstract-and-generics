package org.buildozers.dojo.abstraction.intermediate;

/**
 * Legacy notification system - PushNotification with duplication
 * This represents the "before" state for the intermediate kata challenge
 */
public class PushNotification extends NotificationService {

    private String deviceId;
    
    public PushNotification(String message, String deviceId, String priority) {
        super(message, priority, "Push");
        this.deviceId = deviceId;
    }
    
    @Override
    protected boolean specificValidation() {
        // Specific validation
        if (deviceId == null || deviceId.length() < 10) {
            logError("Invalid device ID: " + deviceId);
            return false;
        }
        return true;
    }

    @Override
    protected boolean executeNotification() {
        // Send notification
        logAttempt();
        System.out.println("🔔 Sending push notification to device: " + deviceId);
        System.out.println("Alert: " + message);
        logSuccess();
        
        return true;
    }


    @Override
    protected void logAttempt() {
        System.out.println("[PUSH] Attempting to send " + priority + " priority message to: " + deviceId);
    }
    
}
