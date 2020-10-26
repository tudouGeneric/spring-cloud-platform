package org.honeybee.rbac.filter;

import lombok.extern.slf4j.Slf4j;
import org.honeybee.base.exception.ServiceException;
import org.honeybee.rbac.pojo.JwtUser;
import org.honeybee.rbac.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

/**
 * 自定义jwttoken过滤器
 */
@Component
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Value("${auth.jwt.header}")
    private String tokenHeader;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String authToken = request.getHeader(this.tokenHeader);
        //获取用户信息
        UserDetails userDetails = jwtTokenUtil.getUserFromToken(authToken);
        if(userDetails != null) {
            String username = ((JwtUser) userDetails).getAccount();
            log.info("check authentication " + username);
            if(username != null && jwtTokenUtil.containToken(username, authToken) && SecurityContextHolder.getContext().getAuthentication() == null) {
                if(jwtTokenUtil.validateToken(authToken)) {     //如果token没有失效
                    Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    log.info("authentication user " + username + ", setting security context");
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    throw new ServiceException(HttpStatus.UNAUTHORIZED, "token失效,请重新登录");
                }
            }
        }
        chain.doFilter(request, response);
    }

}
