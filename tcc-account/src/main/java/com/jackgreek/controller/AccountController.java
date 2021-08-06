package com.jackgreek.controller;


import com.jackgreek.service.IAccountService;
import com.jackgreek.utils.R;
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
    public R decrease(@RequestParam("userId") Long userId, @RequestParam("money") BigDecimal money){
        accountService.decrease(userId,money);
        return R.ok().put("date","Acount decrease success");
    }

}
