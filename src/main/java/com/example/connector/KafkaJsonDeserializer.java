package com.example.connector;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.kafka.support.serializer.DeserializationException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KafkaJsonDeserializer<T> implements Deserializer {

    private Class <T> type;

    public KafkaJsonDeserializer(Class type) {
        this.type = type;
    }

    @Override
    public void configure(Map map, boolean b) {
        // N/A
    }

    @Override
    public Object deserialize(String s, byte[] bytes) {
        ObjectMapper mapper = new ObjectMapper();
        T obj = null;
        try {
            obj = mapper.readValue(bytes, type);
        } catch (Exception e) {

            log.error(e.getMessage());
        }
        return obj;
    }

    @Override
    public void close() {
        // N/A
    }
}

/*
public class KafkaJsonDeserializer<T> implements Deserializer<T> {
    private Class<T> type;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public KafkaJsonDeserializer(Class<T> type) {
        this.type = type;
    }

    public KafkaJsonDeserializer() {
        
    }

    @Override
    public T deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data, type);
        } catch (Exception e) {
            throw new DeserializationException("Error deserializing JSON message", data, false, e);
        }
    }
    
}
*/
