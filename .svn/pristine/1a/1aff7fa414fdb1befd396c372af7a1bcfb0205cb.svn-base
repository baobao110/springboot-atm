package com.dyuanit.atm.springbootatm.service;

import com.alibaba.fastjson.JSON;
import com.dyuanit.atm.springbootatm.dao.WxAuthenDao;
import com.dyuanit.atm.springbootatm.dto.WxUserInfoDTO;
import com.dyuanit.atm.springbootatm.entity.User;
import com.dyuanit.atm.springbootatm.entity.WxAuthen;
import com.dyuanit.atm.springbootatm.exception.TransferException;
import com.dyuanit.atm.springbootatm.util.ApiConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WxAuthenService {

    @Value("${wx.authen.code.url}")
    private String codeURl;

    @Value("${wx.authen.appid}")
    private String appid;

    @Value("${wx.authen.redirect.url}")
    private String redirectUri;

    @Value("${wx.authen.response.type}")
    private String responseType;

    @Value("${wx.authen.scope}")
    private String scope;

    @Value("${wx.authen.app.secret}")
    private String appSecret;

    @Value("${wx.authen.token.url}")
    private String tokenURL;

    @Value("${wx.authen.user.info.url}")
    private String wxInfoURL;

    @Autowired
    private WxAuthenDao wxAuthenDao;

    @Autowired
    private UserService userService;

    public WxAuthen prcessAuthen(WxAuthen wxAuthen) {

        WxAuthen wx = wxAuthenDao.selectByOpenid(wxAuthen.getOpenid());
        if (null == wx) {
            int rows = wxAuthenDao.save(wxAuthen);
            if (1 != rows) {
                throw new TransferException("认证失败");
            }

            return wxAuthen;
        }

        int rows = wxAuthenDao.updateAuthenInfo(wxAuthen);
        if (1 != rows) {
            throw new TransferException("认证失败");
        }

        wx = wxAuthenDao.selectByOpenid(wxAuthen.getOpenid());
       return wx;
    }

    public User bind(String username, String pwd, String openid) {
        User user = userService.login(username, pwd);
        int rows = wxAuthenDao.update(openid, user.getId());
        if (1 != rows) {
            throw new TransferException("绑定失败");
        }

        return user;
    }

    public String getCodeUrl() {
        StringBuilder sx = new StringBuilder();
        sx.append(codeURl)
                .append("appid").append("=").append(appid)
                .append("&")
                .append("redirect_uri").append("=").append(redirectUri)
                .append("&")
                .append("response_type").append("=").append(responseType)
                .append("&")
                .append("scope").append("=").append(scope);
        return sx.toString();
    }

    public WxAuthen getAccessToken(String code) {
        //发送HTTP请求
        StringBuilder sx = new StringBuilder();
        sx.append(tokenURL)
                .append("appid").append("=").append(appid)
                .append("&")
                .append("secret").append("=").append(appSecret)
                .append("&")
                .append("code").append("=").append(code)
                .append("&")
                .append("grant_type").append("=").append("authorization_code");

        String msg = ApiConnector.get(sx.toString(), null);
        System.out.println("微信返回信息：" + msg);

        return JSON.parseObject(msg, WxAuthen.class);
    }

    public WxUserInfoDTO getWxUserInfo(WxAuthen wxAuthen) {
        StringBuilder sx = new StringBuilder();
        sx.append(wxInfoURL)
                .append("?")
                .append("access_token").append("=").append(wxAuthen.getAccessToken())
                .append("&")
                .append("openid").append("=").append(wxAuthen.getOpenid());

        String msg = ApiConnector.get(sx.toString(), null);
        System.out.println("微信用户信息：" + msg);

        return JSON.parseObject(msg, WxUserInfoDTO.class);
    }


}
