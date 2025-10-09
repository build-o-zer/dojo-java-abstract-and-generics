package org.buildozers.dojo.abstraction.intermediate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Simple JUnit tests for BankTransferPayment class
 * Tests basic functionality before abstraction refactoring
 */
class BankTransferPaymentTest {
    
    private BankTransferPayment validPayment;
    
    @BeforeEach
    void setUp() {
        validPayment = new BankTransferPayment("12345678", "123456789", 100.0, "MERCHANT001");
    }
    
    @Test
    void testValidPaymentProcessing() {
        assertTrue(validPayment.processPayment());
    }
    
    @Test
    void testInvalidAmount() {
        BankTransferPayment invalidPayment = new BankTransferPayment("12345678", "123456789", -50.0, "MERCHANT001");
        assertFalse(invalidPayment.processPayment());
    }
    
    @Test
    void testZeroAmount() {
        BankTransferPayment zeroPayment = new BankTransferPayment("12345678", "123456789", 0.0, "MERCHANT001");
        assertFalse(zeroPayment.processPayment());
    }
    
    @Test
    void testInvalidMerchantId() {
        BankTransferPayment invalidPayment = new BankTransferPayment("12345678", "123456789", 100.0, "");
        assertFalse(invalidPayment.processPayment());
    }
    
    @Test
    void testNullMerchantId() {
        BankTransferPayment invalidPayment = new BankTransferPayment("12345678", "123456789", 100.0, null);
        assertFalse(invalidPayment.processPayment());
    }
    
    @Test
    void testInvalidAccountNumber() {
        BankTransferPayment invalidPayment = new BankTransferPayment("1234567", "123456789", 100.0, "MERCHANT001");
        assertFalse(invalidPayment.processPayment());
    }
    
    @Test
    void testInvalidRoutingNumber() {
        BankTransferPayment invalidPayment = new BankTransferPayment("12345678", "12345678", 100.0, "MERCHANT001");
        assertFalse(invalidPayment.processPayment());
    }
    
    @Test
    void testNullAccountNumber() {
        BankTransferPayment invalidPayment = new BankTransferPayment(null, "123456789", 100.0, "MERCHANT001");
        assertFalse(invalidPayment.processPayment());
    }
}
