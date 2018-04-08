package com.dyuanit.atm.springbootatm.service;


import com.dyuanit.atm.springbootatm.dao.CardDao;
import com.dyuanit.atm.springbootatm.dao.FlowDao;
import com.dyuanit.atm.springbootatm.entity.Card;
import com.dyuanit.atm.springbootatm.entity.Flow;
import com.dyuanit.atm.springbootatm.exception.TransferException;
import com.dyuanit.atm.springbootatm.util.BigDemicalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BackService {

    @Autowired
    private CardDao cardDao;

    @Autowired
    private FlowDao flowDao;

    @Transactional(rollbackFor = Exception.class)
    public void processBalance(String cardNum, String amount, int flowType, boolean isAdd) {
        Card card = cardDao.selectCard4Lock(cardNum);
        if (null == card) {
            throw new TransferException("转入卡号不存在");
        }

        String balance = BigDemicalUtil.add(amount, card.getWallet());

        if (!isAdd) {
            if (Double.parseDouble(card.getWallet()) < Double.parseDouble(amount)) {
                throw new TransferException("余额不足");
            }

            balance = BigDemicalUtil.sub(card.getWallet(), amount);
        }

        int rows = cardDao.updateCardById(card.getId(), balance);
        if (1 != rows) {
            throw new TransferException("银行卡操作失败");
        }

        // 增加流水
        Flow flow = new Flow();
        flow.setCardId(card.getId());
        flow.setCardNum(card.getCardNo());
        flow.setTradeType(flowType);
        flow.setWallet(amount);
        rows = flowDao.save(flow);
        if (rows == 0) {
            throw new TransferException("转账失败");
        }

        int i = 10 / 0;
    }
}
