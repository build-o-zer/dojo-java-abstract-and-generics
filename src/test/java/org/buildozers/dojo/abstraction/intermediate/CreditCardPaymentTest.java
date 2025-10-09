package org.buildozers.dojo.abstraction.intermediate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Simple JUnit tests for CreditCardPayment class
 * Tests basic functionality before abstraction refactoring
 */
class CreditCardPaymentTest {
    
    private CreditCardPayment validPayment;
    
    @BeforeEach
    void setUp() {
        validPayment = new CreditCardPayment("1234567890123456", 250.75, "MERCHANT001");
    }
    
    @Test
    void testValidPaymentProcessing() {
        assertTrue(validPayment.processPayment());
    }
    
    @Test
    void testInvalidAmount() {
        CreditCardPayment invalidPayment = new CreditCardPayment("1234567890123456", -10.0, "MERCHANT001");
        assertFalse(invalidPayment.processPayment());
    }
    
    @Test
    void testZeroAmount() {
        CreditCardPayment zeroPayment = new CreditCardPayment("1234567890123456", 0.0, "MERCHANT001");
        assertFalse(zeroPayment.processPayment());
    }
    
    @Test
    void testInvalidMerchantId() {
        CreditCardPayment invalidPayment = new CreditCardPayment("1234567890123456", 100.0, "");
        assertFalse(invalidPayment.processPayment());
    }
    
    @Test
    void testNullMerchantId() {
        CreditCardPayment invalidPayment = new CreditCardPayment("1234567890123456", 100.0, null);
        assertFalse(invalidPayment.processPayment());
    }
    
    @Test
    void testInvalidCardNumber() {
        CreditCardPayment invalidPayment = new CreditCardPayment("123456789012345", 100.0, "MERCHANT001");
        assertFalse(invalidPayment.processPayment());
    }
    
    @Test
    void testNullCardNumber() {
        CreditCardPayment invalidPayment = new CreditCardPayment(null, 100.0, "MERCHANT001");
        assertFalse(invalidPayment.processPayment());
    }
    
    @Test
    void testTooLongCardNumber() {
        CreditCardPayment invalidPayment = new CreditCardPayment("12345678901234567", 100.0, "MERCHANT001");
        assertFalse(invalidPayment.processPayment());
    }
}
