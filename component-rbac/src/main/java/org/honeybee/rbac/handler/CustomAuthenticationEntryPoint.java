package org.honeybee.rbac.handler;

import cn.hutool.json.JSONUtil;
import org.honeybee.base.common.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 解决匿名用户或token校验失败访问无权限资源时的异常
 */
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        httpServletResponse.setHeader("Content-type", "application/json;charset=utf-8");
        ResponseMessage responseMessage = ResponseMessage.error("身份验证失败", HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.getWriter().write(JSONUtil.toJsonStr(responseMessage));
        httpServletResponse.getWriter().flush();
    }

}
