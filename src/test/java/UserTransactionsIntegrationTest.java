import org.emysilva.fraud_detection_system.TransactionEvent;
import org.emysilva.fraud_detection_system.UserTransactions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserTransactionsIntegrationTest {

    @Test
    public void testHasPingPongActivityWithinWindow() {

        // Create mock TransactionEvent object
        TransactionEvent event1 = Mockito.mock(TransactionEvent.class);
        when(event1.getTimestamp()).thenReturn(Instant.now().minusSeconds(60)); // 1 minute ago
        when(event1.getServiceID()).thenReturn("serviceA");

        // Create UserTransactions object
        UserTransactions userTransactions = new UserTransactions();

        // Test update method
        userTransactions.update(event1);

        // Test hasPingPongActivityWithinWindow method
        assertTrue(userTransactions.hasPingPongActivityWithinWindow()); // Ping-pong activity in the last 10 minutes
    }

    @Test
    public void testHasMultipleServicesWithinWindow() {

        // Create mock TransactionEvent objects
        TransactionEvent event1 = Mockito.mock(TransactionEvent.class);
        when(event1.getTimestamp()).thenReturn(Instant.now().minusSeconds(60)); // 1 minute ago
        when(event1.getAmount()).thenReturn(100.00);
        when(event1.getServiceID()).thenReturn("serviceA");
        when(event1.getUserID()).thenReturn("user1");

        TransactionEvent event2 = Mockito.mock(TransactionEvent.class);
        when(event2.getTimestamp()).thenReturn(Instant.now().minusSeconds(120)); // 2 minutes ago
        when(event2.getAmount()).thenReturn(150.00);
        when(event2.getServiceID()).thenReturn("serviceB");
        when(event2.getUserID()).thenReturn("user2");

        TransactionEvent event3 = Mockito.mock(TransactionEvent.class);
        when(event3.getTimestamp()).thenReturn(Instant.now().minusSeconds(180)); // 3 minutes ago
        when(event3.getAmount()).thenReturn(200.00);
        when(event3.getServiceID()).thenReturn("serviceC");
        when(event3.getUserID()).thenReturn("user3");

        TransactionEvent event4 = Mockito.mock(TransactionEvent.class);
        when(event4.getTimestamp()).thenReturn(Instant.now().minusSeconds(240)); // 4 minutes ago
        when(event4.getAmount()).thenReturn(300.00);
        when(event4.getServiceID()).thenReturn("serviceD");
        when(event4.getUserID()).thenReturn("user1");

        // Create UserTransactions object
        UserTransactions userTransactions = new UserTransactions();

        // Test update method
        userTransactions.update(event1);
        userTransactions.update(event2);
        userTransactions.update(event3);
        userTransactions.update(event4);

        // Test hasMultipleServicesWithinWindow method
        assertTrue(userTransactions.hasMultipleServicesWithinWindow()); // Only one service in the last 5 minutes

    }

    @Test
    public void testHasTransactionAboveThreshold() {
        // Create mock TransactionEvent object
        TransactionEvent event1 = Mockito.mock(TransactionEvent.class);
        when(event1.getTimestamp()).thenReturn(Instant.now().minusSeconds(60)); // 1 minute ago
        when(event1.getAmount()).thenReturn(1000.00);
        when(event1.getServiceID()).thenReturn("serviceA");
        when(event1.getUserID()).thenReturn("user1");

        // Create UserTransactions object
        UserTransactions userTransactions = new UserTransactions();

        // Test update method
        userTransactions.update(event1);

        // Test hasTransactionAboveThreshold method
        assertTrue(userTransactions.hasTransactionAboveThreshold()); // Transaction amount exceeds threshold

    }
}