package org.buildozers.dojo.abstraction.intermediate;

/**
 * Legacy notification system - PushNotification with duplication
 * This represents the "before" state for the intermediate kata challenge
 */
public class PushNotification {
    private String message;
    private String deviceId;
    private String priority;
    
    public PushNotification(String message, String deviceId, String priority) {
        this.message = message;
        this.deviceId = deviceId;
        this.priority = priority;
    }
    
    public boolean sendNotification() {
        // Common validation (DUPLICATED AGAIN!)
        if (message == null || message.trim().isEmpty()) {
            logError("Message cannot be empty");
            return false;
        }
        if (message.length() > 256) { // Push notifications have their own limit
            logError("Message too long. Max: 256 characters");
            return false;
        }
        
        // Specific validation
        if (deviceId == null || deviceId.length() < 10) {
            logError("Invalid device ID: " + deviceId);
            return false;
        }
        
        // Send notification
        logAttempt();
        System.out.println("🔔 Sending push notification to device: " + deviceId);
        System.out.println("Alert: " + message);
        logSuccess();
        
        return true;
    }
    
    private void logAttempt() {
        System.out.println("[PUSH] Attempting to send " + priority + " priority message to: " + deviceId);
    }
    
    private void logSuccess() {
        System.out.println("[PUSH] Message delivered successfully");
    }
    
    private void logError(String error) {
        System.err.println("[PUSH] ERROR: " + error);
    }
}
