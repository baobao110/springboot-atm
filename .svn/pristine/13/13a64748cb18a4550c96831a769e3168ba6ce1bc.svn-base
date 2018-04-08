package com.dyuanit.atm.springbootatm.enums;

import com.dyuanit.atm.springbootatm.exception.TransferException;

public enum TransferEnums {

    to_be_treated(0, "待处理"), treated(1, "已处理"), cancal(2, "取消");

    private int k;
    private String v;

    private TransferEnums(int k, String v) {
        this.k = k;
        this.v = v;
    }

    public static TransferEnums getEnums(int k) {
        for (TransferEnums em : values()) {
            if (k == em.getK()) {
                return em;
            }
        }

        throw new TransferException("转账状态无效");
    }

    public int getK() {
        return k;
    }

    public String getV() {
        return v;
    }
}
