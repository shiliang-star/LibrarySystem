package com.shiliang.domain.vo;

import java.util.Date;

/**
 * @author SL
 * @create 2020-03-18 18:43
 * @description 封装读者的详细数据
 */
public class ReaderManageListVo {
    private String id;//读者Id
    private String paperNo;//读者证件号码（用于登陆）
    private String readerName;//读者用户名
    private String readerTypeName;//读者类型
    private String phone;//读者联系方式
    private Date createTime;//创建时间

    public ReaderManageListVo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getReaderTypeName() {
        return readerTypeName;
    }

    public void setReaderTypeName(String readerTypeName) {
        this.readerTypeName = readerTypeName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
