package com.jackgreek.feign;


import com.jackgreek.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * @Author: jackgeeks
 * @ProjectName: springcloud-seata
 * @Package: com.jackgreek
 * @ClassName: AccountApi
 * @Description: @todo
 * @CreateDate: 2021/7/22 14:33
 * @Version: 1.0
 */
@FeignClient(value = "tcc-account")
public interface AccountApi {

    /**
     * 扣减账户余额
     * @param userId 用户id
     * @param money 金额
     * @return
     */
    @PostMapping("/account/decrease")
    void decrease(@RequestParam("userId") Long userId, @RequestParam("money") BigDecimal money);
}
