package com.dyuanit.atm.springbootatm.dao;

import com.dyuanit.atm.springbootatm.entity.Card;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CardDao {

    Card selectCardByCardNo(@Param("cardNo") String cardNo);
    Card selectCardByIdAndLock(@Param("cardId") Integer cardId);
    Card selectCardByIdAndLock2(@Param("id") Integer id);
    Card selectCardById(@Param("cardId") Integer cardId);
    int updateCardById(@Param("cardId") Integer cardId, @Param("wallet") String wallet);
    List<Card> listCardByUserId(@Param("userId") Integer userId);

    Card selectCard4Lock(@Param("cardNo") String cardNo);

}
