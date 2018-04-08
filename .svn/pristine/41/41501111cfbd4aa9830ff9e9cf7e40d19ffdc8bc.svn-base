package com.dyuanit.atm.springbootatm.controller;

import com.dyuanit.atm.springbootatm.dto.FlowDTO;
import com.dyuanit.atm.springbootatm.holder.PageHolder;
import com.dyuanit.atm.springbootatm.service.FlowService;
import com.dyuanit.atm.springbootatm.vo.AjaxVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.util.List;

@Controller
public class FlowController extends BaseController {

    @Autowired
    private FlowService flowService;



    @RequestMapping("/listFlow")
    @ResponseBody
    public AjaxVO listFlow(int currentPage, String pwd, String cardNo) {
        return AjaxVO.success(flowService.listFlow(cardNo, pwd, currentPage));
    }

    @RequestMapping("/downFlow")
    public void downFlow(String cardNum, String pwd, HttpServletResponse response) {
        int currnetPage = 1;

        response.setContentType("application/octet-stream");
        response.addHeader("Content-Disposition", "attachment;filename=" + cardNum + ".csv");

        try(BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(response.getOutputStream(), "UTF-8"))) {

            StringBuilder sx = new StringBuilder();
            sx.append("ID").append(",").append("卡号").append(",").append("金额").append(",").append("交易类型").append(",").append("时间");
            bw.write(sx.toString());
            bw.newLine();
            bw.flush();

            while (true) {
                PageHolder<FlowDTO> pageHolder = flowService.listFlow(cardNum, pwd, currnetPage);

                List<FlowDTO> list = pageHolder.getData();
                for (FlowDTO dto : list) {
                    sx = new StringBuilder();
                    sx.append(String.valueOf(dto.getId())).append(",").append(dto.getCardNum()).append(",").append(dto.getWallet()).append(",").append(dto.getTradeType()).append(",").append(dto.getCreateTime());
                    bw.write(sx.toString());
                    bw.newLine();
                    bw.flush();
                }

                currnetPage ++;

                if (currnetPage > pageHolder.getTotalPage()) {
                    break;
                }
            }

        } catch(Exception e) {
            logger.error("下载异常", e);
        }
    }

}
