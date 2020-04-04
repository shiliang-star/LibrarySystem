package com.shiliang.domain.vo;

/**
 * @author SL
 * @create 2020-03-28 22:56
 * @description 逾期处理列表VO
 */
public class OverDueManageListVO {
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
    //逾期天数
    private Integer overDay;
    //需缴纳罚金
    private Double forfeit;

    public OverDueManageListVO() {
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

    public Integer getOverDay() {
        return overDay;
    }

    public void setOverDay(Integer overDay) {
        this.overDay = overDay;
    }

    public Double getForfeit() {
        return forfeit;
    }

    public void setForfeit(Double forfeit) {
        this.forfeit = forfeit;
    }
}
