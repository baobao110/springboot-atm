package com.dyuanit.atm.springbootatm.service;

import com.dyuanit.atm.springbootatm.dao.FlowDao;
import com.dyuanit.atm.springbootatm.dto.FlowDTO;
import com.dyuanit.atm.springbootatm.entity.Flow;
import com.dyuanit.atm.springbootatm.enums.FlowTypeEnum;
import com.dyuanit.atm.springbootatm.holder.PageHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class FlowService {

    @Autowired
    private FlowDao flowDao;

    public PageHolder listFlow(String cardNo, String pwd, int currentPage) {

        int totalCount = flowDao.countListFlow(cardNo);

        PageHolder<FlowDTO> pageHolder = new PageHolder(currentPage, totalCount);

        List<Flow> flowList = flowDao.listFlow(cardNo, pageHolder.getOffset(), PageHolder.pageNum);

        List<FlowDTO> dtoList = new ArrayList<>(flowList.size());

        for (Flow flow : flowList) {
            FlowDTO dto = new FlowDTO();
            dtoList.add(dto);

            dto.setId(flow.getId());
            dto.setCardId(flow.getCardId());
            dto.setCardNum(flow.getCardNum());
            dto.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(flow.getCreateTime()));
            dto.setTradeType(FlowTypeEnum.getFlowEnum(flow.getTradeType()).getV());
            dto.setWallet(flow.getWallet());
        }

        pageHolder.setData(dtoList);

        return pageHolder;
    }
}
