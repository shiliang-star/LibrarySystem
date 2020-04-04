package com.shiliang.domain.data;


public class BooktypeDo {

  private Integer id;
  private String name;

  public BooktypeDo() {
  }

  public BooktypeDo(Integer id) {
    this.id = id;
  }

  public BooktypeDo(String name) {
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

}
