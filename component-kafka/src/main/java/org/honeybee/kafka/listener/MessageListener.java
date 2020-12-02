package org.honeybee.kafka.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * 监听kafka消息
 */
@Component
@Slf4j
public class MessageListener {

    /**
     * 监听固定topic的消息
     */
    //@KafkaListener id/groupId默认是同一个值,指定消费者组; topics指定topic,可多个;
    @KafkaListener(id="consumer1", topics="student-info")
    public void listen(String message) {
        log.info("接收到topic:{}的消息:{}", "stuent-info", message);
    }

}
