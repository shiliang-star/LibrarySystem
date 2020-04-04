package com.shiliang.domain.data;


import java.sql.Date;

/**
 * @author SL
 * 图书借阅信息DO
 */
public class BorrowinfoDo {
  //借阅编号
  private Integer id;
  //借阅日期
  private java.sql.Date borrowDate;
  //截止归还日期
  private java.sql.Date endDate;
  //逾期天数
  private Integer overday;
  //状态 (未归还=0,逾期未归还=1,归还=2,续借未归还=3,续借逾期未归还=4,续借归还=5)
  private Integer state;
  //借阅读者ID，对应读者表
  private Integer fkReader;
  //借阅书籍ID
  private Integer fkBook;
  //每日罚金
  private double penalty;
  //操作的管理员ID
  private Integer fkAdmin;

  public BorrowinfoDo() {
  }

  public BorrowinfoDo(Integer id) {
    this.id = id;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
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

  public Integer getOverday() {
    return overday;
  }

  public void setOverday(Integer overday) {
    this.overday = overday;
  }


  public Integer getState() {
    return state;
  }

  public void setState(Integer state) {
    this.state = state;
  }


  public Integer getFkReader() {
    return fkReader;
  }

  public void setFkReader(Integer fkReader) {
    this.fkReader = fkReader;
  }

  public Integer getFkBook() {
    return fkBook;
  }

  public void setFkBook(Integer fkBook) {
    this.fkBook = fkBook;
  }


  public double getPenalty() {
    return penalty;
  }

  public void setPenalty(double penalty) {
    this.penalty = penalty;
  }


  public Integer getFkAdmin() {
    return fkAdmin;
  }

  public void setFkAdmin(Integer fkAdmin) {
    this.fkAdmin = fkAdmin;
  }

  @Override
  public String toString() {
    return "BorrowinfoDo{" +
            "id=" + id +
            ", overday=" + overday +
            '}';
  }
}
