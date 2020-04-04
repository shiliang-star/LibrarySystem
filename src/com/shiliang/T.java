package com.shiliang;

import com.shiliang.dao.*;
import com.shiliang.domain.PageBean;
import com.shiliang.domain.data.AdminDo;
import com.shiliang.domain.data.BookDo;
import com.shiliang.domain.data.BooktypeDo;
import com.shiliang.domain.data.ReaderDo;
import com.shiliang.utils.Md5Utils;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author SL
 * @create 2020-03-08 15:57
 * @description 测试类
 */
public class T {
    public static void main(String[] args) {
        try {
            Class<T> clazz = T.class;
            Method m = clazz.getMethod("test", String.class);
            T t = clazz.newInstance();
            m.invoke(t, "aaa");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    public void test(String s) {

        System.out.println("我被调用了"+"  "+s);
    }


    @Test
    public void t1() {
        AdminDao dao = new AdminDao();
        List<AdminDo> allAdminDo = dao.findAllAdmin();
        System.out.println(allAdminDo);
    }

    @Test
    public void t2() {
        AdminDao dao = new AdminDao();
        AdminDo adminDo = new AdminDo();
        adminDo.setUsername("admin");

        AdminDo admin = dao.findAdmin(adminDo);
        System.out.println(admin);
    }

    @Test
    public void t3() {
        ReaderDo readerDo = new ReaderDo();
        readerDo.setPaperNo("1111");

        ReaderDao dao = new ReaderDao();
        ReaderDo readerByPaperNo = dao.findReaderByPaperNo(readerDo);
        System.out.println(readerByPaperNo);
    }


    @Test
    public void t4() {
        AdminDao adminDao=new AdminDao();

        AdminDo adminDo = new AdminDo();
        adminDo.setId(2);
        adminDo.setName("时涛呀");
        adminDo.setPhone("1111111111111");

        Integer integer = adminDao.updateAdminInfo(adminDo);
        System.out.println(integer);
    }

    @Test
    public void t5() {
        AdminDo adminDo = new AdminDo();
        adminDo.setPassword("123456");
        adminDo.setState(1);
        adminDo.setPhone("145245554");
        adminDo.setName("测试");
        adminDo.setUsername("ceshi");
        AdminManageDao dao=new AdminManageDao();
//        int i = dao.addAdmin(adminDo);
//        System.out.println(i);
    }

    @Test
    public void t6() {
        BookManageDao bookManageDao=new BookManageDao();
        BookDo bookDo = new BookDo();
        bookDo.setCurrentNum(10);
        bookDo.setId(1);
        int count = bookManageDao.updateBookInfo(bookDo);
        System.out.println(count);

    }


}
