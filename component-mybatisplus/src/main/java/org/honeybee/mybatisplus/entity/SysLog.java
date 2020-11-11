package org.honeybee.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统aop日志表
 */
@Data
@TableName(value = "t_sys_log")
public class SysLog implements Serializable {

    private static final long serialVersionUID = -3912053388457210598L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    @TableField(value = "username")
    private String username;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private String userId;

    /**
     * 用户操作
     */
    @TableField(value = "operation")
    private String operation;

    /**
     * 响应时间
     */
    @TableField(value = "time")
    private Long time;

    /**
     * 请求方法
     */
    @TableField(value = "method")
    private String method;

    /**
     * 请求参数
     */
    @TableField(value = "params")
    private String params;

    /**
     * ip地址
     */
    @TableField(value = "ip")
    private String ip;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

}
