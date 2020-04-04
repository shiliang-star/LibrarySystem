package com.shiliang.domain.vo;

/**
 * @author SL
 * @create 2020-03-26 19:04
 * @description 借阅详细信息VO
 */
public class BorrowDetailsVO {
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
    private String adminName;
    //归还状态
    // 0 代表未归还
    //1 代表逾期未还
    //2 代表归还
    //3 代表续借未归还
    //4 代表续借逾期未归还
    //5 代表续借归还
    private Integer state;

    public BorrowDetailsVO(Integer borrowId) {
        this.borrowId = borrowId;
    }

    public BorrowDetailsVO() {
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

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
