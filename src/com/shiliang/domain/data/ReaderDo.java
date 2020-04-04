package com.shiliang.domain.data;


import java.sql.Date;

public class ReaderDo {

  private Integer id; //读者Id
  private String password;//读者密码
  private String name;//读者用户名
  private String paperNo;//读者证件号码（用于登陆）
  private String phone;//读者联系方式
  private String email;//读者邮箱
  private java.sql.Date createTime;//创建时间
  private Integer fkReadertype;//读者类型
  private Integer fkAdmin;//操作的管理员

  public ReaderDo() {
  }

  public ReaderDo(Integer id) {
    this.id = id;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getPaperNo() {
    return paperNo;
  }

  public void setPaperNo(String paperNo) {
    this.paperNo = paperNo;
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

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Integer getFkReadertype() {
    return fkReadertype;
  }

  public void setFkReadertype(Integer fkReadertype) {
    this.fkReadertype = fkReadertype;
  }


  public Integer getFkAdmin() {
    return fkAdmin;
  }

  public void setFkAdmin(Integer fkAdmin) {
    this.fkAdmin = fkAdmin;
  }

  @Override
  public String toString() {
    return "ReaderDo{" +
            "id='" + id + '\'' +
            ", password='" + password + '\'' +
            ", name='" + name + '\'' +
            ", paperNo='" + paperNo + '\'' +
            ", phone='" + phone + '\'' +
            ", email='" + email + '\'' +
            ", createTime=" + createTime +
            ", fkReadertype=" + fkReadertype +
            ", fkAdmin=" + fkAdmin +
            '}';
  }
}
