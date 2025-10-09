package org.buildozers.dojo.abstraction.intermediate;

public abstract class Payment {
    protected double amount;
    protected String merchantId;
    protected String paymentType;

    public Payment(double amount, String merchantId, String paymentType) {
        this.amount = amount;
        this.merchantId = merchantId;
        this.paymentType = paymentType;
    }
 
    public final boolean processPayment() {
        if (!validateCommonFields()) {
            return false;
        }

        if (!validateSpecificFields()) {
            return false;
        }

        return executePayment();
    }

    protected boolean validateCommonFields(){
        if (amount <= 0) {
            logError("Invalid amount: " + amount);
            return false;
        }

        if (merchantId == null || merchantId.isEmpty()) {
            logError("Invalid merchant ID");
            return false;
        }
        return true;
    }

    protected abstract boolean validateSpecificFields();
    protected abstract boolean executePayment();

    protected void logInfo(String message) {
        System.out.println("[INFO] [" + paymentType + "] " + message);
    }
    
    protected void logError(String message) {
        System.err.println("[ERROR] [" + paymentType + "] " + message);
    }

}
