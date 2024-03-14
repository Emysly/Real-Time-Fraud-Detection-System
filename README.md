# Real-Time-Fraud-Detection-System
A real-time fraud detection system that can analyze transactions across services to identify potential fraud without relying on external libraries designed specifically for anomaly detection.

## Running the Prototype:

### To run the prototype:

1. Ensure you have Java 17 installed on your system.
2. Clone the repository containing the source code.
3. Run the FraudDetectionSystemApp class to execute the fraud detection system.

**Description of the Algorithm:**

The fraud detection system analyzes a stream of transaction events in real-time to identify suspicious patterns. 
Each transaction event includes a timestamp, transaction amount, user ID, and service ID. 
The system tracks user transactions, updating service counts, total amounts, and recent transactions. 
It then applies predefined rules to detect fraudulent patterns, such as multiple services within a time window, 
transactions significantly above the user's average, and ping-pong activity between services.

**Addressing Constraints:**

- Efficiency without External Libraries: The system focuses on algorithm design rather than relying on external anomaly detection libraries, ensuring efficiency in processing large volumes of transactions.

- Handling Large Volumes of Data: Utilizing efficient data structures and processing techniques, the system scales to handle a high volume of transactions without performance degradation.

- Simulating Real-Time Processing: While not using real-time frameworks, the system can simulate real-time processing by processing events as they arrive, either from input files or in-memory queues.

This approach ensures that the fraud detection system can efficiently process transactions in real-time, identify fraudulent patterns, and generate alerts, meeting the requirements of the task.