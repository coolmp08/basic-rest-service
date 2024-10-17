package io.devsense.basic_rest_service.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

public class Receiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);
    private AtomicInteger messageCounter = new AtomicInteger();

    public void receiveMessage(String message){
        LOGGER.info("Received message <" + message + ">");
        messageCounter.incrementAndGet();
    }

    public int getCount(){
        return messageCounter.get();
    }
}
