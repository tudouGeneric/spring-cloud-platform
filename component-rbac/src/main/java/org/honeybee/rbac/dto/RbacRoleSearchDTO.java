package org.honeybee.rbac.dto;

import cn.hutool.db.Page;
import lombok.Data;

@Data
public class RbacRoleSearchDTO extends Page {

    /**
     * 编号
     */
    private String code;

    /**
     * 名称
     */
    private String name;

}
