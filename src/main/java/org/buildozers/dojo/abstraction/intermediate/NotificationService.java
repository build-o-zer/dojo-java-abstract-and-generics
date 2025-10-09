package org.buildozers.dojo.abstraction.intermediate;

public abstract class NotificationService {
    protected String message;
    protected String priority;
    protected String notificationType;

    public NotificationService(String message, String priority, String notificationType) {
        this.message = message;
        this.priority = priority;
        this.notificationType = notificationType;
    }

    protected final boolean sendNotification() {
        if (!commonValidation()) {
            return false;
        }

        if (!specificValidation()) {
            return false;
        }

        return executeNotification();
    }

    protected final boolean commonValidation() {
        if (message == null || message.trim().isEmpty()) {
            logError("Message cannot be empty");
            return false;
        }
        if (message.length() > 256) { // Push notifications have their own limit
            logError("Message too long. Max: 256 characters");
            return false;
        }
        return true;
    }

    protected abstract boolean specificValidation();
    protected abstract boolean executeNotification();


    protected  abstract void logAttempt();
    
    protected void logSuccess() {
        System.out.println("[" + notificationType + "] Message delivered successfully");
    }
    
    protected void logError(String error) {
        System.err.println("[" + notificationType + "] ERROR: " + error);
    }
}
