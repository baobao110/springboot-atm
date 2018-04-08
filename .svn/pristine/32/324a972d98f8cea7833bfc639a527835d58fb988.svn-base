package com.dyuanit.atm.springbootatm.dao;

import com.dyuanit.atm.springbootatm.entity.Card;
import org.apache.ibatis.annotations.Param;


public interface OpenAccountCardDao {
    Card selectCardByCardNo(@Param("cardNo") String cardNo);
    //开户
    int insertCard(@Param("userId") Integer userId, @Param("cardNo") String cardNo, @Param("pwd") String pwd);
    int countCardByCardNo(@Param("cardNo") String cardNo);

}
