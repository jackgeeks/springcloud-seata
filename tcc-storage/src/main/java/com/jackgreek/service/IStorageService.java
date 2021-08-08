package com.jackgreek.service;

import com.jackgreek.entity.Storage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Mht
 * @since 2021-07-22
 */
public interface IStorageService extends IService<Storage> {

    boolean decrease(String xid,Long productId, Integer count);

    boolean rollback(String xid);

    boolean commit(String xid);
}
