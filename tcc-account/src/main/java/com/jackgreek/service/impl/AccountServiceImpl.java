package com.jackgreek.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jackgreek.entity.Account;
import com.jackgreek.mapper.AccountMapper;
import com.jackgreek.service.IAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Mht
 * @since 2021-07-21
 */
@Service
@Slf4j
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements IAccountService {

    @Override
    public void decrease(Long userId, BigDecimal payAmount) {
        log.info("------->扣减账户开始account中");
        //模拟超时异常，全局事务回滚
        try {
            Thread.sleep(30*10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.baseMapper.decrease(userId,payAmount);
        log.info("------->扣减账户结束account中");

    }
}
