package org.honeybee.kafka.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SendMessageDTO {

    //指定发送的topic名称
    @NotBlank(message = "topic不能为空")
    private String topic;

    //消息
    @NotBlank(message = "message不能为空")
    private String message;

}
