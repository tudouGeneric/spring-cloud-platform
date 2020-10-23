package org.honeybee.rbac.dto;

import lombok.Data;
import org.honeybee.base.common.Page;

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
