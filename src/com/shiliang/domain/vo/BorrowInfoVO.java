package com.shiliang.domain.vo;

import java.util.Date;

/**
 * @author SL
 * @create 2020-03-26 13:38
 * @description 图书借阅信息VO
 */
public class BorrowInfoVO {
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
    //借阅日期
    private Date borrowDate;
    //截至还书日期
    private Date endDate;

    public BorrowInfoVO() {
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

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "BorrowInfoVO{" +
                "borrowId=" + borrowId +
                ", ISBN='" + ISBN + '\'' +
                ", bookName='" + bookName + '\'' +
                ", paperNO='" + paperNO + '\'' +
                ", readerName='" + readerName + '\'' +
                ", borrowDate=" + borrowDate +
                ", endDate=" + endDate +
                '}';
    }
}
