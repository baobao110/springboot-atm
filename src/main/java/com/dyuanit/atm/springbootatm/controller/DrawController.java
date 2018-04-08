package com.dyuanit.atm.springbootatm.controller;

import com.dyuanit.atm.springbootatm.enums.FlowTypeEnum;
import com.dyuanit.atm.springbootatm.exception.TransferException;
import com.dyuanit.atm.springbootatm.service.BalanceService;
import com.dyuanit.atm.springbootatm.service.DrawService;
import com.dyuanit.atm.springbootatm.vo.AjaxVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * 处理交易模块相关的请求
 */
@Controller
public class DrawController extends BaseController {

    @Resource
    private DrawService drawService;

    @Autowired
    private BalanceService balanceService;

    @RequestMapping("/doDraw")
    @ResponseBody
    public AjaxVO doDraw(String cardNum, String amount, String pwd, HttpSession session) {
        try {
            drawService.draw(getUserId(session), amount, pwd, cardNum);
            return AjaxVO.success();
        } catch (TransferException te) {
            return AjaxVO.failed(te.getMessage());
        } catch (Exception e) {
            logger.error("取款异常", e);
            return AjaxVO.failed("请联系客服");
        }
    }

    @RequestMapping("/xx")
    public void xx() {
        //balanceService.subBalance("6002", "2000", FlowTypeEnum.draw_flow.getK());
        balanceService.processBalance("6002", "2000", FlowTypeEnum.draw_flow.getK(), false);
    }



}

