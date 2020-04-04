package com.shiliang.domain.data;


import java.sql.Date;

public class BookDo {

  private Integer id;
  private String name;
  private String isbn;
  private String author;
  private Integer num;
  private Integer currentNum;
  private String press;
  private String description;
  private double price;
  private java.sql.Date putdate;
  private Integer fk_booktype;
  private Integer fk_admin;


  public BookDo() {
  }

  public BookDo(Integer id) {
    this.id = id;
  }

  public BookDo(String isbn) {
    this.isbn = isbn;
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

  public String getIsbn() {
    return isbn;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public Integer getNum() {
    return num;
  }

  public void setNum(Integer num) {
    this.num = num;
  }

  public Integer getCurrentNum() {
    return currentNum;
  }

  public void setCurrentNum(Integer currentNum) {
    this.currentNum = currentNum;
  }

  public String getPress() {
    return press;
  }

  public void setPress(String press) {
    this.press = press;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public Date getPutdate() {
    return putdate;
  }

  public void setPutdate(Date putdate) {
    this.putdate = putdate;
  }

  public Integer getFk_booktype() {
    return fk_booktype;
  }

  public void setFk_booktype(Integer fk_booktype) {
    this.fk_booktype = fk_booktype;
  }

  public Integer getFk_admin() {
    return fk_admin;
  }

  public void setFk_admin(Integer fk_admin) {
    this.fk_admin = fk_admin;
  }
}
