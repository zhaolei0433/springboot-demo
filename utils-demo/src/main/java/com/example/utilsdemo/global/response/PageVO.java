package com.example.utilsdemo.global.response;

/**
 * @author luzh
 * createTime 2017年9月4日 下午5:39:28
 */
public class PageVO<T> {
    private Long total;
    private Integer currentPage;
    private Integer totalPage;
    private T data;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public PageVO(long total, int currentPage, int totalPage, T data) {
        super();
        this.total = total;
        this.currentPage = currentPage;
        this.totalPage = totalPage;
        this.data = data;
    }

    @Override
    public String toString() {
        return "PageVO [total=" + total + ", currentPage=" + currentPage + ", totalPage="
                + totalPage + ", data=" + data + "]";
    }
}
