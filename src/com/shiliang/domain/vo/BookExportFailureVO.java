package com.shiliang.domain.vo;

/**
 * 记录导入错误的信息
 * 所有的字段都是String类型
 */
public class BookExportFailureVO {

    private String ISBN;// ISBN 国际标准书号
    private String bookName; // 图书名称
    private String author; // 作者名称
    private String press; // 出版社
    private String num; // 总数量
    private String price; // 价格
    private String description; // 简介
    private String booktype;//图书类型

    private String errorMsg;//导入错误的信息


    public BookExportFailureVO(String ISBN, String bookName, String author, String press, String num, String price, String description, String booktype, String errorMsg) {
        this.ISBN = ISBN;
        this.bookName = bookName;
        this.author = author;
        this.press = press;
        this.num = num;
        this.price = price;
        this.description = description;
        this.booktype = booktype;
        this.errorMsg = errorMsg;
    }
    //方便创建对象
    public static BookExportFailureVO createBookExportFailureVO(String ISBN,
                                                                String bookName, String author,
                                                                String press, String num,
                                                                String price, String description,
                                                                String booktype, String errorMsg){
        return new BookExportFailureVO( ISBN,  bookName,  author,  press,  num,  price,  description,  booktype,  errorMsg);
    }




    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
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

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBooktype() {
        return booktype;
    }

    public void setBooktype(String booktype) {
        this.booktype = booktype;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
