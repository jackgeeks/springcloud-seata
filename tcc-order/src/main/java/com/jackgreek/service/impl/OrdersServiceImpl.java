package com.jackgreek.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jackgreek.entity.Orders;
import com.jackgreek.feign.AccountApi;
import com.jackgreek.feign.StorageApi;
import com.jackgreek.mapper.OrdersMapper;
import com.jackgreek.service.IOrdersService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Mht
 * @since 2021-07-20
 */
@Service
@Slf4j
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements IOrdersService {




    @Autowired
    private StorageApi storageApi;
    @Autowired
    private AccountApi accountApi;
    /**
     * 创建订单
     * @param order
     * @return
     * 测试结果：
     * 1.添加本地事务：仅仅扣减库存
     * 2.不添加本地事务：创建订单，扣减库存
     */
    @Override
    @GlobalTransactional(name = "create-order",rollbackFor = Exception.class)
    public void create(Orders order) {
        log.info("------->交易开始");
        //本地方法
        this.baseMapper.create(order);

//        //远程方法 扣减库存
        storageApi.decrease(order.getProductId(),order.getCount());
//
//        //远程方法 扣减账户余额
//
        log.info("------->扣减账户开始order中");
       accountApi.decrease(order.getUserId(),order.getPayAmount());

        log.info("------->扣减账户结束order中");
        int a=10/0;

        log.info("------->交易结束");
    }

    @Override
    public void update(Long userId, BigDecimal money, Integer status) {
        log.info("修改订单状态，入参为：userId={},money={},status={}",userId,money,status);
        this.baseMapper.update(userId,money,status);

    }
}
