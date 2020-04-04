package com.shiliang.domain.vo;

/**
 * @author SL
 * @create 2020-03-28 13:39
 * @description 图书归还的详细信息VO
 */
public class BackDetailsVO {
    //借阅编号
    private Integer borrowId;
    //图书ISBN号
    private String ISBN;
    //图书名称
    private String bookName;
    //图书类型
    private String bookType;
    //读者证件号
    private String paperNO;
    //读者名称
    private String readerName;
    //读者类型
    private String readerType;
    //逾期天数
    private Integer overDay;
    //操作的管理员
    private String admin;
    //0 代表未归还
    //1 代表逾期未归还
    //2 代表归还
    //3 代表续借未归还
    //4 代表续借逾期未归还
    //5 代表续借归还
    private int state;

    public BackDetailsVO() {
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

    public String getBookType() {
        return bookType;
    }

    public void setBookType(String bookType) {
        this.bookType = bookType;
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

    public String getReaderType() {
        return readerType;
    }

    public void setReaderType(String readerType) {
        this.readerType = readerType;
    }

    public Integer getOverDay() {
        return overDay;
    }

    public void setOverDay(Integer overDay) {
        this.overDay = overDay;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
