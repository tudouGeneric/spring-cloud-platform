package org.honeybee.rbac.handler;

import cn.hutool.json.JSONUtil;
import org.honeybee.base.common.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 解决认证过的用户访问无权限资源时的异常
 */
public class CustomAccessDeineHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                       AccessDeniedException e) throws IOException {
        httpServletResponse.setHeader("Content-type", "application/json;charset=utf-8");
        ResponseMessage responseMessage = ResponseMessage.error("无访问权限", HttpStatus.FORBIDDEN.value());
        httpServletResponse.getWriter().write(JSONUtil.toJsonStr(responseMessage));
        httpServletResponse.getWriter().flush();
    }

}
