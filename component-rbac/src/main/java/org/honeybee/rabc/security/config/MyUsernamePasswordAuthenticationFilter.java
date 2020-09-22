package org.honeybee.rabc.security.config;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * 登录认证Filter
 * 对于用户登录行为，security通过定义一个Filter来拦截/login来实现的
 * spring security默认支持form方式登录，所以对于使用json发送登录信息的情况，我们自己定义一个Filter，这个Filter直接从AbstractAuthenticationProcessingFilter继承
 * 只需要实现两部分，一个是RequestMatcher，指名拦截的Request类型；另外就是从json body中提取出username和password提交给AuthenticationManager。
 *
 */
public class MyUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public MyUsernamePasswordAuthenticationFilter() {
        //拦截url为 "/login" 的POST请求
        super(new AntPathRequestMatcher("/login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        //从json中获取username和password
        String body = StreamUtils.copyToString(request.getInputStream(), Charset.forName("UTF-8"));
        String username = "", password = "";
        if(StringUtils.hasText(body)) {
            JSONObject jsonObj = JSONUtil.parseObj(body);
            username = jsonObj.getStr("username");
            password = jsonObj.getStr("password");
        }
        username = username.trim();

        //封装到token中提交
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

}
