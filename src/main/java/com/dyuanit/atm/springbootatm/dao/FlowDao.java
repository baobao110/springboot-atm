package com.dyuanit.atm.springbootatm.dao;

import com.dyuanit.atm.springbootatm.entity.Flow;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FlowDao {

    int save(Flow flow);

    List<Flow> listFlow(@Param("cardNo") String cardNo, @Param("offset") int offset, @Param("pageNum") int pageNum);

    int countListFlow(@Param("cardNo") String cardNo);
}
