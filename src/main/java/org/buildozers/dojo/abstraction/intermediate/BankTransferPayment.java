package org.buildozers.dojo.abstraction.intermediate;

/**
 * Legacy payment processing - BankTransferPayment with lots of duplication
 * This represents the "before" state that students will refactor
 */
public class BankTransferPayment extends Payment {
    private String accountNumber;
    private String routingNumber;
    
    public BankTransferPayment(String accountNumber, String routingNumber, double amount, String merchantId) {
        super(amount, merchantId, "BANK Payment");
        this.accountNumber = accountNumber;
        this.routingNumber = routingNumber;
    }
    
    @Override 
    protected boolean validateSpecificFields() {
        if (accountNumber == null || accountNumber.length() < 8) {
            logError("Invalid account number");
            return false;
        }
        if (routingNumber == null || routingNumber.length() != 9) {
            logError("Invalid routing number");
            return false;
        }
        return true;
    }

    @Override 
    protected boolean executePayment() {
        logInfo("Processing bank transfer payment of $" + amount);
        return true;
    }

}
