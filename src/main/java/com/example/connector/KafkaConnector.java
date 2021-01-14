package com.example.connector;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.Properties;

import com.example.entity.OperationEntity;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import lombok.extern.slf4j.Slf4j;

@Component("kakfa-connector")
@Slf4j
public class KafkaConnector implements QueueConnector {

    private final KafkaProperties kafkaProperties;

    private KafkaProducer<String, Object> producer;
    //private KafkaConsumer<String, Object> consumer;
    private Consumer consumer;

    @Autowired
    public KafkaConnector(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    private KafkaProducer<String, Object> initProducer() {
        final Properties producerConfig = new Properties();
        producerConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, this.kafkaProperties.getProducerBootstrapServers());
        producerConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, this.kafkaProperties.getProducerKeySerializer());
        producerConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
        this.kafkaProperties.getProducerValueSerializer());
        
        return new KafkaProducer<>(producerConfig);
    }

    private void initConsumer() {
        final Properties consumerConfig = new Properties();
        consumerConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, this.kafkaProperties.getConsumerBootstrapServers());
        this.consumer = new KafkaConsumer<>(consumerConfig, keyDeserializer(), valueDeserializer());
    }

    @Override
    public void sendMessage(String inputTopic, String key, Object message) {
        if (this.producer == null) this.producer = initProducer();

        this.producer.send(new ProducerRecord<>(inputTopic, key, message), (metadata, exception) -> {
            if (exception != null) {
                log.error("Sent message=[" + message + "] --> " + exception.getLocalizedMessage());
            } else {
                log.info("Sent message=[" + message + "] with offset=[" + metadata.offset() + "]");
            }
        });
    }

    @Override    
    public void receiveMessages(String outputTopic) {
        log.info("Calling receiveMessages");

        if (this.consumer == null) initConsumer();

        try {
            this.consumer.subscribe(Collections.singletonList(outputTopic));
        } finally {
            this.consumer.close();
        }

    }

    private Deserializer keyDeserializer() {
        return new StringDeserializer();
    }

    private Deserializer valueDeserializer() {
        return new KafkaJsonDeserializer<>(OperationEntity.class);
    }
}
