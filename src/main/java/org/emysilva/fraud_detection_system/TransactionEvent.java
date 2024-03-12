package org.emysilva.fraud_detection_system;

import java.time.Instant;

public class TransactionEvent {
    private Instant timestamp;
    private double amount;
    private String userID;
    private String serviceID;

    // Constructor
    public TransactionEvent(Instant timestamp, double amount, String userID, String serviceID) {
        this.timestamp = timestamp;
        this.amount = amount;
        this.userID = userID;
        this.serviceID = serviceID;
    }

    // Getters
    public Instant getTimestamp() {
        return timestamp;
    }

    public double getAmount() {
        return amount;
    }

    public String getUserID() {
        return userID;
    }

    public String getServiceID() {
        return serviceID;
    }
}