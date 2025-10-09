package org.buildozers.dojo.abstraction.intermediate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Simple JUnit tests for SmsNotification class
 * Tests basic functionality before abstraction refactoring
 */
class SmsNotificationTest {
    
    private SmsNotification validNotification;
    
    @BeforeEach
    void setUp() {
        validNotification = new SmsNotification("Hello SMS!", "+1234567890", "HIGH");
    }
    
    @Test
    void testValidNotificationSending() {
        assertTrue(validNotification.sendNotification());
    }
    
    @Test
    void testEmptyMessage() {
        SmsNotification invalidNotification = new SmsNotification("", "+1234567890", "HIGH");
        assertFalse(invalidNotification.sendNotification());
    }
    
    @Test
    void testNullMessage() {
        SmsNotification invalidNotification = new SmsNotification(null, "+1234567890", "HIGH");
        assertFalse(invalidNotification.sendNotification());
    }
    
    @Test
    void testWhitespaceOnlyMessage() {
        SmsNotification invalidNotification = new SmsNotification("   ", "+1234567890", "HIGH");
        assertFalse(invalidNotification.sendNotification());
    }
    
    @Test
    void testMessageTooLong() {
        String longMessage = "a".repeat(161); // Over 160 character limit for SMS
        SmsNotification invalidNotification = new SmsNotification(longMessage, "+1234567890", "HIGH");
        assertFalse(invalidNotification.sendNotification());
    }
    
    @Test
    void testInvalidPhoneNumber() {
        SmsNotification invalidNotification = new SmsNotification("Hello", "invalid-phone", "HIGH");
        assertFalse(invalidNotification.sendNotification());
    }
    
    @Test
    void testNullPhoneNumber() {
        SmsNotification invalidNotification = new SmsNotification("Hello", null, "HIGH");
        assertFalse(invalidNotification.sendNotification());
    }
    
    @Test
    void testMaxLengthMessage() {
        String maxMessage = "a".repeat(160); // Exactly at SMS limit
        SmsNotification maxLengthNotification = new SmsNotification(maxMessage, "+1234567890", "LOW");
        assertTrue(maxLengthNotification.sendNotification());
    }
    
    @Test
    void testDifferentPhoneFormats() {
        SmsNotification withoutPlus = new SmsNotification("Test", "1234567890", "MEDIUM");
        assertTrue(withoutPlus.sendNotification());
    }
}
