package com.jackgreek.controller;


import com.jackgreek.service.IStorageService;
import com.jackgreek.utils.R;
import io.seata.rm.tcc.api.BusinessActionContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.juli.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Mht
 * @since 2021-07-22
 */
@RestController
@RequestMapping("/storage")
@Slf4j
public class StorageController {
    @Autowired
    private IStorageService storageService;
    /**
     * 扣减库存
     * @param productId 产品id
     * @param count 数量
     * @return
     */
    @PostMapping("decrease")
    public boolean decrease(@RequestBody BusinessActionContext actionContext, @RequestParam("productId") Long productId, @RequestParam("count") Integer count){

        return storageService.decrease(actionContext.getXid(),productId,count);
    }
    @RequestMapping("commit")
    public boolean commit(@RequestBody BusinessActionContext actionContext){
        try {
            return storageService.commit(actionContext.getXid());
        }catch (IllegalStateException e){
            log.error("commit error:", e);
            return true;
        }
    }

    @RequestMapping("rollback")
    public boolean rollback(@RequestBody BusinessActionContext actionContext){
        try {
            return storageService.rollback(actionContext.getXid());
        }catch (IllegalStateException e){
            log.error("rollback error:", e);
            return true;
        }
    }

}
