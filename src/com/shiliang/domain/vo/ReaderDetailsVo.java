package com.shiliang.domain.vo;

/**
 * @author SL
 * @create 2020-03-19 15:23
 * @description 用于查看读者个人信息的数据封装
 */
public class ReaderDetailsVo {
    private Integer id;//读者id
    private String paperNO;//读者证件号码（用于登陆）
    private String readerName;//读者名称
    private String readerType;//读者类型
    private String phone;//读者联系方式
    private String email;//读者邮箱
    private String adminName;//操作管理员

    public ReaderDetailsVo() {
    }

    public ReaderDetailsVo(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }
}
