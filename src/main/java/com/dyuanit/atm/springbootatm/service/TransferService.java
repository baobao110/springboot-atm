package com.dyuanit.atm.springbootatm.service;

import com.dyuanit.atm.springbootatm.dao.CardDao;
import com.dyuanit.atm.springbootatm.dao.FlowDao;
import com.dyuanit.atm.springbootatm.dao.TransferMapper;
import com.dyuanit.atm.springbootatm.entity.Card;
import com.dyuanit.atm.springbootatm.entity.Transfer;
import com.dyuanit.atm.springbootatm.enums.FlowTypeEnum;
import com.dyuanit.atm.springbootatm.enums.TransferEnums;
import com.dyuanit.atm.springbootatm.exception.TransferException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class TransferService {

    @Autowired
    private TransferMapper transferMapper;

    @Autowired
    private CardDao cardDao;

    @Autowired
    private FlowDao flowDao;

    @Autowired
    private BalanceService balanceService;

    public List<Transfer> listToBeTransferRecord(int currentPage) {

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, -10);
        Date targetDate = cal.getTime();

        int offset = (currentPage - 1) * 2;
        return transferMapper.listTransfer(targetDate, TransferEnums.to_be_treated.getK(), offset);
    }

    @Transactional(rollbackFor = Exception.class)
    public void processTransferRollBack(Transfer transfer) {
        System.out.println("正在处理卡号回滚：" + transfer.getInCard());

        // 转出卡号加钱
        Card card = cardDao.selectCard4Lock(transfer.getOutCard());
        if (null == card) {
            throw new TransferException("转出卡号不存在");
        }

        // 修改转账记录状态
        int rows = transferMapper.updateStatus(transfer.getId(), TransferEnums.cancal.getK());
        if (1 != rows) {
            throw new TransferException("转账回滚处理失败");
        }

        //加钱
        balanceService.addBalance(card.getCardNo(), transfer.getAmount(), FlowTypeEnum.transfer_rollback.getK());
    }

    @Transactional(rollbackFor = Exception.class)
    public void processTransferIn(Transfer transfer) {

        System.out.println("正在处理卡号" + transfer.getInCard());

        // 转入卡号加钱
        Card card = cardDao.selectCard4Lock(transfer.getInCard());
        if (null == card) {
            throw new TransferException("转入卡号不存在");
        }

        // 修改转账记录状态
        int rows = transferMapper.updateStatus(transfer.getId(), TransferEnums.treated.getK());
        if (1 != rows) {
            throw new TransferException("转账处理失败");
        }

        //加钱
        balanceService.addBalance(card.getCardNo(), transfer.getAmount(), FlowTypeEnum.transfer_in_flow.getK());

        int i = 10 / 0;
    }
}
