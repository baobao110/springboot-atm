package com.dyuanit.atm.springbootatm.controller;

import com.dyuanit.atm.springbootatm.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;

public abstract class BaseController {

    protected Logger logger = LoggerFactory.getLogger(OpenAccountDemoController.class);

    protected int getUserId(HttpSession session) {
//        Object obj = session.getAttribute("user");
//        if (null == obj) {
//            throw new TransferException("用户为登录");
//        }
//
//        if (obj instanceof User) {
//            User user = (User)obj;
//            return user.getId();
//        } else {
//            throw new TransferException("用户未登录");
//        }

        return 1;
    }

    protected String getUsername(HttpSession session) {
//        Object obj = session.getAttribute("user");
//        if (null == obj) {
//            throw new TransferException("用户为登录");
//        }
//
//        if (obj instanceof User) {
//            User user = (User)obj;
//            return user.getUserName();
//        } else {
//            throw new TransferException("用户未登录");
//        }

        return "tom";

    }

    protected User getUser(HttpSession session) {
//        Object obj = session.getAttribute("user");
//        if (null == obj) {
//            throw new TransferException("用户为登录");
//        }
//
//        if (obj instanceof User) {
//            return (User)obj;
//        } else {
//            throw new TransferException("用户未登录");
//        }

      return new User();

    }

    protected void saveUser(HttpSession session, User user) {
        session.setAttribute("user", user);
    }
}
