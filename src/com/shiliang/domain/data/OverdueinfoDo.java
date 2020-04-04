package com.shiliang.domain.data;

/**
 * @author SL
 * 图书逾期信息Do
 */
public class OverdueinfoDo {
  //借阅编号
  private Integer id;
  //罚金金额
  private double forfeit;
  //是否已经支付罚金
  private Integer isPay;
  //操作的管理员
  private Integer fkAdmin;

  public OverdueinfoDo() {
  }

  public Integer getId() {
    return id;
  }

  public OverdueinfoDo(Integer id) {
    this.id = id;
  }

  public void setId(Integer id) {
    this.id = id;
  }


  public double getForfeit() {
    return forfeit;
  }

  public void setForfeit(double forfeit) {
    this.forfeit = forfeit;
  }


  public Integer getIsPay() {
    return isPay;
  }

  public void setIsPay(Integer isPay) {
    this.isPay = isPay;
  }


  public Integer getFkAdmin() {
    return fkAdmin;
  }

  public void setFkAdmin(Integer fkAdmin) {
    this.fkAdmin = fkAdmin;
  }

}
