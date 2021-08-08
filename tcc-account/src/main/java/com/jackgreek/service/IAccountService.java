package com.jackgreek.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jackgreek.entity.Account;

import java.math.BigDecimal;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Mht
 * @since 2021-07-21
 */
public interface IAccountService extends IService<Account> {

    boolean decrease(String xid,Long userId, BigDecimal money);

    boolean commit(String xid);

    boolean rollback(String xid);
}
