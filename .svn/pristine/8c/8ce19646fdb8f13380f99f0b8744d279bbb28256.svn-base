package com.dyuanit.atm.springbootatm.holder;

import java.util.List;

public class PageHolder<T> {
    public static final int pageNum = 2;
    private List<T> data;
    private int totalPage;
    private int currentPage;
    private int offset;
    private int totalCount;

    public PageHolder(int currentPage, int totalCount) {
        this.currentPage = currentPage;
        this.totalCount = totalCount;
        init();
    }

    private void init() {
        processPageCount();
        processOffset();
    }

    private void processPageCount() {
        this.totalPage = (totalCount % pageNum) == 0 ? totalCount/pageNum : totalCount/pageNum + 1;
    }

    private void processOffset() {
        this.offset = (currentPage - 1) * pageNum;
    }

    public int getOffset() {
        return offset;
    }

    public List<T> getData() {
        return data;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
