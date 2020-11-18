package org.honeybee.rbac.controller;

import lombok.extern.slf4j.Slf4j;
import org.honeybee.base.common.ResponseMessage;
import org.honeybee.base.constant.BaseConstant;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * rbac模块异常处理器
 */
@RestControllerAdvice
@Order(BaseConstant.RBAC_EXCEPTION_HANDLER_ORDER)
@Slf4j
public class RbacExceptionController {

    /**
     * 捕捉LockedException 账户锁定异常
     * @param e
     * @return
     */
    @ExceptionHandler(LockedException.class)
    public ResponseMessage LockedException(LockedException e) {
        e.printStackTrace();
        return ResponseMessage.error("账号被锁定", HttpStatus.UNAUTHORIZED.value());
    }

    /**
     * 捕捉DisabledException 账户禁用异常
     * @param e
     * @return
     */
    @ExceptionHandler(DisabledException.class)
    public ResponseMessage disabledException(DisabledException e) {
        e.printStackTrace();
        return ResponseMessage.error("账号被禁用", HttpStatus.UNAUTHORIZED.value());
    }

    /**
     * 捕捉BadCredentialsException 账号密码错误异常
     * @param e
     * @return
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseMessage badCredentialsException(BadCredentialsException e) {
        e.printStackTrace();
        return ResponseMessage.error("账号或密码错误", HttpStatus.UNAUTHORIZED.value());
    }

    /**
     * 捕捉AccessDeniedException 无访问权限异常
     * @param e
     * @return
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseMessage accessDeniedException(AccessDeniedException e) {
        e.printStackTrace();
        return ResponseMessage.error("无访问权限:" + e.getMessage(), HttpStatus.FORBIDDEN.value());
    }

}
