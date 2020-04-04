package com.shiliang.domain.data;


/**
 * @author 19655
 *         值为 1 表示具有该权限，0 为没有改权限
 */
public class AuthorizationDo {
  //管理员Id
  private Integer id;
  //图书设置权限
  private Integer bookSet;
  //读者设置权限
  private Integer readerSet;
  //借阅设置权限
  private Integer borrowSet;
  //图书分类权限设置
  private Integer typeSet;
  //归还设置权限
  private Integer backSet;
  //逾期设置权限
  private Integer forfeitSet;
  //系统设置权限
  private Integer sysSet;
  //超级管理权限【1超级管理员】【0普通管理员】
  private Integer superSet;

  public AuthorizationDo() {
  }

  public AuthorizationDo(Integer id, Integer bookSet, Integer readerSet, Integer borrowSet, Integer typeSet, Integer backSet, Integer forfeitSet, Integer sysSet) {
    this.id = id;
    this.bookSet = bookSet;
    this.readerSet = readerSet;
    this.borrowSet = borrowSet;
    this.typeSet = typeSet;
    this.backSet = backSet;
    this.forfeitSet = forfeitSet;
    this.sysSet = sysSet;
  }

  public AuthorizationDo(Integer id) {
    this.id = id;
  }

  public Integer getId() {
    return id;
  }


  public void setId(Integer id) {
    this.id = id;
  }


  public Integer getBookSet() {
    return bookSet;
  }

  public void setBookSet(Integer bookSet) {
    this.bookSet = bookSet;
  }


  public Integer getReaderSet() {
    return readerSet;
  }

  public void setReaderSet(Integer readerSet) {
    this.readerSet = readerSet;
  }


  public Integer getBorrowSet() {
    return borrowSet;
  }

  public void setBorrowSet(Integer borrowSet) {
    this.borrowSet = borrowSet;
  }


  public Integer getTypeSet() {
    return typeSet;
  }

  public void setTypeSet(Integer typeSet) {
    this.typeSet = typeSet;
  }


  public Integer getBackSet() {
    return backSet;
  }

  public void setBackSet(Integer backSet) {
    this.backSet = backSet;
  }


  public Integer getForfeitSet() {
    return forfeitSet;
  }

  public void setForfeitSet(Integer forfeitSet) {
    this.forfeitSet = forfeitSet;
  }


  public Integer getSysSet() {
    return sysSet;
  }

  public void setSysSet(Integer sysSet) {
    this.sysSet = sysSet;
  }


  public Integer getSuperSet() {
    return superSet;
  }

  public void setSuperSet(Integer superSet) {
    this.superSet = superSet;
  }

}
