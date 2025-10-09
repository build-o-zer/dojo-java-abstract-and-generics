package org.buildozers.dojo.abstraction.intermediate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Simple JUnit tests for PushNotification class
 * Tests basic functionality before abstraction refactoring
 */
class PushNotificationTest {
    
    private PushNotification validNotification;
    
    @BeforeEach
    void setUp() {
        validNotification = new PushNotification("Push message!", "device123456789", "HIGH");
    }
    
    @Test
    void testValidNotificationSending() {
        assertTrue(validNotification.sendNotification());
    }
    
    @Test
    void testEmptyMessage() {
        PushNotification invalidNotification = new PushNotification("", "device123456789", "HIGH");
        assertFalse(invalidNotification.sendNotification());
    }
    
    @Test
    void testNullMessage() {
        PushNotification invalidNotification = new PushNotification(null, "device123456789", "HIGH");
        assertFalse(invalidNotification.sendNotification());
    }
    
    @Test
    void testWhitespaceOnlyMessage() {
        PushNotification invalidNotification = new PushNotification("   ", "device123456789", "HIGH");
        assertFalse(invalidNotification.sendNotification());
    }
    
    @Test
    void testMessageTooLong() {
        String longMessage = "a".repeat(257); // Over 256 character limit for push
        PushNotification invalidNotification = new PushNotification(longMessage, "device123456789", "HIGH");
        assertFalse(invalidNotification.sendNotification());
    }
    
    @Test
    void testInvalidDeviceId() {
        PushNotification invalidNotification = new PushNotification("Hello", "short", "HIGH");
        assertFalse(invalidNotification.sendNotification());
    }
    
    @Test
    void testNullDeviceId() {
        PushNotification invalidNotification = new PushNotification("Hello", null, "HIGH");
        assertFalse(invalidNotification.sendNotification());
    }
    
    @Test
    void testMaxLengthMessage() {
        String maxMessage = "a".repeat(256); // Exactly at push notification limit
        PushNotification maxLengthNotification = new PushNotification(maxMessage, "device123456789", "LOW");
        assertTrue(maxLengthNotification.sendNotification());
    }
    
    @Test
    void testMinimumValidDeviceId() {
        PushNotification minDeviceNotification = new PushNotification("Test", "1234567890", "MEDIUM");
        assertTrue(minDeviceNotification.sendNotification());
    }
}
