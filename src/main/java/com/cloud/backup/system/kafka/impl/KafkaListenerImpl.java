package com.cloud.backup.system.kafka.impl;

import com.cloud.backup.system.kafka.KafkaListener;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;


@ApplicationScoped
public class KafkaListenerImpl implements KafkaListener {

    @Incoming("your-kafka-topic")
    public void processKafkaMessage(String message) {
        // Kafka processing logic
    }
}
