package org.buildozers.dojo.abstraction.intermediate;

/**
 * Test class demonstrating the duplication problem
 * Students will see how much code is repeated across payment types
 */
public class PaymentTestBefore {
    private static final String MERCHANT_ID = "MERCHANT_123";
    
    public static void main(String[] args) {
        System.out.println("=== Before Refactoring - Payment Processing System ===");
        
        Payment creditCard = new CreditCardPayment("1234567890123456", 100.0, MERCHANT_ID);
        Payment paypal = new PayPalPayment("user@example.com", 75.0, MERCHANT_ID);
        Payment bankTransfer = new BankTransferPayment("12345678", "123456789", 200.0, MERCHANT_ID);
        
        System.out.println("\nProcessing payments...");
        creditCard.processPayment();
        paypal.processPayment();
        bankTransfer.processPayment();
        
        System.out.println("\n=== PROBLEMS IDENTIFIED ===");
        System.out.println("1. Amount validation is duplicated in all classes");
        System.out.println("2. Merchant ID validation is duplicated in all classes");
        System.out.println("3. Logging methods are duplicated in all classes");
        System.out.println("4. Overall processing flow is duplicated");
        System.out.println("\n⚡ SOLUTION: Use abstract classes with Template Method pattern!");
    }
}
