package org.buildozers.dojo.abstraction.intermediate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Simple JUnit tests for PayPalPayment class
 * Tests basic functionality before abstraction refactoring
 */
class PayPalPaymentTest {
    
    private PayPalPayment validPayment;
    
    @BeforeEach
    void setUp() {
        validPayment = new PayPalPayment("user@example.com", 150.25, "MERCHANT001");
    }
    
    @Test
    void testValidPaymentProcessing() {
        assertTrue(validPayment.processPayment());
    }
    
    @Test
    void testInvalidAmount() {
        PayPalPayment invalidPayment = new PayPalPayment("user@example.com", -25.0, "MERCHANT001");
        assertFalse(invalidPayment.processPayment());
    }
    
    @Test
    void testZeroAmount() {
        PayPalPayment zeroPayment = new PayPalPayment("user@example.com", 0.0, "MERCHANT001");
        assertFalse(zeroPayment.processPayment());
    }
    
    @Test
    void testInvalidMerchantId() {
        PayPalPayment invalidPayment = new PayPalPayment("user@example.com", 100.0, "");
        assertFalse(invalidPayment.processPayment());
    }
    
    @Test
    void testNullMerchantId() {
        PayPalPayment invalidPayment = new PayPalPayment("user@example.com", 100.0, null);
        assertFalse(invalidPayment.processPayment());
    }
    
    @Test
    void testInvalidEmail() {
        PayPalPayment invalidPayment = new PayPalPayment("invalid-email", 100.0, "MERCHANT001");
        assertFalse(invalidPayment.processPayment());
    }
    
    @Test
    void testNullEmail() {
        PayPalPayment invalidPayment = new PayPalPayment(null, 100.0, "MERCHANT001");
        assertFalse(invalidPayment.processPayment());
    }
    
    @Test
    void testEmptyEmail() {
        PayPalPayment invalidPayment = new PayPalPayment("", 100.0, "MERCHANT001");
        assertFalse(invalidPayment.processPayment());
    }
}
