package org.buildozers.dojo.abstraction.intermediate;

/**
 * Legacy payment processing - PayPalPayment with lots of duplication
 * This represents the "before" state that students will refactor
 */
public class PayPalPayment {
    private String email;
    private double amount;
    private String merchantId;
    
    public PayPalPayment(String email, double amount, String merchantId) {
        this.email = email;
        this.amount = amount;
        this.merchantId = merchantId;
    }
    
    public boolean processPayment() {
        // Common validation logic (DUPLICATED!)
        if (amount <= 0) {
            logError("Invalid amount: " + amount);
            return false;
        }
        if (merchantId == null || merchantId.isEmpty()) {
            logError("Invalid merchant ID");
            return false;
        }
        
        // Specific validation
        if (email == null || !email.contains("@")) {
            logError("Invalid PayPal email address");
            return false;
        }
        
        // Simulate processing
        logInfo("Processing PayPal payment of $" + amount);
        return true;
    }
    
    private void logInfo(String message) {
        System.out.println("[INFO] [PAYPAL] " + message);
    }
    
    private void logError(String message) {
        System.err.println("[ERROR] [PAYPAL] " + message);
    }
}
