package org.honeybee.rbac.filter;

import cn.hutool.json.JSONUtil;
import org.honeybee.base.common.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 负责启动未经过身份验证的用户的身份验证过程(当他们试图访问受保护的资源)
 */
public class GoAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        httpServletResponse.setHeader("Content-type", "application/json;charset=utf-8");
        ResponseMessage responseMessage = ResponseMessage.error("身份认证失败,请重新登录", HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.getWriter().write(JSONUtil.toJsonStr(responseMessage));
        httpServletResponse.getWriter().flush();
    }

}
