package com.dyuanit.atm.springbootatm.service;

import com.dyuanit.atm.springbootatm.dao.CardDao;
import com.dyuanit.atm.springbootatm.dao.FlowDao;
import com.dyuanit.atm.springbootatm.entity.Card;
import com.dyuanit.atm.springbootatm.entity.Flow;
import com.dyuanit.atm.springbootatm.enums.FlowTypeEnum;
import com.dyuanit.atm.springbootatm.exception.TransferException;
import com.dyuanit.atm.springbootatm.util.BigDemicalUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DrawService {

    @Autowired
    private CardDao cardDao;
    @Autowired
    private FlowDao flowDao;

    @Transactional(rollbackFor = Exception.class)
    public synchronized void draw(int userId, String amount, String pwd, String cardNum) {
       // Card card = cardDao.selectCardByCardNo(cardNum);
        Card card = cardDao.selectCard4Lock(cardNum);

        if (null == card) {
            throw new TransferException("卡号不存在");
        }
        if (!card.getUserId().equals(userId)) {
            throw new TransferException("卡号不属于你");
        }
        if (StringUtils.isBlank(pwd)) {
            throw new TransferException("密码错误");
        }
        if (!card.getPwd().equals(Integer.parseInt(pwd))) {
            throw new TransferException("密码错误");
        }
        if (StringUtils.isBlank(amount)) {
            throw new TransferException("请输入正确取款金额");
        }
        if (!StringUtils.isNumeric(amount)) {
            throw new TransferException("请输入正确取款金额");
        }
        if (Integer.parseInt(amount) <= 0) {
            throw new TransferException("请输入正确取款金额");
        }
        if (Double.parseDouble(card.getWallet()) < Double.parseDouble(amount)) {
            throw new TransferException("账号金额不足");
        }

        String newBalance = BigDemicalUtil.sub(card.getWallet(), amount);
        int rows = cardDao.updateCardById(card.getId(), newBalance);

        if (rows == 0) {
            throw new TransferException("取款失败");
        }

        Flow flow = new Flow();
        flow.setCardId(card.getId());
        flow.setCardNum(card.getCardNo());
        flow.setTradeType(FlowTypeEnum.draw_flow.getK());
        flow.setWallet(amount);
        rows = flowDao.save(flow);

        if (rows == 0) {
            throw new TransferException("取款失败");
        }

    }
}
