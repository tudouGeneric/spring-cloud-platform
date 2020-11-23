package org.honeybee.base.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.http.HttpStatus;

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

    //执行成功/失败的布尔值
    @ApiModelProperty(required = true, notes = "执行成功/失败", example = "true")
    private Boolean flag;

    private ResponseMessage() {

    }

    private ResponseMessage(String message, int status, Boolean flag) {
        this.message = message;
        this.status = status;
        this.flag = flag;
    }

    private ResponseMessage(String message, T result, int status, Boolean flag) {
        this.message = message;
        this.result = result;
        this.status = status;
        this.flag = flag;
    }

    /**
     * 执行成功时返回
     * @param result
     * @param <T>
     * @return
     */
    public static <T> ResponseMessage<T> success(String message) {
        return new ResponseMessage(message, HttpStatus.OK.value(), true);
    }

    /**
     * 执行成功时返回
     * @param message
     * @param result
     * @param <T>
     */
    public static <T> ResponseMessage<T> success(String message, T result) {
        return new ResponseMessage(message, result, HttpStatus.OK.value(), true);
    }

    /**
     * 执行失败时返回
     * @param message
     * @return
     */
    public static <T> ResponseMessage<T> fail(String message) {
        return new ResponseMessage(message, HttpStatus.OK.value(), false);
    }

    /**
     * 发生异常时返回
     * @param message
     * @return
     */
    public static <T> ResponseMessage<T> error(String message, int status) {
        return new ResponseMessage(message, status, false);
    }

    /**
     * 执行失败时返回
     * @param message
     * @param result
     * @param <T>
     * @return
     */
    public static <T> ResponseMessage<T> fail(String message, T result) {
        return new ResponseMessage(message, result, HttpStatus.OK.value(), false);
    }

    /**
     * 发生异常时返回
     * @param message
     * @param result
     * @param <T>
     * @return
     */
    public static <T> ResponseMessage<T> error(String message, T result, int status) {
        return new ResponseMessage(message, result, status, false);
    }

}
