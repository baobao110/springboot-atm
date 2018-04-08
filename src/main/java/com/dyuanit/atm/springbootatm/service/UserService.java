package com.dyuanit.atm.springbootatm.service;

import com.dyuanit.atm.springbootatm.dao.UserDao;
import com.dyuanit.atm.springbootatm.entity.User;
import com.dyuanit.atm.springbootatm.exception.TransferException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;
    /**
     * 开户
     */
    @Transactional(rollbackFor = Exception.class)
    public void inserUser(User user, String loginCode) {
        user.setLoginCode(loginCode);
        userDao.insertUser(user);

    }

    public User login(String loginName, String password) {

        if (StringUtils.isAnyBlank(loginName, password)) {
            throw new TransferException("账户密码不能为空");
        }

        User user = userDao.getUserByloginCode(loginName);

        if (null == user) {
            throw new TransferException("用户名或密码不正确");
        }

        if (!password.equals(user.getPassword())) {
            throw new TransferException("用户名或密码不正确");
        }

       return user;
    }

    public User getUser(int userId) {
        return userDao.selectUserById(userId);
    }

    public void regist() {
        User user = new User();
        user.setLoginCode("xx");
        user.setUserName("xxx");
        user.setPassword("xxxx");
        userDao.insertUser(user);
       // user  = userDao.getUserByloginCode(user.getLoginCode());
        System.out.println(user.getId());
    }
}
