package com.jackgreek.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jackgreek.entity.Orders;

import java.math.BigDecimal;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Mht
 * @since 2021-07-20
 */
public interface IOrdersService extends IService<Orders> {
    /**
     * 创建订单
     * @param order
     * @return
     */
    boolean create(Orders order);

    /**
     * 修改订单状态
     * @param userId
     * @param money
     * @param status
     */
    void update(Long userId, BigDecimal money, Integer status);
}


