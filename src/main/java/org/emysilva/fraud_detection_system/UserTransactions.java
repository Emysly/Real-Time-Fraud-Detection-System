package org.emysilva.fraud_detection_system;

import java.time.Instant;
import java.util.*;

public class UserTransactions {
    private static final long WINDOW_SIZE_MINUTES = 5;

    private static final double THRESHOLD_MULTIPLIER = 5;

    private static final long PING_PONG_WINDOW_MINUTES = 10;

    private static final long DISTINCT_SERVICE_COUNT = 3;

    private static Map<String, Integer> serviceCounts;
    private static Map<String, Double> totalAmounts;
    private static Deque<TransactionEvent> recentTransactions;

    static {
        serviceCounts = new HashMap<>();
        totalAmounts = new HashMap<>();
        recentTransactions = new ArrayDeque<>();
    }

    public UserTransactions() {
    }

    // Update user transactions
    public void update(TransactionEvent event) {
        String serviceID = event.getServiceID();
        double amount = event.getAmount();

        // Update service counts
        serviceCounts.put(serviceID, serviceCounts.getOrDefault(serviceID, 0) + 1);

        // Update total amounts
        totalAmounts.put(serviceID, totalAmounts.getOrDefault(serviceID, 0.0) + amount);

        // Add transaction to recent transactions
        recentTransactions.add(event);

        // Remove old transactions outside the window
        while (!recentTransactions.isEmpty() &&
               recentTransactions.getFirst().getTimestamp().isBefore(Instant.now().minusSeconds(WINDOW_SIZE_MINUTES * 60))) {
            TransactionEvent removedEvent = recentTransactions.removeFirst();
            String removedServiceID = removedEvent.getServiceID();

            // Adjust service counts and total amounts
            serviceCounts.put(removedServiceID, serviceCounts.get(removedServiceID) - 1);
            totalAmounts.put(removedServiceID, totalAmounts.get(removedServiceID) - removedEvent.getAmount());
        }
    }

    // Check if the user conducted transactions in more than 'count' distinct services within 'window' minutes
    public boolean hasMultipleServicesWithinWindow() {
        long windowSeconds = WINDOW_SIZE_MINUTES * 60;
        Instant windowStart = Instant.now().minusSeconds(windowSeconds);
        long distinctServices = serviceCounts.entrySet().stream()
                .filter(entry -> entry.getValue() > 0 && recentTransactions.stream()
                        .anyMatch(transaction -> transaction.getTimestamp().isAfter(windowStart) &&
                                transaction.getServiceID().equals(entry.getKey()))).count();
        return distinctServices > DISTINCT_SERVICE_COUNT;
    }

    // Check if the user's transaction amount exceeds 'multiplier' times the average of the last 24 hours
    public boolean hasTransactionAboveThreshold() {
        if (recentTransactions.isEmpty()) {
            return false;
        }

        // Calculate average transaction amount in the last 24 hours
        Instant twentyFourHoursAgo = Instant.now().minusSeconds(24 * 60 * 60);
        double sum = 0;
        int count = 0;

        // sum the total amount of previous transactions
        List<TransactionEvent> transactionEvents = recentTransactions.stream().toList();
        int size = transactionEvents.size() == 1 ? 1 : transactionEvents.size() - 1;
        for (int i = 0; i < size; i++) {
            if (transactionEvents.get(i).getTimestamp().isAfter(twentyFourHoursAgo)) {
                sum += transactionEvents.get(i).getAmount();
                count++;
            } else {
                break;
            }
        }

        double average = count > 0 ? sum / count : 0;

        // Check if the most recent transaction exceeds the threshold
        double latestAmount = recentTransactions.getLast().getAmount();

        // remove the flagged transaction from the transaction list
        if (latestAmount > average * THRESHOLD_MULTIPLIER) {
            TransactionEvent lastTransaction = recentTransactions.removeLast();
            String removedServiceID = lastTransaction.getServiceID();

            // Adjust service counts and total amounts
            serviceCounts.put(removedServiceID, serviceCounts.get(removedServiceID) - 1);
            totalAmounts.put(removedServiceID, totalAmounts.get(removedServiceID) - lastTransaction.getAmount());
        }

        return latestAmount > average * THRESHOLD_MULTIPLIER;
    }

    // Check if there's ping-pong activity (transactions bouncing back and forth between two services) within 'window' minutes
    public boolean hasPingPongActivityWithinWindow() {
        long windowSeconds = PING_PONG_WINDOW_MINUTES * 60;
        Instant windowStart = Instant.now().minusSeconds(windowSeconds);
        String firstService = null;
        for (TransactionEvent transaction : recentTransactions) {
            if (transaction.getTimestamp().isAfter(windowStart)) {
                if (firstService == null) {
                    firstService = transaction.getServiceID();
                } else if (!transaction.getServiceID().equals(firstService)) {
                    // If a different service is encountered within the window, it's not ping-pong activity
                    return false;
                }
            } else {
                break; // Stop checking once transactions outside the window are encountered
            }
        }
        return true; // If only one service is found within the window, it's ping-pong activity
    }
}