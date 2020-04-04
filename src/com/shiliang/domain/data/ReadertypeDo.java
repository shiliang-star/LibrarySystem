package com.shiliang.domain.data;


public class ReadertypeDo {
  //ID
  private Integer id;
  //读者类型名字
  private String name;
  //最大借书量
  private Integer maxNum;
  //可借天数
  private Integer bday;
  //每日罚金
  private double penalty;
  //可续借天数
  private Integer renewDays;


  public ReadertypeDo() {
  }

  public ReadertypeDo(Integer id) {
    this.id = id;
  }

  public ReadertypeDo(String name) {
    this.name = name;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public Integer getMaxNum() {
    return maxNum;
  }

  public void setMaxNum(Integer maxNum) {
    this.maxNum = maxNum;
  }


  public Integer getBday() {
    return bday;
  }

  public void setBday(Integer bday) {
    this.bday = bday;
  }


  public double getPenalty() {
    return penalty;
  }

  public void setPenalty(double penalty) {
    this.penalty = penalty;
  }


  public Integer getRenewDays() {
    return renewDays;
  }

  public void setRenewDays(Integer renewDays) {
    this.renewDays = renewDays;
  }

}
