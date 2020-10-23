package org.honeybee.rbac.config;

import org.honeybee.rbac.filter.GoAuthenticationEntryPoint;
import org.honeybee.rbac.filter.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * spring security核心配置类
 */
@Configuration
//作用1:加载了WebSecurityConfiguration配置类, 配置安全认证策略。2:加载了AuthenticationConfiguration, 配置了认证信息。
@EnableWebSecurity
//开启基于方法的安全认证机制,也就是说在web层的controller启用注解机制的安全确认.
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Autowired
    private UserDetailsService userDetailsService;

    @Value("${jwt.expiration}")
    private int validate;

    /**
     * 装载BCrypt密码编码器
     * @return
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //添加自定义的userDetailService认证
        auth.userDetailsService(this.userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //过滤静态资源
        web.ignoring().antMatchers(
                "/js/**",
                "/css/**",
                "/images/**",
                "/swagger-ui.html",
                "/swagger-resources/**");  //过滤不拦截的
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .cors().and()
                //使用jwt,关闭session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .httpBasic()
                //未经过认证的用户访问受保护的资源返回配置
                .authenticationEntryPoint(new GoAuthenticationEntryPoint())
                .and()
                .authorizeRequests()
                .anyRequest().authenticated();

        http.exceptionHandling()
                .and().addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);



        /*http.authorizeRequests()
                .antMatchers("/admin/**").hasRole("admin")
                .antMatchers("/user/**").hasRole("user")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginProcessingUrl("/doLogin")     //登录接口
                .usernameParameter("username")  //登录用户参数名
                .passwordParameter("password")  //登录密码参数名
                .successHandler(((httpServletRequest, httpServletResponse, authentication) -> { //登录成功处理
                    httpServletResponse.setContentType("application/json;charset=utf-8");
                    PrintWriter out = httpServletResponse.getWriter();
                    out.write(new ObjectMapper().writeValueAsString(authentication.getPrincipal()));
                    out.flush();
                    out.close();
                }))
                .failureHandler(((httpServletRequest, httpServletResponse, e) -> {  //登录失败处理
                    httpServletResponse.setContentType("application/json;charset=utf-8");
                    PrintWriter out = httpServletResponse.getWriter();
                    out.write(new ObjectMapper().writeValueAsString(e.getMessage()));
                    out.flush();
                    out.close();
                }))
                .permitAll()        //放行相关url
                .and()
                .logout()
//                .logoutUrl("/logout")     //登出接口
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST"))     //自定义登出接口
                .logoutSuccessHandler(((httpServletRequest, httpServletResponse, authentication) -> {
                    httpServletResponse.setContentType("application/json;charset=utf-8");
                    PrintWriter out = httpServletResponse.getWriter();
                    out.write(new ObjectMapper().writeValueAsString("登出成功"));
                    out.flush();
                    out.close();
                }))
                .permitAll()
                .and()
                .csrf().disable()
                .exceptionHandling()    //未认证处理
                .authenticationEntryPoint(((httpServletRequest, httpServletResponse, e) -> {
                    httpServletResponse.setContentType("application/json;charset=utf-8");
                    PrintWriter out = httpServletResponse.getWriter();
                    out.write(new ObjectMapper().writeValueAsString("尚未登录,请先登录"));
                    out.flush();
                    out.close();
                }));*/

    }

}
