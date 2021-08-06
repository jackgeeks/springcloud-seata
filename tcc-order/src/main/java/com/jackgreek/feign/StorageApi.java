package com.jackgreek.feign;

import com.jackgreek.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
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
public interface StorageApi {

    /**
     * 扣减库存
     * @param productId
     * @param count
     * @return
     */
    @PostMapping(value = "/storage/decrease")
    void decrease(@RequestParam("productId") Long productId, @RequestParam("count") Integer count);
}
