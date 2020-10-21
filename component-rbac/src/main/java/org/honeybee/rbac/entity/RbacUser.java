package org.honeybee.rbac.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.honeybee.base.entity.BaseEntity;
import org.honeybee.base.enums.DeleteStatusEnum;

import java.util.Date;

/**
 * 用户表
 */
@Data
@TableName(value = "rbac_user")
public class RbacUser extends BaseEntity {

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 姓名
     */
    private String name;

    /**
     * 生日
     */
    private Date birth;

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    @TableField(value = "nick_name")
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
    private String avatar;

    /**
     * 密码最后次更新时间
     */
    private Date lastPasswordResetDate;

    /**
     * 删除状态（0：未删除 [默认] ；1：已删除）
     */
    private DeleteStatusEnum deleteStatus = DeleteStatusEnum.EXISTED;

}
