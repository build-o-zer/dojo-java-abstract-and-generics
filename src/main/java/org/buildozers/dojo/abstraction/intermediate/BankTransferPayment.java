package org.buildozers.dojo.abstraction.intermediate;

/**
 * Legacy payment processing - BankTransferPayment with lots of duplication
 * This represents the "before" state that students will refactor
 */
public class BankTransferPayment {
    private String accountNumber;
    private String routingNumber;
    private double amount;
    private String merchantId;
    
    public BankTransferPayment(String accountNumber, String routingNumber, double amount, String merchantId) {
        this.accountNumber = accountNumber;
        this.routingNumber = routingNumber;
        this.amount = amount;
        this.merchantId = merchantId;
    }
    
    public boolean processPayment() {
        // Common validation logic (DUPLICATED AGAIN!)
        if (amount <= 0) {
            logError("Invalid amount: " + amount);
            return false;
        }
        if (merchantId == null || merchantId.isEmpty()) {
            logError("Invalid merchant ID");
            return false;
        }
        
        // Specific validation
        if (accountNumber == null || accountNumber.length() < 8) {
            logError("Invalid account number");
            return false;
        }
        if (routingNumber == null || routingNumber.length() != 9) {
            logError("Invalid routing number");
            return false;
        }
        
        // Simulate processing
        logInfo("Processing bank transfer payment of $" + amount);
        return true;
    }
    
    private void logInfo(String message) {
        System.out.println("[INFO] [BANK_TRANSFER] " + message);
    }
    
    private void logError(String message) {
        System.err.println("[ERROR] [BANK_TRANSFER] " + message);
    }
}
