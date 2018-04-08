package com.dyuanit.atm.springbootatm.entity;

import java.util.Date;

public class Transfer {
    private Integer id;

    private String outCard;

    private String inCard;

    private String amount;

    private Byte status;

    private Date createTime;

    private Date modifyTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOutCard() {
        return outCard;
    }

    public void setOutCard(String outCard) {
        this.outCard = outCard == null ? null : outCard.trim();
    }

    public String getInCard() {
        return inCard;
    }

    public void setInCard(String inCard) {
        this.inCard = inCard == null ? null : inCard.trim();
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount == null ? null : amount.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}