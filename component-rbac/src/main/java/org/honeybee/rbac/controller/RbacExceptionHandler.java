package org.honeybee.rbac.controller;

import lombok.extern.slf4j.Slf4j;
import org.honeybee.base.common.ResponseMessage;
import org.honeybee.base.constant.BaseConstant;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * rbac模块异常处理器
 */
@RestControllerAdvice
@Order(BaseConstant.RBAC_EXCEPTION_HANDLER_ORDER)
@Slf4j
public class RbacExceptionHandler {

    /**
     * 捕捉AccessDeniedException 无访问权限异常
     * @param e
     * @return
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseMessage accessDeniedException(AccessDeniedException e) {
        e.printStackTrace();
        log.error("===============" + e.getMessage() + "===============");
        log.error("异常出现:", e);
        return ResponseMessage.error("无访问权限:" +e.getMessage(), HttpStatus.FORBIDDEN.value());
    }

}
