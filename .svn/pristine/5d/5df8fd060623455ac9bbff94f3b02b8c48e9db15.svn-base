package com.dyuanit.atm.springbootatm.service;

import com.dyuanit.atm.springbootatm.dao.CardDao;
import com.dyuanit.atm.springbootatm.dao.FlowDao;
import com.dyuanit.atm.springbootatm.entity.Card;
import com.dyuanit.atm.springbootatm.entity.Flow;
import com.dyuanit.atm.springbootatm.enums.FlowTypeEnum;
import com.dyuanit.atm.springbootatm.exception.TransferException;
import com.dyuanit.atm.springbootatm.util.BigDemicalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DepositService {

    @Autowired
    private CardDao cardDao;

    @Autowired
    private FlowDao flowDao;

    @Transactional(rollbackFor = Exception.class)
    public void deposit(int userId, String amount, String pwd, String cardNum) {
        //TODO 校验卡的所属人
        Card card = cardDao.selectCardByCardNo(cardNum);
//        if (null == card) {
//            throw new TransferException("卡号不存在");
//        }
//
//        if (!card.getUserId().equals(userId)) {
//            throw new TransferException("卡号不属于你");
//        }

        //TODO 校验密码
        //TODO 校验金额是否合法

        // 修改银行卡金额
        String newBalance = BigDemicalUtil.add(card.getWallet(), amount);
        System.out.println("newBalance=" + newBalance);
        int rows = cardDao.updateCardById(card.getId(), newBalance);
        if (rows == 0) {
            throw new TransferException("存款失败");
        }

        // 增加流水
        Flow flow = new Flow();
        flow.setCardId(card.getId());
        flow.setCardNum(card.getCardNo());
        flow.setTradeType(FlowTypeEnum.deposit_flow.getK());
        flow.setWallet(amount);
        rows = flowDao.save(flow);
        if (rows == 0) {
            throw new TransferException("存款失败");
        }

    }
}
