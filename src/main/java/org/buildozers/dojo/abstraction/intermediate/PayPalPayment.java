package org.buildozers.dojo.abstraction.intermediate;

/**
 * Legacy payment processing - PayPalPayment with lots of duplication
 * This represents the "before" state that students will refactor
 */
public class PayPalPayment extends Payment {
    private String email;
    
    public PayPalPayment(String email, double amount, String merchantId) {
        super(amount, merchantId, "PayPal Payment");
        this.email = email;
    }
    
    @Override
    protected boolean validateSpecificFields() {
        if (email == null || !email.contains("@")) {
            logError("Invalid PayPal email address");
            return false;
        }
        return true;
    }

    @Override
    protected boolean executePayment() {
        logInfo("Processing PayPal payment of $" + amount);
        return true;
    }

}
