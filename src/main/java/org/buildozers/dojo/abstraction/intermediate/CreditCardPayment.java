package org.buildozers.dojo.abstraction.intermediate;

/**
 * Legacy payment processing - CreditCardPayment with lots of duplication
 * This represents the "before" state that students will refactor
 */
public class CreditCardPayment extends Payment {
    private String cardNumber;
    
    public CreditCardPayment(String cardNumber, double amount, String merchantId) {
        super(amount, merchantId, "CREDIT_CARD");
        this.cardNumber = cardNumber;
    }
    
    @Override
    protected boolean validateSpecificFields() {
        if (cardNumber == null || cardNumber.length() != 16) {
            logError("Invalid credit card number");
            return false;
        }
        return true;
    }

    @Override
    protected boolean executePayment() {
        logInfo("Processing credit card payment of $" + amount);
        return true;
    }

}
