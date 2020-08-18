package org.honeybee.rabc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity  //开启Security
@EnableGlobalMethodSecurity(prePostEnabled = true)  //使用表达式时间方法级别的安全性 4个注解可用
/**
 * @PreAuthorize 在方法调用之前,基于表达式的计算结果来限制对方法的访问
 * @PostAuthorize 允许方法调用,但是如果表达式计算结果为false,将抛出一个安全性异常
 * @PostFilter 允许方法调用,但必须按照表达式来过滤方法的结果
 * @PreFilter 允许方法调用,但必须在进入方法之前过滤输入值
 */
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    @Autowired
//    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
//
//    @Autowired
//    JwtUserDetailsService jwtUserDetailsService;
//
//    @Autowired
//    JwtAuthorizationTokenFilter authenticationTokenFilter;

    //先来这里认证一下
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoderBean());
    }

    //拦截在这配
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
//                .and()
//                .authorizeRequests()
//                .antMatchers("/login").permitAll()
//                .antMatchers("/sysUser/test").permitAll()
//                .antMatchers(HttpMethod.OPTIONS, "/**").anonymous()
//                .anyRequest().authenticated()       // 剩下所有的验证都需要验证
//                .and()
//                .csrf().disable()                      // 禁用 Spring Security 自带的跨域处理
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // 定制我们自己的 session 策略：调整为让 Spring Security 不创建和使用 session

//        http.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }


    @Bean
    public PasswordEncoder passwordEncoderBean() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
