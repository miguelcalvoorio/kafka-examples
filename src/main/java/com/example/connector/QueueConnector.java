package com.example.connector;

public interface QueueConnector {
    public void sendMessage(String inputTopic, String key, Object message);

    public void receiveMessages(String outputTopic);
}
