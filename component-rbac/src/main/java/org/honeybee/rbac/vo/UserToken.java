package org.honeybee.rbac.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserToken {

    private String token;

    private Long userId;

    private String name;

}
