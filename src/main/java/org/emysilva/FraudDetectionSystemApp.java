package org.emysilva;

import org.emysilva.fraud_detection_system.FraudDetectionSystem;
import org.emysilva.fraud_detection_system.TransactionEvent;

import java.io.*;
import java.time.Instant;

public class FraudDetectionSystemApp {
    public static void main(String[] args) {

        FraudDetectionSystem fraudDetectionSystem = new FraudDetectionSystem();

        // Simulate transaction stream (replace with actual stream processing)
        // read data set from file
        String fileName = "src/main/resources/dataset.txt";

        try (FileInputStream fis = new FileInputStream(fileName);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr)) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.split(",");
                TransactionEvent event = new TransactionEvent(Instant.ofEpochSecond(Long.parseLong(words[words.length - 4])), Double.parseDouble(words[words.length - 3]), words[words.length - 2], words[words.length - 1]);
                fraudDetectionSystem.processTransaction(event);
            }
        } catch (IOException e) {
            System.out.println("An IO error occurred while reading file " + e.getMessage());
        }


        // Simulate transaction stream (replace with actual stream processing)
        // Example transaction events
//        TransactionEvent[] transactionEvents = {
//                new TransactionEvent(Instant.ofEpochSecond(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)), 150.00, "user1", "serviceA"),
//                new TransactionEvent(Instant.ofEpochSecond(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)), 4500.00, "user2", "serviceB"),
//                new TransactionEvent(Instant.ofEpochSecond(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)), 75.00, "user1", "serviceC"),
//                new TransactionEvent(Instant.ofEpochSecond(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)), 3000.00, "user3", "serviceA"),
//                new TransactionEvent(Instant.ofEpochSecond(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)), 200.00, "user1", "serviceB"),
//                new TransactionEvent(Instant.ofEpochSecond(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)), 4800.00, "user2", "serviceC"),
//                new TransactionEvent(Instant.ofEpochSecond(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)), 100.00, "user4", "serviceA"),
//                new TransactionEvent(Instant.ofEpochSecond(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)), 4900.00, "user3", "serviceB"),
//                new TransactionEvent(Instant.ofEpochSecond(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)), 120.00, "user1", "serviceD"),
//                new TransactionEvent(Instant.ofEpochSecond(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)), 5000.00, "user3", "serviceC"),
//        };
//
//        // Process each transaction event
//        for (TransactionEvent event : transactionEvents) {
//            fraudDetectionSystem.processTransaction(event);
//        }
    }
}
