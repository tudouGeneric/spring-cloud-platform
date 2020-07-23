package org.honeybee.base.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 返回数据
 */
@Data
@ApiModel("通用接口返回对象")
public class ResponseMessage<T> {

    //消息
    @ApiModelProperty(required = true, notes = "返回提示信息")
    private String message;

    //内容
    @ApiModelProperty(notes = "返回结果")
    private T result;

    //状态码
    @ApiModelProperty(required = true, notes = "http状态码", example = "200")
    private int status;

    private ResponseMessage() {

    }

    private ResponseMessage(String message, int status) {
        this.message = message;
        this.status = status;
    }

    private ResponseMessage(String message, T result, int status) {
        this.message = message;
        this.result = result;
        this.status = status;
    }

    /**
     * 成功时返回
     * @param result
     * @param <T>
     * @return
     */
    public static <T> ResponseMessage<T> success(T result) {
        return new ResponseMessage("success", result, 200);
    }

    /**
     * 出现异常时返回
     * @param message
     * @param status
     * @param <T>
     * @return
     */
    public static <T> ResponseMessage<T> error(String message, int status) {
        return new ResponseMessage(message, status);
    }

    public static <T> ResponseMessage<T> error(String message, T result, int status) {
        return new ResponseMessage(message, result, status);
    }

}
