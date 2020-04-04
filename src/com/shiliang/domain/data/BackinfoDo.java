package com.shiliang.domain.data;


import java.util.Date;

/**
 * @author SL
 * 归还信息DO
 */
public class BackinfoDo {
  //主键id
  private Integer id;
  //图书归还时间
  private Date backDate;
  //外键，对应操作的管理员
  private Integer fkAdmin;

  public BackinfoDo() {
  }

  public BackinfoDo(Integer id) {
    this.id = id;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }


  public Date getBackDate() {
    return backDate;
  }

  public void setBackDate(Date backDate) {
    this.backDate = backDate;
  }


  public Integer getFkAdmin() {
    return fkAdmin;
  }

  public void setFkAdmin(Integer fkAdmin) {
    this.fkAdmin = fkAdmin;
  }

}
