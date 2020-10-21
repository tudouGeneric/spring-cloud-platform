package org.honeybee.rbac.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.honeybee.rbac.entity.RbacUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

/**
 * security核心用户类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtUser implements UserDetails {

    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 密码更新时间
     */
    private Date lastPasswordResetDate;

    /**
     * 权限
     */
    private Collection<? extends  GrantedAuthority> authorities;

    /**
     * 写一个直接能用rbacUser构造jwtUser的构造器
     * @param user
     */
    public JwtUser(RbacUser user) {
        this.id = user.getId();
        this.name = user.getName();
        this.account = user.getAccount();
        this.password = user.getPassword();
        this.lastPasswordResetDate = user.getLastPasswordResetDate();
        Set<SimpleGrantedAuthority> simpleGrantedAuthorities = new HashSet<>();
        //FIXME 获取用户权限信息
//        if(CollectionUtils.isNotEmpty()) {
//
//        }
        this.authorities = simpleGrantedAuthorities;
    }

    public JwtUser(Long id, String name, String account, List<String> authorities) {
        this.id =id;
        this.name = name;
        this.account = account;
        Set<SimpleGrantedAuthority> simpleGrantedAuthorities = new HashSet<>();
        if(CollectionUtils.isNotEmpty(authorities)) {
            for(String code : authorities) {
                SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(code);
                simpleGrantedAuthorities.add(simpleGrantedAuthority);
            }
        }
        this.authorities = simpleGrantedAuthorities;
    }


    /**
     * 获取权限信息
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return account;
    }

    //账号是否未过期
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //账号是否未锁定
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //账号凭证是否未过期
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //账号是否可用
    @Override
    public boolean isEnabled() {
        return true;
    }

}
