package com.jackgreek.controller;


import com.jackgreek.service.IAccountService;
import com.jackgreek.utils.R;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.math.BigDecimal;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Mht
 * @since 2021-07-21
 */
@RestController
@RequestMapping("/account")
@Slf4j
public class AccountController {

    @Autowired
    private IAccountService accountService;
    /**
     * 扣减账户余额
     * @param userId id
     * @param money 金额
     * @return
     */
    @PostMapping("decrease")
    public boolean   decrease(@RequestBody BusinessActionContext actionContext,@RequestParam("userId") Long userId, @RequestParam("money") BigDecimal money){
        return accountService.decrease(actionContext.getXid(), userId,money);
    }
    @RequestMapping("commit")
    public boolean commit(@RequestBody BusinessActionContext actionContext){
        try {
            return accountService.commit(actionContext.getXid());
        }catch (IllegalStateException e){
            log.error("commit error:", e);
            return true;
        }
    }

    @RequestMapping("rollback")
    public boolean rollback(@RequestBody BusinessActionContext actionContext){
        try {
            return accountService.rollback(actionContext.getXid());
        }catch (IllegalStateException e){
            log.error("rollback error:", e);
            return true;
        }
    }

}
