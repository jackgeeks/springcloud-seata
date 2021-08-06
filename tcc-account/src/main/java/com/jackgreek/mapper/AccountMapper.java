package com.jackgreek.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jackgreek.entity.Account;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Mht
 * @since 2021-07-21
 */
public interface AccountMapper extends BaseMapper<Account> {
    /**
     * 扣减账户余额
     * @param userId 用户id
     * @param payAmount 金额
     */
    void decrease(@Param("userId") Long userId, @Param("payAmount") BigDecimal payAmount);
}
