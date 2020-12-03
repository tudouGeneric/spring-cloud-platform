package org.honeybee.kafka.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.honeybee.base.common.ResponseMessage;
import org.honeybee.kafka.dto.SendMessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/kafka")
@Api(tags = "分布式消息队列kafka相关接口")
@Slf4j
public class MessageController {

    @Resource
    private KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * 测试发送消息
     * @param sendMessageDTO
     * @return
     */
    @PostMapping("/sendMessage")
    @ApiOperation(value = "测试发送消息到topic")
    public ResponseMessage sendMessageTes(@RequestBody @Validated SendMessageDTO sendMessageDTO) {
        ListenableFuture<SendResult<String, Object>> future =
                kafkaTemplate.send(sendMessageDTO.getTopic(), sendMessageDTO.getMessage());
        //阻塞等待broker的响应,直到消息发送成功
        try {
            future.get(3, TimeUnit.SECONDS);    //设置超时时间
        } catch (InterruptedException e) {
            e.printStackTrace();
            log.error("======发送至topic:{}失败======", sendMessageDTO.getTopic());
            return ResponseMessage.fail("发送失败");
        } catch (ExecutionException e) {
            e.printStackTrace();
            log.error("======发送至topic:{}, 执行失败======", sendMessageDTO.getTopic());
            return ResponseMessage.fail("执行失败");
        } catch (TimeoutException e) {
            e.printStackTrace();
            log.error("======发送至topic:{}超时======", sendMessageDTO.getTopic());
            return ResponseMessage.fail("发送超时");
        }

        return ResponseMessage.success("发送成功");
    }

}
