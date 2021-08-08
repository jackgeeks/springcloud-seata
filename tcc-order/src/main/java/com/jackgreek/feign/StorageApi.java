package com.jackgreek.feign;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: jackgeeks
 * @ProjectName: springcloud-seata
 * @Package: com.jackgreek
 * @ClassName: StorageApi
 * @Description: @todo
 * @CreateDate: 2021/7/22 14:33
 * @Version: 1.0
 */
@FeignClient(value = "tcc-storage")
@LocalTCC
public interface StorageApi {

    /**
     * 扣减库存
     * @param productId
     * @param count
     * @return
     */
    @PostMapping(value = "/storage/decrease")
    @TwoPhaseBusinessAction(name = "storageApi", commitMethod = "commit", rollbackMethod = "rollback")
    boolean decrease(@RequestBody BusinessActionContext actionContext,@RequestParam("productId") Long productId, @RequestParam("count") Integer count);
    /**
     * 提交事务
     * @param actionContext save xid
     * @return
     */
    @GetMapping(value = "/storage/commit")
    boolean commit(@RequestBody BusinessActionContext actionContext);

    /**
     * 回滚事务
     * @param actionContext save xid
     * @return
     */
    @GetMapping(value = "/storage/rollback")
    boolean rollback(@RequestBody BusinessActionContext actionContext);
}
