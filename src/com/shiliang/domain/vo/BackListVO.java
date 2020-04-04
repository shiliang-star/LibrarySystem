package com.shiliang.domain.vo;

import java.util.Date;

/**
 * @author SL
 * @create 2020-03-27 23:19
 * @description 图书归还信息列表VO
 */
public class BackListVO {
    //借阅编号
    private Integer borrowId;
    //图书ISBN号
    private String ISBN;
    //图书名称
    private String bookName;
    //读者证件号
    private String paperNO;
    //读者名称
    private String readerName;
    //归还日期
    private Date backDate;
    //截至还书日期
    private Date endDate;

    public BackListVO() {
    }

    public Integer getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(Integer borrowId) {
        this.borrowId = borrowId;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getPaperNO() {
        return paperNO;
    }

    public void setPaperNO(String paperNO) {
        this.paperNO = paperNO;
    }

    public String getReaderName() {
        return readerName;
    }

    public void setReaderName(String readerName) {
        this.readerName = readerName;
    }

    public Date getBackDate() {
        return backDate;
    }

    public void setBackDate(Date backDate) {
        this.backDate = backDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
