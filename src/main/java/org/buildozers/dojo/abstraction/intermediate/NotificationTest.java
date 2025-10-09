package org.buildozers.dojo.abstraction.intermediate;

public class NotificationTest {
    

    public static void main(String[] args) {
        NotificationService email = new EmailNotification("System maintenance scheduled" , "admin@company.com","HIGH");
        NotificationService sms = new SmsNotification("Your order is ready", "+1234567890", "MEDIUM");
        NotificationService push = new PushNotification("New message received", "device123456789", "LOW");
    
        System.out.println("Sending message");
        email.executeNotification();
        sms.executeNotification();
        push.executeNotification();
    
    }



}
