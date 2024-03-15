package org.emysilva;

import org.emysilva.fraud_detection_system.FraudDetectionSystem;
import org.emysilva.fraud_detection_system.TransactionEvent;

import java.io.*;
import java.time.Instant;

public class FraudDetectionSystemApp {

    private static final String FILE_NAME = "src/main/resources/dataset.txt";

    public static void main(String[] args) {

        FraudDetectionSystem fraudDetectionSystem = new FraudDetectionSystem();

        // Simulate transaction stream (replace with actual stream processing)
        // read data set from file
        try (FileInputStream fis = new FileInputStream(FILE_NAME);
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

    }
}
