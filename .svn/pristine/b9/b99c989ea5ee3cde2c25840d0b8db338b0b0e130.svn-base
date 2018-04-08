package com.dyuanit.atm.springbootatm.controller;


import com.dyuanit.atm.springbootatm.exception.TransferException;
import com.dyuanit.atm.springbootatm.service.OpenAccountService;
import com.dyuanit.atm.springbootatm.vo.AjaxVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class OpenAccountDemoController extends BaseController {


    @Autowired
    private OpenAccountService openAccountService;





    @RequestMapping("/openAccount")
    @ResponseBody
    public AjaxVO openAccount(String cardNum, String pwd, HttpSession session) {
        try {
            openAccountService.openAccount(getUserId(session),cardNum,pwd);
            return AjaxVO.success();
        } catch (TransferException te) {
            return AjaxVO.failed(te.getMessage());
        } catch (Exception e) {
            logger.error("开户异常", e);
            return AjaxVO.failed("请联系客服");
        }
    }


}
