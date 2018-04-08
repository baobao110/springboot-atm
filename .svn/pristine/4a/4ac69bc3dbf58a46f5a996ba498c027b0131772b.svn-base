package com.dyuanit.atm.springbootatm.dao;

import com.dyuanit.atm.springbootatm.entity.Transfer;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface TransferMapper {

    int insert(Transfer record);

    Transfer selectByPrimaryKey(Integer id);

    int updateStatus(@Param("id") Integer id, @Param("status") Integer status);

    List<Transfer> listTransfer(@Param("date") Date date,
                                @Param("status") Integer status,
                                @Param("offset") Integer offset);

}