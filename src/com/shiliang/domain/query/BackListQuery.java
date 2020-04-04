package com.shiliang.domain.query;

/**
 * @author SL
 * @create 2020-03-27 23:25
 * @description 图书归还信息查询 实体类
 */
public class BackListQuery extends Query {
    //借阅编号
    private String borrowId;
    //图书ISBN号
    private String ISBN;
    //证件号
    private String paperNO;

    public BackListQuery() {
    }

    public String getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(String borrowId) {
        this.borrowId = borrowId;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getPaperNO() {
        return paperNO;
    }

    public void setPaperNO(String paperNO) {
        this.paperNO = paperNO;
    }
}
