package com.shiliang.domain.vo;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author SL
 * @create 2020-03-12 23:19
 * @description 图书管理页面VO
 */
public class BookManageListVo {

    private Integer id;
    private String name;
    private String isbn;
    private String author;
    private Integer num;
    private Integer currentNum;
    private String press;
    private String description;
    private double price;
    private Date putdate;

    private String booktype;

    public BookManageListVo() {
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Date getPutdate() {
        return putdate;
    }

    public void setPutdate(Date putdate) {
        this.putdate = putdate;
    }

    public String getBooktype() {
        return booktype;
    }

    public void setBooktype(String booktype) {
        this.booktype = booktype;
    }
}
