package com.shiliang.service;

import com.shiliang.dao.*;
import com.shiliang.domain.PageBean;
import com.shiliang.domain.data.*;
import com.shiliang.domain.vo.AdminDetailsVo;
import com.shiliang.domain.vo.BorrowDetailsVO;
import com.shiliang.domain.vo.BorrowInfoVO;
import com.shiliang.domain.vo.ReaderDetailsVo;
import com.shiliang.utils.JDBCUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.BeanUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author SL
 * @create 2020-03-26 13:49
 * @description 图书借阅管理服务
 */
public class BorrowManageService {
    BorrowManageDao borrowManageDao=new BorrowManageDao();
    BookManageDao bookManageDao=new BookManageDao();
    BookTypeManageDao bookTypeManageDao=new BookTypeManageDao();
    ReaderManageDao readerManageDao=new ReaderManageDao();
    ReaderTypeManageDao readerTypeManageDao=new ReaderTypeManageDao();
    OverDueInfoDao overDueInfoDao=new OverDueInfoDao();
    BackInfoDao backInfoDao=new BackInfoDao();

    /**
     * 分页查询图书借阅信息
     * @param currentPage
     * @param pageSize
     * @return
     */
    public PageBean<BorrowInfoVO> pageSearch(int currentPage, int pageSize) {
        return borrowManageDao.pageSearch(currentPage, pageSize);
    }

    /**
     * 通过id来获取图书的详细借阅信息
     * @param borrowinfoDo
     * @return
     */
    public BorrowDetailsVO getBorrowInfoById(BorrowinfoDo borrowinfoDo, AdminDetailsVo adminDetailsVo) {
        BorrowinfoDo borrowinfo=borrowManageDao.getBorrowInfoById(borrowinfoDo);
        BorrowDetailsVO bvo = new BorrowDetailsVO();
        BeanUtils.copyProperties(borrowinfo, bvo);

        BookDo book = bookManageDao.getBookInfoById(new BookDo(borrowinfo.getFkBook()));
        bvo.setBookName(book.getName());
        bvo.setISBN(book.getIsbn());
        bvo.setBorrowId(borrowinfo.getId());

        BooktypeDo bookType = bookTypeManageDao.getBookTypeById(new BooktypeDo(book.getFk_booktype()));
        if (bookType != null) {
            bvo.setBookType(bookType.getName());
        }

        ReaderDo  readerInfo = readerManageDao.getReaderById(new ReaderDo(borrowinfo.getFkReader()));
        bvo.setReaderName(readerInfo.getName());
        bvo.setPaperNO(readerInfo.getPaperNo());

        ReadertypeDo readerTypeByID = readerTypeManageDao.getReaderTypeByID(new ReadertypeDo(readerInfo.getFkReadertype()));
        bvo.setReaderType(readerTypeByID.getName());
        bvo.setAdminName(adminDetailsVo.getName());

        //TODO 逾期天数有待处理
        bvo.setOverday(0);


//        System.out.println(bvo);
        return bvo;
    }

    /**
     * 添加图书借阅信息
     *        详细逻辑：
     *        1.根据页面传来的数据，查询借阅的读者信息
     *        2.判断读者证件号、以及密码是否和页面传过来的数据匹配
     *                 匹配：继续执行
     *                 不匹配：提示【密码错误】
     *        3.查询图书的ISBN号是否存在
     *                 存在：继续执行
     *                 不存在：提示【图书ISBN错误】
     *        4获取该读者的借阅信息
     *          查询该读者类型的读者最大借阅数量，匹配借阅的数量是否小于最大借阅量
     *             小于：可以继续借阅
     *             大于或等于：提示【不可以借阅，直接返回借阅量已超i过】
     *        5.查看读者的罚款信息，是否有未缴纳的罚款
     *            有：返回有尚未缴纳的罚金，缴纳罚金方可继续借书
     *            没有：继续执行
     *        6.查询借阅的书籍，查看该书籍的在馆数量是否大于1
     *            大于1：继续执行
     *            小于或等于1：提示该图书未馆内最后一本，无法借阅
     *        7.处理借阅信息
     *            得到该读者的最大借阅天数和每日罚金
     *            获得当前时间，根据最大借阅天数，计算出截至日期
     *            设置每日的罚金jine和截至日期
     *            设置借阅状态和逾期天数
     *            获得该读者的信息，为借阅信息设置读者信息
     *            获得图书信息，为借阅信息设置图书信息
     *            获得管理员信息，为借阅信息设置操作的管理员信息
     *        8.存储借阅信息
     *        9.借阅的书籍的在馆数量需要减1
     *        10.设置归还信息
     *
     */
    public JSONObject addBorrow(ReaderDo readerDo, String isbn, AdminDetailsVo admin) {
        JSONObject jsonObject = new JSONObject();
//        Connection connection = JDBCUtil.getConnection();
        //根据页面传来的数据，查询借阅的读者信息
        try {
            ReaderDo reader = readerManageDao.getReaderByPaperNo(readerDo);
            if (reader == null) {
                jsonObject.put("code", -1);
                jsonObject.put("msg", "读者证件号不存在");
                return jsonObject;
            }
            //判断读者证件号、以及密码是否和页面传过来的数据匹配
            if ( !readerDo.getPassword() .equals(reader.getPassword()) ) {
                jsonObject.put("code", -2);
                jsonObject.put("msg", "读者密码输入错误");
                return jsonObject;
            }

            //查询图书的ISBN号是否存在
            BookDo bookByISBN = bookManageDao.getBookByISBN(new BookDo(isbn));
            if (bookByISBN == null) {
                jsonObject.put("code", -3);
                jsonObject.put("msg", "图书的ISBN号不存在");
                return jsonObject;
            }

            //获取该读者的借阅信息
            ReadertypeDo readerType = readerTypeManageDao.getReaderTypeByID(new ReadertypeDo(reader.getFkReadertype()));
            //查询该读者类型的读者最大借阅数量
            Integer maxNum = readerType.getMaxNum();
            //匹配借阅的数量是否小于最大借阅量
            Integer noBackNum = borrowManageDao.getNoBackNum();
            if (noBackNum!=null && noBackNum >= maxNum) {
                jsonObject.put("code", -4);
                jsonObject.put("msg", "借阅数量已达上限");
                return jsonObject;
            }

            //查看读者的罚款信息，是否有未缴纳的罚款
            List<OverdueinfoDo> overDueInfoByReaderId = overDueInfoDao.getOverDueInfoByReaderId(new ReaderDo(reader.getId()));
            for (OverdueinfoDo overdueinfoDo : overDueInfoByReaderId) {
                if (overdueinfoDo.getIsPay() == 0) {
                    jsonObject.put("code", -5);
                    jsonObject.put("msg", "请先缴纳未缴纳的罚金");
                    return jsonObject;
                }
            }

            //查询借阅的书籍，查看该书籍的在馆数量是否大于1
            if (bookByISBN.getCurrentNum() <= 1) {
                jsonObject.put("code", -6);
                jsonObject.put("msg", "该图书是馆内最后一本，无法借阅");
                return jsonObject;
            }

            //读者的最大借阅天数
            Integer bday = readerType.getBday();
            //每日罚金
            double penalty = readerType.getPenalty();


            java.sql.Date borrowDate = new java.sql.Date(System.currentTimeMillis());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(borrowDate);
            calendar.add(Calendar.DAY_OF_MONTH, +bday);
            Date time = calendar.getTime();
            java.sql.Date endDate = new java.sql.Date(time.getTime());

            BorrowinfoDo borrowinfoDo = new BorrowinfoDo();
            //设置借阅信息的每日罚金
            borrowinfoDo.setPenalty(penalty);
            //设置图书的借阅日期
            borrowinfoDo.setBorrowDate(borrowDate);
            //设置图书的归还日期
            borrowinfoDo.setEndDate(endDate);
            //设置图书的归还状态
            borrowinfoDo.setState(0);
            //设置图书借阅的管理员
            borrowinfoDo.setFkAdmin(admin.getId());
            //设置图书借阅的读者
            borrowinfoDo.setFkReader(reader.getId());
            //设置图书借阅的图书id
            borrowinfoDo.setFkBook(bookByISBN.getId());
            //设置逾期的天数
            borrowinfoDo.setOverday(0);

////            TODO 开启事务
//            JDBCUtil.getConnection().setAutoCommit(false);

            //将借阅图书的信息导入数据库并获取到插入后的主键id
            int id = borrowManageDao.addBorrowInfo(borrowinfoDo);
            if (id != 0) {



//                借阅的书籍的在馆数量需要减1
                bookByISBN.setCurrentNum(bookByISBN.getCurrentNum() - 1);
                bookManageDao.updateBookInfo(bookByISBN);


                //相应的插入一条图书归还数据
                BackinfoDo backinfoDo=new BackinfoDo(id);
                int count = backInfoDao.addBackInfo(backinfoDo);

                //提交事务
//               JDBCUtil.getConnection().commit();
                if (count > 0) {
                    jsonObject.put("code", 1);
                    jsonObject.put("msg", "借阅成功");
                    return jsonObject;
                }
            }


//        System.out.println(borrowDate + " " + endDate);
        } catch (Exception e) {
            e.printStackTrace();
//            try {
//                JDBCUtil.getConnection().rollback();
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
        }

        jsonObject.put("code", 2);
        jsonObject.put("msg", "借阅失败");
        return jsonObject;
    }

    /**
     * 检查图书信息是否存在逾期图书信息
     */
    public boolean checkBorrowInfo() {
        //查询所有未归还得记录
        List<BorrowinfoDo> borrowinfoDos = borrowManageDao.getBorrowInfoByNoBackState();
        if (borrowinfoDos != null) {
            for (BorrowinfoDo borrowinfoDo : borrowinfoDos) {
                //获取当前得时间戳
                long currentTime = System.currentTimeMillis();
                //获取图书的截至时间
                long endTime = borrowinfoDo.getEndDate().getTime();
                if (currentTime > endTime) {
                    long days = (currentTime - endTime) / (1000 * 60 * 60 * 24);
                    borrowinfoDo.setOverday((int) days);
                    //获取图书得借阅状态
                    Integer state = borrowinfoDo.getState();
                    //判断图书借阅状态
                    if (state == 0) {
                        borrowinfoDo.setState(1);
                    }
                    if (state == 3) {
                        borrowinfoDo.setState(4);
                    }
                    //更新图书借阅信息
                    borrowManageDao.updateBorrowStateAndOverday(borrowinfoDo);


                    //保存逾期信息
                    OverdueinfoDo overdueinfoDo = new OverdueinfoDo();
                    overdueinfoDo.setId(borrowinfoDo.getId());
                    //设置罚款金额
                    double forfeit = borrowinfoDo.getPenalty() * days;
                    overdueinfoDo.setForfeit(forfeit);

                    //将逾期信息插入到数据库
                    //首先判断数据库中是否已存在该逾期信息
                    OverdueinfoDo overDueInfoById = overDueInfoDao.getOverDueInfoById(overdueinfoDo);
                    int count = 0;
                    if (overDueInfoById == null) {
                        System.out.println("先前没有记录");
                         count = overDueInfoDao.addOverDueInfo(overdueinfoDo);
                    } else {
                        System.out.println("先前有记录");
                        count = overDueInfoDao.updateOverDueForfeit(overdueinfoDo);
                    }
                    System.out.println("逾期了:" + borrowinfoDo);
                    System.out.println("逾期天数:" + days);

                    if (count > 0) {
                        return true;
                    } else {
                        return false;
                    }
                }

            }
        }

        return false;
    }
}
