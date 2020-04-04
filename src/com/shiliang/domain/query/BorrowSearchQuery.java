package com.shiliang.domain.query;

/**
 * @author SL
 * @create 2020-03-28 15:59
 * @description 图书借阅查询
 */
public class BorrowSearchQuery extends Query {
    //借阅编号
    private String borrowId;
    //读者证件号
    private String paperNO;
    //图书ISBN号
    private String ISBN;

    public BorrowSearchQuery() {
    }

    public String getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(String borrowId) {
        this.borrowId = borrowId;
    }

    public String getPaperNO() {
        return paperNO;
    }

    public void setPaperNO(String paperNO) {
        this.paperNO = paperNO;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }
}
