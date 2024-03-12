package org.emysilva.fraud_detection_system;

import java.util.HashMap;
import java.util.Map;

public class FraudDetectionSystem {
    private Map<String, UserTransactions> userTransactionsMap;

    // Constructor
    public FraudDetectionSystem() {
        userTransactionsMap = new HashMap<>();
    }

    // Method to process a transaction event
    public void processTransaction(TransactionEvent event) {
        String userID = event.getUserID();
        userTransactionsMap.computeIfAbsent(userID, k -> new UserTransactions());
        UserTransactions userTransactions = userTransactionsMap.get(userID);

        // Update user transactions
        userTransactions.update(event);

        // Check for fraudulent patterns
        if (userTransactions.hasMultipleServicesWithinWindow()) {
            System.out.println("Flag user " + userID + ": conducting transactions in more than 3 distinct services within a 5-minute window.");
        }

        if (userTransactions.hasTransactionAboveThreshold()) {
            System.out.println("Flag user " + userID + ": transaction significantly higher than typical amount.");
        }

        if (userTransactions.hasPingPongActivityWithinWindow()) {
            System.out.println("Flag user " + userID + ": ping-pong activity detected within a 10-minute window.");
        }
    }
}