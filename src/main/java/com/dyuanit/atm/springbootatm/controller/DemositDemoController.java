package com.dyuanit.atm.springbootatm.controller;

import com.dyuanit.atm.springbootatm.dto.CardDTO;
import com.dyuanit.atm.springbootatm.exception.TransferException;
import com.dyuanit.atm.springbootatm.service.CardService;
import com.dyuanit.atm.springbootatm.service.DepositService;
import com.dyuanit.atm.springbootatm.vo.AjaxVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class DemositDemoController extends BaseController {

    @Autowired
    private DepositService depositService;

    @Autowired
    private CardService cardService;

    @RequestMapping("/deposit")
    @ResponseBody
    public AjaxVO deposit(String cardNum, String amount, String pwd, HttpSession session) {
        try {
            depositService.deposit(getUserId(session), amount, pwd, cardNum);
            return AjaxVO.success();
        } catch (TransferException te) {
            return AjaxVO.failed(te.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxVO.failed("请联系客服");
        }
    }

    @RequestMapping("/listBankCard")
    @ResponseBody
    public AjaxVO listBankCard(HttpSession session) {
        List<CardDTO> list = cardService.listCard(getUserId(session));
        return AjaxVO.success(list);
    }

}
