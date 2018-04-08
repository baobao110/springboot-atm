package com.dyuanit.atm.springbootatm.dao;

import com.dyuanit.atm.springbootatm.entity.WxAuthen;
import org.apache.ibatis.annotations.Param;

public interface WxAuthenDao {

    int save(WxAuthen wxAuthen);

    WxAuthen selectByOpenid(@Param("openid") String openid);

    int del(@Param("openid") String openid);

    int updateAuthenInfo(WxAuthen wxAuthen);

    int update(@Param("openid") String openid, @Param("userId") Integer userId);
}
