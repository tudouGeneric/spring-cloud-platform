package org.honeybee.base.controller;

import lombok.extern.slf4j.Slf4j;
import org.honeybee.base.common.ResponseMessage;
import org.honeybee.base.constant.BaseConstant;
import org.honeybee.base.exception.BussinessException;
import org.honeybee.base.exception.ServiceException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 全局异常处理器
 *
 */
@RestControllerAdvice
@Order(BaseConstant.BASE_EXCEPTION_HANDLER_ORDER)
@Slf4j
public class ExceptionController {

    /**
     * 统一处理请求参数校验(实体对象传参)
     *
     * @param e BindException
     * @return FebsResponse
     */
    @ExceptionHandler(BindException.class)
    public ResponseMessage validException(BindException e) {
        e.printStackTrace();
        log.error("===============" + e.getMessage() + "===============");
        log.error("异常出现:", e);
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        Map<String, Object> result = this.getValidError(fieldErrors);
        return ResponseMessage.error(result.get("errorMsg").toString(), result.get("errorList"), HttpStatus.BAD_REQUEST.value());
    }

    /**
     * 捕捉校验异常(MethodArgumentNotValidException)
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseMessage validException(MethodArgumentNotValidException e) {
        e.printStackTrace();
        log.error("===============" + e.getMessage() + "===============");
        log.error("异常出现:", e);
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        Map<String, Object> result = this.getValidError(fieldErrors);
        return ResponseMessage.error(result.get("errorMsg").toString(), result.get("errorList"), HttpStatus.BAD_REQUEST.value());
    }

    /**
     * 捕捉自定义Bussiness异常
     * @param e
     * @return
     */
    @ExceptionHandler(BussinessException.class)
    public ResponseMessage customBussinessException(BussinessException e) {
        e.printStackTrace();
        log.error("===============" + e.getMessage() + "===============");
        log.error("异常出现:", e);
        return ResponseMessage.error(e.getMessage(), e.getStatus());
    }

    /**
     * 捕捉自定义Service异常
     * @param e
     * @return
     */
    @ExceptionHandler(ServiceException.class)
    public ResponseMessage customServiceException(ServiceException e) {
        e.printStackTrace();
        log.error("===============" + e.getMessage() + "===============");
        log.error("异常出现:", e);
        return ResponseMessage.error(e.getMessage(), e.getStatus());
    }

    /**
     * 捕捉其他所有异常
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseMessage globalException(HttpServletRequest request, Throwable ex) {
        ex.printStackTrace();
        log.error("===============" + ex.getMessage() + "===============");
        log.error("异常出现:", ex);
        return ResponseMessage.error(ex.toString() + ": " + ex.getMessage(), this.getStatus(request).value());
    }

    /**
     * 获取状态码
     * @param request
     * @return
     */
    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }

    /**
     * 获取校验错误信息
     * @param fieldErrors
     * @return
     */
    private Map<String, Object> getValidError(List<FieldError> fieldErrors) {
        Map<String, Object> result = new HashMap<>();
        List<String> errorList = new ArrayList<>();
        StringBuffer errorMsg = new StringBuffer("校验异常(ValidException):");
        for(FieldError error : fieldErrors) {
            errorList.add(error.getField() + "-" + error.getDefaultMessage());
            errorMsg.append(error.getField() + "-" + error.getDefaultMessage() + ".");
        }
        result.put("errorList", errorList);
        result.put("errorMsg", errorMsg);
        return result;
    }

}
