package org.honeybee.rbac.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 登录成功返回对象
 */
@Data
@AllArgsConstructor
public class ResponseUserToken {

    private String token;

    private JwtUser userDetail;

}
