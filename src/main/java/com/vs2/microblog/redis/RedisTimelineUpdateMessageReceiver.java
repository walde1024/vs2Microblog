package com.vs2.microblog.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.concurrent.CountDownLatch;


/**
 * Created by Walde on 10.06.16.
 */
public class RedisTimelineUpdateMessageReceiver {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void receiveUpdateMessage(String message) {
        messagingTemplate.convertAndSend("/message/update", "{'update':'New Messages available'}");
    }
}
