package com.shiliang.domain.vo;

/**
 * @author SL
 * @create 2020-03-29 14:29
 * @description 图书详细逾期信息
 */
public class OverDueDetailsVO {
    //借阅编号
    private Integer borrowId;
    //借阅书籍ISBN号
    private String ISBN;
    //借阅书籍名称
    private String bookName;
    //借阅书籍类型
    private String bookType;
    //读者证件号
    private String paperNO;
    //读者名称
    private String readerName;
    //读者类型
    private String readerType;
    //逾期天数
    private Integer overday;
    //操作管理员
    private String admin;
    //是否已经支付罚金（0未支付 1已支付）
    private Integer isPay;

    public OverDueDetailsVO() {
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

    public Integer getOverday() {
        return overday;
    }

    public void setOverday(Integer overday) {
        this.overday = overday;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public Integer getIsPay() {
        return isPay;
    }

    public void setIsPay(Integer isPay) {
        this.isPay = isPay;
    }
}
