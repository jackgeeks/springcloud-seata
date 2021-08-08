package com.jackgreek.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jackgreek.entity.Account;
import com.jackgreek.mapper.AccountMapper;
import com.jackgreek.service.IAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Mht
 * @since 2021-07-21
 */
@Service
@Slf4j
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements IAccountService {

    private Map<String, Statement> statementMap = new ConcurrentHashMap<>(100);
    private Map<String, Connection> connectionMap = new ConcurrentHashMap<>(100);

    @Resource
    private DataSource hikariDataSource;

    @Override
    public boolean decrease(String xid,Long userId, BigDecimal payAmount) {
        log.info("commit, xid:{}", xid);
        log.info("------->尝试扣减账户开始account");
        //模拟超时异常，全局事务回滚
        /*try {
            Thread.sleep(30*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        try {
            //尝试扣减账户金额,事务不提交
            Connection connection = hikariDataSource.getConnection();
            connection.setAutoCommit(false);
            String sql = "UPDATE account SET balance = balance - ?,used = used + ? where user_id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setBigDecimal(1, payAmount);
            stmt.setBigDecimal(2, payAmount);
            stmt.setLong(3, userId);
            statementMap.put(xid, stmt);
            connectionMap.put(xid, connection);
        } catch (Exception e) {
            log.error("decrease parepare failure:", e);
            return false;
        }

        log.info("------->尝试扣减账户结束account");

        return true;

    }

    @Override
    public boolean commit(String xid) {
        log.info("扣减账户金额, commit, xid:{}", xid);
        PreparedStatement statement = (PreparedStatement) statementMap.get(xid);
        Connection connection = connectionMap.get(xid);
        try {
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            log.error("扣减账户失败:", e);
            return false;
        }finally {
            try {
                statementMap.remove(xid);
                connectionMap.remove(xid);
                if (null != statement){
                    statement.close();
                }
                if (null != connection){
                    connection.close();
                }
            } catch (SQLException e) {
                log.error("扣减账户提交事务后关闭连接池失败:", e);
            }
        }
        return true;
    }

    @Override
    public boolean rollback(String xid) {
        log.info("扣减账户金额, rollback, xid:{}", xid);
        PreparedStatement statement = (PreparedStatement) statementMap.get(xid);
        Connection connection = connectionMap.get(xid);
        try {
            connection.rollback();
        } catch (SQLException e) {
            return false;
        }finally {
            try {
                if (null != statement){
                    statement.close();
                }
                if (null != connection){
                    connection.close();
                }
                statementMap.remove(xid);
                connectionMap.remove(xid);
            } catch (SQLException e) {
                log.error("扣减账户回滚事务后关闭连接池失败:", e);
            }
        }
        return true;
    }
}
