package org.honeybee.base.constant;

/**
 * 基本常量
 */
public class BaseConstant {

    public static final int BASE_EXCEPTION_HANDLER_ORDER = 10;

    public static final int RBAC_EXCEPTION_HANDLER_ORDER = 1;

    public static final String SUPER_ADMIN_ROLE_AUTHORITY = "hasRole('ROLE_SUPER_ADMIN') or ";

    //Redis中用户token的键key开头
    public static final String JWT_KEY_START = "TOKEN_";

}
