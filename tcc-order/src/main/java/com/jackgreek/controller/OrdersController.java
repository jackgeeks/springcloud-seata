package com.jackgreek.controller;


import com.jackgreek.entity.Orders;
import com.jackgreek.service.IOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.math.BigDecimal;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Mht
 * @since 2021-07-20
 */

@RestController
@RequestMapping("/orders")
public class OrdersController {
    @Autowired
    public IOrdersService iOrdersService;



    /**
     * 创建订单
     * @param order
     * @return
     */
    @PostMapping("create")
    public String create(@RequestBody Orders order){
        iOrdersService.create(order);
        return "Create order success";
    }

    /**
     * 修改订单状态
     * @param userId
     * @param payAmount
     * @param status
     * @return
     */
    @RequestMapping("update")
    String update(@RequestParam("userId") Long userId, @RequestParam("payAmount") BigDecimal payAmount, @RequestParam("status") Integer status){
        iOrdersService.update(userId,payAmount,status);
        return "订单状态修改成功";
    }
}
