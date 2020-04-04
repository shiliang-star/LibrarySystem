package com.shiliang.domain.query;

/**
 * @author SL
 * @create 2020-03-18 18:24
 * @description 读者的条件查询数据封装
 */
public class ReaderQuery {

    private String currentPage; //当前页
    private String paperNo;//读者证件号码（用于登陆）
    private String readerName;//读者用户名
    private String readerTypeId;//读者类型名称


    public ReaderQuery() {
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public String getPaperNo() {
        return paperNo;
    }

    public void setPaperNo(String paperNo) {
        this.paperNo = paperNo;
    }

    public String getReaderName() {
        return readerName;
    }

    public void setReaderName(String readerName) {
        this.readerName = readerName;
    }

    public String getReaderTypeId() {
        return readerTypeId;
    }

    public void setReaderTypeId(String readerTypeId) {
        this.readerTypeId = readerTypeId;
    }
}
