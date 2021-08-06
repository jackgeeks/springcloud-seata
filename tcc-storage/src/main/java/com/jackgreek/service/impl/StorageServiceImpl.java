package com.jackgreek.service.impl;

import com.jackgreek.entity.Storage;
import com.jackgreek.mapper.StorageMapper;
import com.jackgreek.service.IStorageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.juli.logging.Log;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Mht
 * @since 2021-07-22
 */
@Service
@Slf4j
public class StorageServiceImpl extends ServiceImpl<StorageMapper, Storage> implements IStorageService {

    @Override
    public void decrease(Long productId, Integer count) {
        log.info("------->扣减库存开始");
        this.baseMapper.decrease(productId,count);
        log.info("------->扣减库存结束");
    }
}
