package org.honeybee.base.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 自定义业务异常
 */
@Getter
public class BussinessException extends RuntimeException {

    private Integer status = HttpStatus.INTERNAL_SERVER_ERROR.value();

    public BussinessException(String message) {
        super(message);
    }

    public BussinessException(HttpStatus status, String message) {
        super(message);
        this.status = status.value();
    }

}
