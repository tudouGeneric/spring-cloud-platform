package org.honeybee.mybatisplus.util;


import cn.hutool.db.sql.Direction;
import cn.hutool.db.sql.Order;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页工具类
 */
public class PageUtil {

    /**
     * 构建分页page
     * @param dto
     * @param <T>
     * @return
     */
    public static <T extends cn.hutool.db.Page> Page buildPage(T dto) {
        Page page = new Page(dto.getPageNumber(), dto.getPageSize());
        if(dto.getOrders() != null) {
            List<OrderItem> orderItems = new ArrayList<>();
            for(Order order : dto.getOrders()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setColumn(order.getField());
                if(order.getDirection() != null && order.getDirection().equals(Direction.DESC)) {
                    orderItem.setAsc(false);
                }
                orderItems.add(orderItem);
            }
            page.setOrders(orderItems);
        }
        return page;
    }

}
