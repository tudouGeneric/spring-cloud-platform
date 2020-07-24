package org.honeybee.base.common;

import cn.hutool.db.sql.Order;
import lombok.Data;

import java.io.Serializable;

/**
 * 分页查询辅助对象
 */
@Data
public class Page implements Serializable {

    private static final long serialVersionUID = -4360760761827410629L;

    //每页数量
    private Integer pageSize;

    //页码(从1开始)
    private Integer pageNumber;

    //排序对象
    private Order[] orders;

    public Page() {
        this(10, 1);
    }

    public Page(Integer pageSize, Integer pageNumber) {
        this.pageNumber = Math.max(pageNumber, 1);
        this.pageSize = pageSize <= 0 ? 10 : pageSize;
    }

    public Page(Integer pageSize, Integer pageNumber, Order[] orders) {
        this(pageSize, pageNumber);
        this.orders = orders;
    }

}
