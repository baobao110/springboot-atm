package com.dyuanit.atm.springbootatm.enums;

import com.dyuanit.atm.springbootatm.exception.TransferException;

public enum  FlowTypeEnum {

    deposit_flow(1, "存钱"), draw_flow(2, "取钱"), transfer_in_flow(3, "转账收入"), transfer_out_flow(4, "转账支出"), transfer_rollback(5, "转账取消");

    private int k;
    private String v;

    private FlowTypeEnum(int k, String v) {
        this.k = k;
        this.v = v;
    }

    public static FlowTypeEnum getFlowEnum(int k) {
        for (FlowTypeEnum flowEnum : values()) {
            if (flowEnum.getK() == k) {
                return flowEnum;
            }
        }

        throw new TransferException("流水类型异常");
    }

    public int getK() {
        return k;
    }

    public String getV() {
        return v;
    }
}
