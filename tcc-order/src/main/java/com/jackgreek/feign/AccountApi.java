package com.jackgreek.feign;


import com.jackgreek.utils.R;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;
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
@LocalTCC
public interface AccountApi {

    /**
     * 扣减账户余额
     * @param userId 用户id
     * @param money 金额
     * @return
     */
    @PostMapping("/account/decrease")
    @TwoPhaseBusinessAction(name = "accountApi", commitMethod = "commit1", rollbackMethod = "rollback1")
    boolean decrease(@RequestBody BusinessActionContext actionContext,@RequestParam("userId") Long userId, @RequestParam("money") BigDecimal money);

    /**
     * Commit boolean.
     *
     * @param actionContext save xid
     * @return the boolean
     */
    @RequestMapping("/account/commit")
    boolean commit1(@RequestBody BusinessActionContext actionContext);

    /**
     * Rollback boolean.
     *
     * @param actionContext save xid
     * @return the boolean
     */
    @RequestMapping("/account/rollback")
    boolean rollback1(@RequestBody BusinessActionContext actionContext);
}
