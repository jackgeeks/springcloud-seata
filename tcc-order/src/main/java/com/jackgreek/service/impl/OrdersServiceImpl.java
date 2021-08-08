package com.jackgreek.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jackgreek.entity.Orders;
import com.jackgreek.feign.AccountApi;
import com.jackgreek.feign.StorageApi;
import com.jackgreek.mapper.OrdersMapper;
import com.jackgreek.service.IOrdersService;
import com.jackgreek.service.OrderApi;
import io.seata.core.context.RootContext;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

    private Map<String, Statement> statementMap = new ConcurrentHashMap<>(100);
    private Map<String, Connection> connectionMap = new ConcurrentHashMap<>(100);

      @Autowired
      private OrderApi orderApi;

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
    @GlobalTransactional()
    public boolean create(Orders order) {
        String xid = RootContext.getXID();
        log.info("------->交易开始");
        BusinessActionContext actionContext = new BusinessActionContext();
        actionContext.setXid(xid);
        //本地方法
        boolean result = orderApi.saveOrder(actionContext, order);
        if(!result){
            throw new RuntimeException("保存订单失败");
        }
        log.info("------->扣减库存开始storage中");
        //远程方法 扣减库存
//        result=storageApi.decrease(actionContext,order.getProductId(),order.getCount());
//        if(!result){
//            throw new RuntimeException("扣减库存失败");
//        }
//
//        //远程方法 扣减账户余额
//
        log.info("------->扣减账户开始account中");
      accountApi.decrease(actionContext,order.getUserId(),order.getPayAmount());
        log.info("------->扣减账户结束account中" + result);
        log.info("------->交易结束");
        //throw new RuntimeException("调用2阶段提交的rollback方法");
        return true;
    }

   @Override
    public void update(Long userId, BigDecimal money, Integer status) {
        log.info("修改订单状态，入参为：userId={},money={},status={}",userId,money,status);
        this.baseMapper.update(userId,money,status);

    }

}
