# Java Kata: Abstract Classes for Refactoring

**Subject:** Refactoring with Abstract Classes  
**Goal:** Learn to identify code duplication and refactor using abstract classes  
**Level:** Intermediate  
**Duration:** 25-30 minutes  

---

## 道場での対話 (Dojo Dialogue)

### Scene: The Legacy Code Temple

*The deshi stares at a sprawling codebase, multiple similar classes scattered across the screen. The sensei approaches, carrying the wisdom of countless refactoring battles.*

**Sensei:** "I sense disturbance in your code, young deshi. What legacy haunts your workspace?"

**Deshi:** "Sensei, I have inherited a payment processing system. There are classes for CreditCardPayment, PayPalPayment, and BankTransferPayment. Each has similar validation and logging, but slightly different processing. The duplication is overwhelming!"

**Sensei:** "Ah, the classic refactoring challenge. You have discovered why abstract classes are not just for new code, but for healing existing wounds. Let me teach you the art of therapeutic abstraction."

---

## The Teaching

**Sensei:** "First, examine the patient - your current code structure:"

```java
// Legacy code - notice the duplication
public class CreditCardPayment {
    private String cardNumber;
    private double amount;
    private String merchantId;
    
    public CreditCardPayment(String cardNumber, double amount, String merchantId) {
        this.cardNumber = cardNumber;
        this.amount = amount;
        this.merchantId = merchantId;
    }
    
    public boolean processPayment() {
        // Common validation logic
        if (amount <= 0) {
            logError("Invalid amount: " + amount);
            return false;
        }
        if (merchantId == null || merchantId.isEmpty()) {
            logError("Invalid merchant ID");
            return false;
        }
        
        // Specific processing
        if (cardNumber.length() != 16) {
            logError("Invalid card number");
            return false;
        }
        
        // Simulate processing
        logInfo("Processing credit card payment of $" + amount);
        return true;
    }
    
    private void logInfo(String message) {
        System.out.println("[INFO] " + message);
    }
    
    private void logError(String message) {
        System.err.println("[ERROR] " + message);
    }
}

public class PayPalPayment {
    private String email;
    private double amount;
    private String merchantId;
    
    // Similar constructor, validation, and logging methods...
    // 90% duplication with CreditCardPayment!
}
```

**Deshi:** "Sensei, I can see the pattern! The validation and logging are nearly identical, but the core processing differs."

**Sensei:** "Excellent observation! This is the perfect scenario for abstract class refactoring. Let me show you the transformation."

---

## The Practice (実践)

**Sensei:** "We shall heal this code in stages, like treating a patient. First, identify what is common:"

### Step 1: Extract the Abstract Base

```java
public abstract class Payment {
    protected double amount;
    protected String merchantId;
    protected String paymentType;
    
    public Payment(double amount, String merchantId, String paymentType) {
        this.amount = amount;
        this.merchantId = merchantId;
        this.paymentType = paymentType;
    }
    
    // Template method - defines the payment flow
    public final boolean processPayment() {
        if (!validateCommonFields()) {
            return false;
        }
        
        if (!validateSpecificFields()) {
            return false;
        }
        
        return executePayment();
    }
    
    // Common validation
    protected boolean validateCommonFields() {
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
    
    // Abstract methods for subclass-specific behavior
    protected abstract boolean validateSpecificFields();
    protected abstract boolean executePayment();
    
    // Shared logging methods
    protected void logInfo(String message) {
        System.out.println("[INFO] [" + paymentType + "] " + message);
    }
    
    protected void logError(String message) {
        System.err.println("[ERROR] [" + paymentType + "] " + message);
    }
}
```

<details>
<summary><button>🧙‍♂️ Advice from Sensei</button></summary>

**Sensei explains:** "Notice the `final` keyword on `processPayment()`. This is the Template Method pattern - it defines the algorithm structure while allowing subclasses to customize specific steps. The `final` prevents subclasses from changing the overall flow."

</details>

### Step 2: Refactor Concrete Classes

```java
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
        // Simulate credit card processing
        return true;
    }
}
```

<details>
<summary><button>🧙‍♂️ Advice from Sensei</button></summary>

**Sensei advises:** "See how clean this becomes? We eliminated all the duplicate validation and logging code. Each subclass now focuses only on what makes it unique. This is the essence of good abstraction."

</details>

---

## Common Pitfalls (落とし穴)

**Sensei warns:** "Refactoring requires patience and wisdom. Avoid these traps:"

1. **Over-abstracting too early**
   ```java
   // Don't create abstractions until you see real duplication
   // Wait for at least 2-3 similar classes before abstracting
   ```

2. **Making everything abstract**
   ```java
   // Bad: Not everything needs to be abstract
   public abstract class Payment {
       public abstract String getPaymentType(); // This could just be a field
       public abstract double getAmount();      // This could just be a getter
   }
   ```

3. **Breaking existing contracts during refactoring**
   ```java
   // Be careful when changing method signatures
   // Consider using @Deprecated first, then remove in next version
   ```

---

## The Challenge (挑戦)

**Sensei:** "Now, deshi, demonstrate your refactoring mastery:"

### Setup

You have a notification system with email, SMS, and push notification classes that are 80% duplicated code.

<details>
<summary><button>🎯 Solution from Sensei - Step 1</button></summary>

Create the abstract base class:

```java
package org.buildozers.dojo.abstraction;

public abstract class NotificationService {
    protected String message;
    protected String recipient;
    protected String priority;
    
    public NotificationService(String message, String recipient, String priority) {
        this.message = message;
        this.recipient = recipient;
        this.priority = priority;
    }
    
    public final boolean sendNotification() {
        if (!validateMessage()) {
            return false;
        }
        
        if (!validateRecipient()) {
            return false;
        }
        
        logAttempt();
        boolean success = deliverMessage();
        
        if (success) {
            logSuccess();
        } else {
            logFailure();
        }
        
        return success;
    }
    
    protected boolean validateMessage() {
        if (message == null || message.trim().isEmpty()) {
            logError("Message cannot be empty");
            return false;
        }
        if (message.length() > getMaxMessageLength()) {
            logError("Message too long. Max: " + getMaxMessageLength());
            return false;
        }
        return true;
    }
    
    protected abstract boolean validateRecipient();
    protected abstract boolean deliverMessage();
    protected abstract int getMaxMessageLength();
    
    protected void logAttempt() {
        System.out.println("[" + getServiceName() + "] Attempting to send " + priority + " priority message to: " + recipient);
    }
    
    protected void logSuccess() {
        System.out.println("[" + getServiceName() + "] Message delivered successfully");
    }
    
    protected void logFailure() {
        System.err.println("[" + getServiceName() + "] Failed to deliver message");
    }
    
    protected void logError(String error) {
        System.err.println("[" + getServiceName() + "] ERROR: " + error);
    }
    
    protected abstract String getServiceName();
}
```

</details>

### Your Mission

1. Analyze the existing notification classes (provided in initial state)
2. Identify common patterns and duplication
3. Create an abstract `NotificationService` base class
4. Refactor the concrete classes to extend the abstract base
5. Ensure all functionality is preserved

<details>
<summary><button>🎯 Solution from Sensei - Step 2</button></summary>

Implement the concrete notification services:

```java
// EmailNotificationService.java
package org.buildozers.dojo.abstraction;

import java.util.regex.Pattern;

public class EmailNotificationService extends NotificationService {
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    
    public EmailNotificationService(String message, String email, String priority) {
        super(message, email, priority);
    }
    
    @Override
    protected boolean validateRecipient() {
        if (!EMAIL_PATTERN.matcher(recipient).matches()) {
            logError("Invalid email address: " + recipient);
            return false;
        }
        return true;
    }
    
    @Override
    protected boolean deliverMessage() {
        // Simulate email sending
        System.out.println("📧 Sending email to: " + recipient);
        System.out.println("Subject: " + priority.toUpperCase() + " Notification");
        System.out.println("Body: " + message);
        return true;
    }
    
    @Override
    protected int getMaxMessageLength() {
        return 1000; // Email can be longer
    }
    
    @Override
    protected String getServiceName() {
        return "EMAIL";
    }
}

// SmsNotificationService.java
package org.buildozers.dojo.abstraction;

public class SmsNotificationService extends NotificationService {
    
    public SmsNotificationService(String message, String phoneNumber, String priority) {
        super(message, phoneNumber, priority);
    }
    
    @Override
    protected boolean validateRecipient() {
        if (recipient == null || !recipient.matches("\\+?[1-9]\\d{1,14}")) {
            logError("Invalid phone number: " + recipient);
            return false;
        }
        return true;
    }
    
    @Override
    protected boolean deliverMessage() {
        // Simulate SMS sending
        System.out.println("📱 Sending SMS to: " + recipient);
        System.out.println("Message: " + message);
        return true;
    }
    
    @Override
    protected int getMaxMessageLength() {
        return 160; // SMS length limit
    }
    
    @Override
    protected String getServiceName() {
        return "SMS";
    }
}
```

</details>

<details>
<summary><button>🎯 Solution from Sensei - Step 3</button></summary>

Complete the refactoring with push notifications and test:

```java
// PushNotificationService.java
package org.buildozers.dojo.abstraction;

public class PushNotificationService extends NotificationService {
    
    public PushNotificationService(String message, String deviceId, String priority) {
        super(message, deviceId, priority);
    }
    
    @Override
    protected boolean validateRecipient() {
        if (recipient == null || recipient.length() < 10) {
            logError("Invalid device ID: " + recipient);
            return false;
        }
        return true;
    }
    
    @Override
    protected boolean deliverMessage() {
        // Simulate push notification
        System.out.println("🔔 Sending push notification to device: " + recipient);
        System.out.println("Alert: " + message);
        return true;
    }
    
    @Override
    protected int getMaxMessageLength() {
        return 256; // Push notification limit
    }
    
    @Override
    protected String getServiceName() {
        return "PUSH";
    }
}

// NotificationTest.java
package org.buildozers.dojo.abstraction;

public class NotificationTest {
    public static void main(String[] args) {
        NotificationService[] services = {
            new EmailNotificationService("System maintenance scheduled", "admin@company.com", "HIGH"),
            new SmsNotificationService("Your order is ready!", "+1234567890", "MEDIUM"),
            new PushNotificationService("New message received", "device123456789", "LOW")
        };
        
        for (NotificationService service : services) {
            System.out.println("\n" + "=".repeat(50));
            service.sendNotification();
        }
    }
}
```

</details>

### Expected Result

```
==================================================
[EMAIL] Attempting to send HIGH priority message to: admin@company.com
📧 Sending email to: admin@company.com
Subject: HIGH Notification
Body: System maintenance scheduled
[EMAIL] Message delivered successfully

==================================================
[SMS] Attempting to send MEDIUM priority message to: +1234567890
📱 Sending SMS to: +1234567890
Message: Your order is ready!
[SMS] Message delivered successfully

==================================================
[PUSH] Attempting to send LOW priority message to: device123456789
🔔 Sending push notification to device: device123456789
Alert: New message received
[PUSH] Message delivered successfully
```

---

## Reflection (反省)

**Sensei:** "What transformation have you witnessed today, deshi?"

**Deshi:** "I have learned that refactoring is like healing - we identify the pain points (duplication) and apply the right medicine (abstraction). The code becomes not just cleaner, but more maintainable and extensible."

**Sensei:** "Indeed. Refactoring with abstract classes is a powerful technique. You eliminate duplication while creating a framework for future extensions."

### Key Takeaways

- 🔄 Refactoring reduces duplication and improves maintainability
- 🏗️ Abstract classes provide structure during refactoring
- 📋 Template Method pattern controls algorithm flow
- ⚡ Refactored code is easier to extend and modify
- 🎯 Focus on common behavior, abstract the differences

---

## Next Steps

Continue your journey with:
- [Java Kata: Advanced Abstraction Patterns](./java-abstraction-advanced.md)
- [Java Kata: Design Patterns with Abstraction](./java-design-patterns.md)

---

*"Refactoring is not about changing what the code does, but about revealing what it was always meant to be."* - Refactoring Dojo Wisdom
