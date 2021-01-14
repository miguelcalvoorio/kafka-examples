package com.example.connector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import lombok.extern.slf4j.Slf4j;

@Component("kakfa-spring-connector")
@Slf4j
public class KafkaSpringConnector implements QueueConnector {

    @Autowired
    private KafkaTemplate<Object, Object> template;

    public void sendMessage(String inputTopic, String key, Object message) {

        ListenableFuture<SendResult<Object, Object>> future = this.template.send(inputTopic, key, message);

        future.addCallback(new ListenableFutureCallback<SendResult<Object, Object>>() {
            @Override
            public void onSuccess(SendResult<Object, Object> result) {
                log.info("Sent message=[" + message + 
                  "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }
            @Override
            public void onFailure(Throwable ex) {
                log.info("Unable to send message=[" 
                  + message + "] due to : " + ex.getMessage());
            }
        });
    }

    @Override    
    public void receiveMessages(String outputTopic) {
        log.info("Calling receiveMessages");
    }
}
