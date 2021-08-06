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

    void decrease(Long userId, BigDecimal money);
}
