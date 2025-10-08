package org.buildozers.dojo.abstraction.intermediate;

/**
 * Legacy payment processing - CreditCardPayment with lots of duplication
 * This represents the "before" state that students will refactor
 */
public class CreditCardPayment {
    private String cardNumber;
    private double amount;
    private String merchantId;
    
    public CreditCardPayment(String cardNumber, double amount, String merchantId) {
        this.cardNumber = cardNumber;
        this.amount = amount;
        this.merchantId = merchantId;
    }
    
    public boolean processPayment() {
        // Common validation logic (duplicated across payment types)
        if (amount <= 0) {
            logError("Invalid amount: " + amount);
            return false;
        }
        if (merchantId == null || merchantId.isEmpty()) {
            logError("Invalid merchant ID");
            return false;
        }
        
        // Specific validation
        if (cardNumber == null || cardNumber.length() != 16) {
            logError("Invalid credit card number");
            return false;
        }
        
        // Simulate processing
        logInfo("Processing credit card payment of $" + amount);
        return true;
    }
    
    private void logInfo(String message) {
        System.out.println("[INFO] [CREDIT_CARD] " + message);
    }
    
    private void logError(String message) {
        System.err.println("[ERROR] [CREDIT_CARD] " + message);
    }
}
