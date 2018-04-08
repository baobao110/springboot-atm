package com.dyuanit.atm.springbootatm.dao;

import com.dyuanit.atm.springbootatm.entity.Card;
import com.dyuanit.atm.springbootatm.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {

    User getUserByloginCode(@Param("loginName") String loginName);
    List<Card> listCardByUserId(@Param("userId") Integer userId);
    int insertUser(User user);

    User selectUserById(@Param("userId") Integer userId);
}
