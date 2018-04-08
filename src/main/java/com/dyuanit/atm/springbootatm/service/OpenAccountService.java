package com.dyuanit.atm.springbootatm.service;

import com.dyuanit.atm.springbootatm.dao.OpenAccountCardDao;
import com.dyuanit.atm.springbootatm.exception.TransferException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class OpenAccountService {
    @Resource
    private OpenAccountCardDao openAccountCardDao;

    @Transactional(rollbackFor = Exception.class)
    public void openAccount(int userId,String cardNum,String pwd) {

        int count = openAccountCardDao.countCardByCardNo(cardNum);
        if (count > 0) {
            throw new TransferException("银行卡卡号已经存在");
        }

        int rows = openAccountCardDao.insertCard(userId,cardNum,pwd);
        if (rows != 1) {
            throw new TransferException("开户失败");
        }
    }





}
