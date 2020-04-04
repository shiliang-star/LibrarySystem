package com.shiliang.domain.query;

/**
 * @author SL
 * @create 2020-03-12 21:34
 * @description 图书管理查询
 */
public class BookManageQuery {
    private String currentPage; //当前页
    private String ISBN;  //图书的ISBN号
    private String bookTypeId;  //图书分类名称
    private String  bookName;   //图书名称
    private String author;  //作者名称
    private String press;   //出版社名称

    public BookManageQuery() {
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getBookTypeId() {
        return bookTypeId;
    }

    public void setBookTypeId(String bookTypeId) {
        this.bookTypeId = bookTypeId;
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
}
