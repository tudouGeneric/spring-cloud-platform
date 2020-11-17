package org.honeybee.rbac.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.honeybee.base.enums.DeleteStatusEnum;
import org.honeybee.base.enums.SexEnum;

import java.util.Date;

@Data
public class UserVO {

    /**
     * 姓名
     */
    private String name;

    /**
     * 姓名拼音
     */
    private String pinYin;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 性别
     */
    private SexEnum sex;

    /**
     * 生日
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
    private Date birth;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机
     */
    private String mobilePhone;

    /**
     * 头像地址
     */
    private String photoUrl;

    /**
     * 删除状态
     */
    private DeleteStatusEnum deleteStatus;

}
