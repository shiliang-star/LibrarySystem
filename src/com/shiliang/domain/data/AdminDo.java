package com.shiliang.domain.data;


/**
 * @author SL
 */
public class AdminDo {

  private Integer id;
  private String username;
  private String name;
  private String phone;
  private Integer state;
  private String password;

  public AdminDo() {
  }

  public AdminDo(Integer id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getState() {
    return state;
  }

  public void setState(Integer state) {
    this.state = state;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }


  @Override
  public String toString() {
    return "Admin{" +
            "id=" + id +
            ", username='" + username + '\'' +
            ", name='" + name + '\'' +
            ", phone='" + phone + '\'' +
            ", state=" + state +
            ", password='" + password + '\'' +
            '}';
  }
}
