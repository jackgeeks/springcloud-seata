package com.jackgreek.controller;


import com.jackgreek.service.IStorageService;
import com.jackgreek.utils.R;
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
    public R decrease(@RequestParam("productId") Long productId, @RequestParam("count") Integer count){
        storageService.decrease(productId,count);
        return R.ok().put("date","Decrease storage success");
    }

}
