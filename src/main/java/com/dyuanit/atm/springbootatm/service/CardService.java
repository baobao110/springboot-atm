package com.dyuanit.atm.springbootatm.service;

import com.dyuanit.atm.springbootatm.dao.CardDao;
import com.dyuanit.atm.springbootatm.dao.FlowDao;
import com.dyuanit.atm.springbootatm.dao.TransferMapper;
import com.dyuanit.atm.springbootatm.dto.CardDTO;
import com.dyuanit.atm.springbootatm.entity.Card;
import com.dyuanit.atm.springbootatm.entity.Transfer;
import com.dyuanit.atm.springbootatm.enums.FlowTypeEnum;
import com.dyuanit.atm.springbootatm.enums.TransferEnums;
import com.dyuanit.atm.springbootatm.exception.TransferException;
import com.dyuanit.atm.springbootatm.util.BigDemicalUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 业务层
 */
@Service
public class CardService {

    @Resource
    private CardDao cardDao;

    @Autowired
    private FlowDao flowDao;

    @Autowired
    private TransferMapper transferMapper;

    @Autowired
    private BalanceService balanceService;


    /**
     * 根据卡号id查询
     *
     * @param id
     * @return
     */
    public Card selectCardById(Integer id) {
        return cardDao.selectCardById(id);
    }

    /**
     * 取款
     *
     * @param drawCardNo
     * @param drawWallet
     */
    @Transactional(rollbackFor = Exception.class)
    public void draw(String drawCardNo, String drawWallet) {
//
//      /*******取款********/
//     //查询账户信息
//    Card drawCard = cardMapper.selectCardByCardNo(drawCardNo);
//
//
//      //锁住转出、转入账号
//      drawCard = cardMapper.selectCardByIdAndLock(drawCard.getId());
//
//       if (Double.parseDouble(drawCard.getWallet()) < Double.parseDouble(drawWallet)) {
//          throw new TransferException("账号金额不足");
//       }
//
//      double drawCardFinalMoney = BigDemicalUtil.sub(Double.parseDouble(drawCard.getWallet()), Double.parseDouble(drawWallet));
//
//       //更新账户金额
//      cardMapper.updateCardById(drawCard.getId(), drawCardFinalMoney + "");
//
//
//       /*******添加流水********/
//      CardStream cardStream = new CardStream(drawCard.getId(), drawWallet, StreamType.DRAW.code);
//       cardStreamMapper.insertCardStream(cardStream);
    }


    /**
     * 存款
     *
     * @param depositCardNo
     * @param depositWallet
     */
    public void deposit(String depositCardNo, String depositWallet) {

        /*******存款********/
        //查询账户信息
        Card depositCard = cardDao.selectCardByCardNo(depositCardNo);


        //锁住转出、转入账号
        depositCard = cardDao.selectCardByIdAndLock(depositCard.getId());

        double depositCardFinalMoney = BigDemicalUtil.add(Double.parseDouble(depositCard.getWallet()), Double.parseDouble(depositWallet));

        //更新账户金额
        cardDao.updateCardById(depositCard.getId(), depositCardFinalMoney + "");


//        /*******添加流水********/
//        CardStream cardStream = new CardStream(depositCard.getId(), depositWallet, StreamType.DEPOSIT.code);
//        cardStreamMapper.insertCardStream(cardStream);

    }

    @Transactional(rollbackFor = Exception.class)
    public void transferDely(String checkoutCardNo,
                             String checkinCardNo,
                             String wallet,
                             Integer checkoutCardPassword,
                             int userId) {

        if (StringUtils.isBlank(checkoutCardNo)) {
            throw new TransferException("请选择转出卡号");
        }
        if (StringUtils.isBlank(checkinCardNo)) {
            throw new TransferException("请输入转入卡号");
        }
        if (StringUtils.isBlank(wallet)) {
            throw new TransferException("请输入转帐金额");
        }
        if (null == checkoutCardPassword) {
            throw new TransferException("请输入您的转出银行卡密码");
        }

        if (!StringUtils.isNumeric(checkoutCardNo)) {
            throw new TransferException("请选择正确的转出卡号");
        }
        if (!StringUtils.isNumeric(checkinCardNo)) {
            throw new TransferException("请输入正确的转入卡号");
        }
        if (!StringUtils.isNumeric(wallet)) {
            throw new TransferException("请输入正确的转帐金额");
        }
        if (!StringUtils.isNumeric(wallet)) {
            throw new TransferException("请输入正确的转帐金额");
        }
        String pwd = checkoutCardPassword.toString();
        if (!StringUtils.isNumeric(pwd)) {
            throw new TransferException("请输入正确的转出卡密码");
        }

        Card checkoutCard = cardDao.selectCardByCardNo(checkoutCardNo);
        Card checkinCard = cardDao.selectCardByCardNo(checkinCardNo);

        String getCheckoutCardNo = checkoutCard.getCardNo();
        String getCheckinCardNo = checkinCard.getCardNo();

        if (userId != checkoutCard.getUserId().intValue()) {
            throw new TransferException("请选择您自己的银行卡来进行转出操作");
        }

        if (!getCheckinCardNo.equals(checkinCardNo)) {
            throw new TransferException("转出银行卡不存在");
        }

        Integer getCheckoutCardPassword = checkoutCard.getPwd();
        if (!checkoutCardPassword.equals(getCheckoutCardPassword)){
            throw new TransferException("请输入正确的转出卡密码");
        }

        balanceService.subBalance(checkoutCard.getCardNo(), wallet, FlowTypeEnum.transfer_out_flow.getK());

        // 保存转账记录
        Transfer transfer = new Transfer();
        transfer.setAmount(wallet);
        transfer.setInCard(checkinCardNo);
        transfer.setOutCard(checkoutCardNo);
        transfer.setStatus((byte) TransferEnums.to_be_treated.getK());
        transfer.setCreateTime(new Date());
        transfer.setModifyTime(transfer.getCreateTime());
        int rows = transferMapper.insert(transfer);
        if (1 != rows) {
            throw new TransferException("转账失败");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void transfer(String checkoutCardNo,
                         String checkinCardNo,
                         String wallet,
                         Integer checkoutCardPassword) {

        if (StringUtils.isBlank(checkoutCardNo)) {
            throw new TransferException("请选择转出卡号");
        }
        if (StringUtils.isBlank(checkinCardNo)) {
            throw new TransferException("请输入转入卡号");
        }
        if (StringUtils.isBlank(wallet)) {
            throw new TransferException("请输入转帐金额");
        }
        if (null == checkoutCardPassword) {
            throw new TransferException("请输入您的转出银行卡密码");
        }

        if (!StringUtils.isNumeric(checkoutCardNo)) {
            throw new TransferException("请选择正确的转出卡号");
        }
        if (!StringUtils.isNumeric(checkinCardNo)) {
            throw new TransferException("请输入正确的转入卡号");
        }
        if (!StringUtils.isNumeric(wallet)) {
            throw new TransferException("请输入正确的转帐金额");
        }
        if (!StringUtils.isNumeric(wallet)) {
            throw new TransferException("请输入正确的转帐金额");
        }
        String pwd = checkoutCardPassword.toString();
        if (!StringUtils.isNumeric(pwd)) {
            throw new TransferException("请输入正确的转出卡密码");
        }

        Card checkoutCard = cardDao.selectCardByCardNo(checkoutCardNo);
        Card checkinCard = cardDao.selectCardByCardNo(checkinCardNo);
        String getCheckoutCardNo = checkoutCard.getCardNo();
        String getCheckinCardNo = checkinCard.getCardNo();
        if (!getCheckoutCardNo.equals(checkoutCardNo)) {
            throw new TransferException("请选择您自己的银行卡来进行转出操作");
        }
        if (!getCheckinCardNo.equals(checkinCardNo)) {
            throw new TransferException("转出银行卡不存在");
        }

        checkoutCard = cardDao.selectCardByIdAndLock2(checkoutCard.getId());
        checkinCard = cardDao.selectCardByIdAndLock2(checkinCard.getId());

        String getWallet = checkoutCard.getWallet();
        if (Double.parseDouble(getWallet) < Double.parseDouble(wallet)) {
            throw new TransferException("该卡余额不足");
        }

        Integer getCheckoutCardPassword = checkoutCard.getPwd();
        if (!checkoutCardPassword.equals(getCheckoutCardPassword)){
            throw new TransferException("请输入正确的转出卡密码");
        }

        double checkoutCardFinalMoney = BigDemicalUtil.sub(Double.parseDouble(checkoutCard.getWallet()), Double.parseDouble(wallet));
        double checkinCardFinalMoney = BigDemicalUtil.add(Double.parseDouble(checkinCard.getWallet()), Double.parseDouble(wallet));

        cardDao.updateCardById(checkoutCard.getId(), checkoutCardFinalMoney + "");
        cardDao.updateCardById(checkinCard.getId(), checkinCardFinalMoney + "");


        /*******添加流水********/
//        CardStream cardStream = new CardStream(checkoutCard.getId(), wallet, StreamType.TRANSFER_OUT.code);
//        cardStreamMapper.insertCardStream(cardStream);
//
//        cardStream = new CardStream(checkinCard.getId(), wallet, StreamType.TRANSFER_IN.code);
//        cardStreamMapper.insertCardStream(cardStream);
    }


    public List<CardDTO> listCard(int usreId) {
        List<Card> list = cardDao.listCardByUserId(usreId);
        List<CardDTO> dtoList = new ArrayList<>();
        for (Card card : list) {
            CardDTO dto = new CardDTO();
            dtoList.add(dto);

            dto.setCardNo(card.getCardNo());
            dto.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(card.getCreateTime()));
            dto.setId(card.getId());
            dto.setUserId(card.getUserId());
            dto.setWallet(card.getWallet());
        }

        return dtoList;
    }

}
