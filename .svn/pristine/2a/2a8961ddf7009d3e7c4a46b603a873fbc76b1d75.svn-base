package com.dyuanit.atm.springbootatm.controller;

import com.dyuanit.atm.springbootatm.entity.User;
import com.dyuanit.atm.springbootatm.exception.TransferException;
import com.dyuanit.atm.springbootatm.service.UserService;
import com.dyuanit.atm.springbootatm.vo.AjaxVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
public class UserController extends BaseController {

    @Resource
    private UserService userService;

    public UserController() {
        System.out.println("user controller...");
    }

    @RequestMapping("/regist")
    @ResponseBody
    public AjaxVO regist() {
        userService.regist();
        return AjaxVO.success();
    }

    @RequestMapping("/login")
    @ResponseBody
    public AjaxVO login(HttpSession session,
                        @RequestParam("loginName") String loginName,
                        @RequestParam("password") String password) {

        try {
           User user= userService.login(loginName,password);
            session.setAttribute("user", user);
            return AjaxVO.success();
        } catch (TransferException te) {
            return AjaxVO.failed(te.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxVO.failed("请联系客服");
        }

    }






}
