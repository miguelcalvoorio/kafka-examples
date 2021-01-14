package com.example.connector;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import lombok.Data;

@Component
@Data
public final class KafkaProperties {
    private String producerBootstrapServers;
    private String producerKeySerializer;
    private String producerValueSerializer;

    private String consumerBootstrapServers;
    private String consumerKeyDeserializer;
    private String consumerValueDeserializer;

    @Autowired
    public void setKafkaProducerProperties(@Value("${app.kafka.producer.bootstrap-servers}") String producerBootstrapServers,
                                           @Value("${app.kafka.producer.key-serializer}") String producerKeySerializer,
                                           @Value("${app.kafka.producer.value-serializer}") String producerValueSerializer) {

        this.producerBootstrapServers = producerBootstrapServers;
        this.producerKeySerializer = producerKeySerializer;
        this.producerValueSerializer = producerValueSerializer;
    }

    @Autowired
    public void setKafkaConsumerProperties(@Value("${app.kafka.consumer.bootstrap-servers}") String consumerBootstrapServers,
                                           @Value("${app.kafka.consumer.key-deserializer}") String consumerKeyDeserializer,
                                           @Value("${app.kafka.consumer.value-deserializer}") String consumerValueDeserializer) {
        
        this.consumerBootstrapServers = consumerBootstrapServers;
        this.consumerKeyDeserializer = consumerKeyDeserializer;
        this.consumerValueDeserializer = consumerValueDeserializer;
    }
}
