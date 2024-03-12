package org.emysilva;

import org.emysilva.fraud_detection_system.FraudDetectionSystem;
import org.emysilva.fraud_detection_system.TransactionEvent;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class FraudDetectionSystemApp {
    public static void main(String[] args) {
        FraudDetectionSystem fraudDetectionSystem = new FraudDetectionSystem();

        // Simulate transaction stream (replace with actual stream processing)
        // Example transaction events
        TransactionEvent[] transactionEvents = {
                new TransactionEvent(Instant.ofEpochSecond(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)), 150.00, "user1", "serviceA"),
                new TransactionEvent(Instant.ofEpochSecond(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)), 4500.00, "user2", "serviceB"),
                new TransactionEvent(Instant.ofEpochSecond(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)), 75.00, "user1", "serviceC"),
                new TransactionEvent(Instant.ofEpochSecond(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)), 3000.00, "user3", "serviceA"),
                new TransactionEvent(Instant.ofEpochSecond(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)), 200.00, "user1", "serviceB"),
                new TransactionEvent(Instant.ofEpochSecond(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)), 4800.00, "user2", "serviceC"),
                new TransactionEvent(Instant.ofEpochSecond(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)), 100.00, "user4", "serviceA"),
                new TransactionEvent(Instant.ofEpochSecond(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)), 4900.00, "user3", "serviceB"),
                new TransactionEvent(Instant.ofEpochSecond(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)), 120.00, "user1", "serviceD"),
                new TransactionEvent(Instant.ofEpochSecond(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)), 5000.00, "user3", "serviceC"),
        };

        // Process each transaction event
        for (TransactionEvent event : transactionEvents) {
            fraudDetectionSystem.processTransaction(event);
        }
    }
}
