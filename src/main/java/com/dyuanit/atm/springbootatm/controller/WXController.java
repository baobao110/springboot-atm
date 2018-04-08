package com.dyuanit.atm.springbootatm.controller;

import com.dyuanit.atm.springbootatm.dto.WxUserInfoDTO;
import com.dyuanit.atm.springbootatm.entity.User;
import com.dyuanit.atm.springbootatm.entity.WxAuthen;
import com.dyuanit.atm.springbootatm.exception.TransferException;
import com.dyuanit.atm.springbootatm.service.UserService;
import com.dyuanit.atm.springbootatm.service.WxAuthenService;
import com.dyuanit.atm.springbootatm.vo.AjaxVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class WXController extends BaseController {

    @Autowired
    private WxAuthenService wxAuthenService;

    @Autowired
    private UserService userService;

    @RequestMapping("/toWxLogin")
    public String toWxLogin(HttpServletResponse response) {
        return "redirect:" + wxAuthenService.getCodeUrl();
    }

    @RequestMapping("/wxAuthenCallBack")
    @ResponseBody
    public ModelAndView wxAuthenCallBack(String code, String state, HttpSession session) {
        System.out.println("code="+ code);
        System.out.println("state=" + state);

        WxAuthen wxAuthenInfo = wxAuthenService.getAccessToken(code);

        //跳转页面 1-绑定页面 2-用户中心页面
        WxAuthen wxAuthen = wxAuthenService.prcessAuthen(wxAuthenInfo);

        ModelAndView mv = new ModelAndView();

        if (null == wxAuthen.getUserId()) {
            mv.setViewName("wxAuthen");
            WxUserInfoDTO wxUserInfoDTO = wxAuthenService.getWxUserInfo(wxAuthen);

            mv.addObject("nickname", wxUserInfoDTO.getNickname());
            mv.addObject("avatarImg", wxUserInfoDTO.getHeadimgurl());

            session.setAttribute("wxopenid", wxAuthen.getOpenid());
            return mv;
        }

        User user = userService.getUser(wxAuthen.getUserId());
        saveUser(session, user);

        mv.setViewName("mainPage");

        return mv;
    }

    @RequestMapping("/wxBind")
    @ResponseBody
    public AjaxVO wxBind(String username, String pwd, HttpSession session) {
        Object obj = session.getAttribute("wxopenid");
        if (null == obj) {
            return AjaxVO.failed("绑定失败");
        }

        try {
            User user = wxAuthenService.bind(username, pwd, obj.toString());
            saveUser(session, user);
        } catch(TransferException te) {
            return AjaxVO.failed(te.getMessage());
        } catch (Exception e) {
            return AjaxVO.failed("绑定异常，请联系客服");
        }

        return AjaxVO.success();
    }
}
