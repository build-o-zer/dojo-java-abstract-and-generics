package org.buildozers.dojo.abstraction.intermediate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Simple JUnit tests for EmailNotification class
 * Tests basic functionality before abstraction refactoring
 */
class EmailNotificationTest {
    
    private EmailNotification validNotification;
    
    @BeforeEach
    void setUp() {
        validNotification = new EmailNotification("Hello World!", "test@example.com", "HIGH");
    }
    
    @Test
    void testValidNotificationSending() {
        assertTrue(validNotification.sendNotification());
    }
    
    @Test
    void testEmptyMessage() {
        EmailNotification invalidNotification = new EmailNotification("", "test@example.com", "HIGH");
        assertFalse(invalidNotification.sendNotification());
    }
    
    @Test
    void testNullMessage() {
        EmailNotification invalidNotification = new EmailNotification(null, "test@example.com", "HIGH");
        assertFalse(invalidNotification.sendNotification());
    }
    
    @Test
    void testWhitespaceOnlyMessage() {
        EmailNotification invalidNotification = new EmailNotification("   ", "test@example.com", "HIGH");
        assertFalse(invalidNotification.sendNotification());
    }
    
    @Test
    void testMessageTooLong() {
        String longMessage = "a".repeat(1001); // Over 1000 character limit
        EmailNotification invalidNotification = new EmailNotification(longMessage, "test@example.com", "HIGH");
        assertFalse(invalidNotification.sendNotification());
    }
    
    @Test
    void testInvalidEmail() {
        EmailNotification invalidNotification = new EmailNotification("Hello", "invalid-email", "HIGH");
        assertFalse(invalidNotification.sendNotification());
    }
    
    @Test
    void testNullEmail() {
        EmailNotification invalidNotification = new EmailNotification("Hello", null, "HIGH");
        assertFalse(invalidNotification.sendNotification());
    }
    
    @Test
    void testMaxLengthMessage() {
        String maxMessage = "a".repeat(1000); // Exactly at limit
        EmailNotification maxLengthNotification = new EmailNotification(maxMessage, "test@example.com", "LOW");
        assertTrue(maxLengthNotification.sendNotification());
    }
}
