package com.dyuanit.atm.springbootatm.controller;

import com.dyuanit.atm.springbootatm.exception.TransferException;
import com.dyuanit.atm.springbootatm.service.CardService;
import com.dyuanit.atm.springbootatm.vo.AjaxVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * 处理交易模块相关的请求
 */
@Controller

public class TransferController extends BaseController {

    @Resource
    private CardService cardService;

    @RequestMapping("/doTransfer")
    @ResponseBody
    public AjaxVO doTransfer(HttpSession session,
                             @RequestParam("checkoutCardNo") String checkoutCardNo,
                             @RequestParam("checkinCardNo") String checkinCardNo,
                             @RequestParam("wallet") String wallet,
                             @RequestParam("checkoutCardPassword") Integer checkoutCardPassword) throws Exception {

        try {
            //cardService.transfer(checkoutCardNo, checkinCardNo, wallet, checkoutCardPassword);
            cardService.transferDely(checkoutCardNo, checkinCardNo, wallet, checkoutCardPassword, getUserId(session));
            return AjaxVO.success();
        } catch (TransferException te) {
            return AjaxVO.failed(te.getMessage());
        } catch (Exception e) {
            logger.error("转账异常", e);
            return AjaxVO.failed("系统异常，请联系客服");
        }

    }
}


