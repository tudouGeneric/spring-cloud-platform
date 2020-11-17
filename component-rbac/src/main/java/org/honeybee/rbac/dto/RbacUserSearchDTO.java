package org.honeybee.rbac.dto;

import cn.hutool.db.Page;
import lombok.Data;

@Data
public class RbacUserSearchDTO extends Page {

    /**
     * 账号
     */
    private String account;

    /**
     * 姓名
     */
    private String name;

}
