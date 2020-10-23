package org.honeybee.base.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 自定义服务异常
 */
@Getter
public class ServiceException extends RuntimeException {

    private Integer status = HttpStatus.INTERNAL_SERVER_ERROR.value();

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(HttpStatus status, String message) {
        super(message);
        this.status = status.value();
    }

}
