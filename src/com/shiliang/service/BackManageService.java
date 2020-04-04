package com.shiliang.service;

import com.shiliang.dao.*;
import com.shiliang.domain.PageBean;
import com.shiliang.domain.data.*;
import com.shiliang.domain.query.BackListQuery;
import com.shiliang.domain.vo.AdminDetailsVo;
import com.shiliang.domain.vo.BackDetailsVO;
import com.shiliang.domain.vo.BackListVO;

import java.util.Date;

/**
 * @author SL
 * @create 2020-03-27 23:40
 * @description 图书归还业务逻辑层
 */
public class BackManageService {
    BackInfoDao backInfoDao=new BackInfoDao();
    BorrowManageDao borrowManageDao=new BorrowManageDao();
    BookManageDao bookManageDao=new BookManageDao();
    BookTypeManageDao bookTypeManageDao=new BookTypeManageDao();
    ReaderManageDao readerManageDao=new ReaderManageDao();
    ReaderTypeManageDao readerTypeManageDao=new ReaderTypeManageDao();
    AdminManageDao adminManageDao=new AdminManageDao();

    public PageBean<BackListVO> pageSearch(BackListQuery query, int pageSize) {
        return backInfoDao.pageSearch(query, pageSize);
    }

    /**
     * 通过借阅编号获取图书归还详细信息
     */
    public BackDetailsVO getBackInfoById(BackinfoDo backinfoDo) {
//        BackDetailsVO vo=new BackDetailsVO();
//
//        //获取图书的借阅信息
//        BorrowinfoDo borrowInfo = borrowManageDao.getBorrowInfoById(new BorrowinfoDo(id));
//
//        //设置归还信息的借阅编号和逾期天数和归还状态
//        vo.setBorrowId(borrowInfo.getId());
//        vo.setOverDay(borrowInfo.getOverday());
//        vo.setState(borrowInfo.getState());
//
//        //通过图书借阅信息获取图书相关信息
//        BookDo bookinfo = bookManageDao.getBookInfoById(new BookDo(borrowInfo.getFkBook()));
//        vo.setISBN(bookinfo.getIsbn());
//        vo.setBookName(bookinfo.getName());
//        //获取图书的类型
//        BooktypeDo bookType = bookTypeManageDao.getBookTypeById(new BooktypeDo(bookinfo.getFk_booktype()));
//        vo.setBookType(bookType.getName());
//
//        //通过图书借阅信息获取读者的信息
//        ReaderDo reader = readerManageDao.getReaderById(new ReaderDo(borrowInfo.getFkReader()));
//        vo.setPaperNO(reader.getPaperNo());
//        vo.setReaderName(reader.getName());
//
//        //获取读者的类型
//        ReadertypeDo readertype = readerTypeManageDao.getReaderTypeByID(new ReadertypeDo(reader.getFkReadertype()));
//        vo.setReaderType(readertype.getName());
//
////        if (bookinfo.getFk_admin() != null) {
////            AdminDo admin = adminManageDao.getAdminInfoById(new AdminDo(bookinfo.getFk_admin()));
////            vo.setAdmin(admin.getName());
////        }

        return backInfoDao.getBackInfoById(backinfoDo);
    }

    /**
     * 归还图书业务逻辑
     * @param borrowId
     * @return
     */
    public int backBook(int borrowId, AdminDetailsVo admin) {
        BorrowinfoDo borrowInfo = borrowManageDao.getBorrowInfoById(new BorrowinfoDo(borrowId));
        if (borrowInfo != null) {
            Integer state = borrowInfo.getState();
            if (state == 2 || state == 5) {
                return -1;
            }
            //获取书籍信息，使书籍在馆数量增加
            BookDo book = bookManageDao.getBookInfoById(new BookDo(borrowInfo.getFkBook()));
            book.setCurrentNum(book.getCurrentNum() + 1);
            int result = bookManageDao.updateBookInfo(book);

            if (result > 0) {
                //设置图书的归还信息
               BackinfoDo backinfoDo = new BackinfoDo(borrowId);
                backinfoDo.setBackDate(new Date(System.currentTimeMillis()));
                backinfoDo.setFkAdmin(admin.getId());
                int count = backInfoDao.updateBackInfo(backinfoDo);

                if (count > 0) {
                    //设置图书的借阅状态
                    //如果图书的借阅状态不为续借，那么设置为归还
                    if (borrowInfo.getState() == 0) {
                        borrowInfo.setState(2);
                        int i = borrowManageDao.updateBorrowState(borrowInfo);
                        if (i > 0) {
                            return 1;
                        }
                    }
                    //如果图书的借阅状态为逾期未归还，那么设置为归还
                    if (borrowInfo.getState() == 1) {
                        borrowInfo.setState(2);
                        int i = borrowManageDao.updateBorrowState(borrowInfo);
                        if (i > 0) {
                            return 2;
                        }
                    }

                    //如果图书的借阅状态为续借未归还，那么设置为续借归还
                    if (borrowInfo.getState() == 3) {
                        borrowInfo.setState(5);
                        int i = borrowManageDao.updateBorrowState(borrowInfo);
                        if (i > 0) {
                            return 1;
                        }
                    }

                    //如果图书为续借逾期未归还，那么设置为归还 并提醒交罚款
                    if (borrowInfo.getState() == 4) {
                        borrowInfo.setState(5);
                        int i = borrowManageDao.updateBorrowState(borrowInfo);
                        if (i > 0) {
                            ////归还成功，请缴纳逾期罚金
                            return 2;
                        }
                    }



                }

            }

        }

        return 0;
    }
}
