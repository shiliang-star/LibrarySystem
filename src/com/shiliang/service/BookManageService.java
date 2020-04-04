package com.shiliang.service;

import com.monitorjbl.xlsx.StreamingReader;
import com.shiliang.dao.AdminDao;
import com.shiliang.dao.BookManageDao;
import com.shiliang.dao.BookTypeManageDao;
import com.shiliang.domain.PageBean;
import com.shiliang.domain.data.AdminDo;
import com.shiliang.domain.data.BookDo;
import com.shiliang.domain.data.BooktypeDo;
import com.shiliang.domain.query.BookManageQuery;
import com.shiliang.domain.vo.AdminDetailsVo;
import com.shiliang.domain.vo.BookDetailsVo;
import com.shiliang.domain.vo.BookExportFailureVO;
import com.shiliang.domain.vo.BookManageListVo;
import com.shiliang.utils.Constant;
import com.shiliang.utils.FileUtil;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author SL
 * @create 2020-03-12 22:38
 * @description 图书管理服务层
 */
public class BookManageService {

    BookManageDao dao = new BookManageDao();
    BookTypeManageDao bookTypeManageDao = new BookTypeManageDao();
    AdminDao adminDao = new AdminDao();

    public PageBean<BookManageListVo> pageSearch(BookManageQuery query, int pageSize) {

        return dao.pageSearch(query, pageSize);
    }


    public List<BooktypeDo> getAllBookTypes() {
        try {
            return dao.getAllBookTypes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public Integer addBook(BookDo bookDo) {
        try {
            return dao.addBook(bookDo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public BookDo getBookByISBN(BookDo bookDo) {
        try {
            return dao.getBookByISBN(bookDo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取图书的详细信息 包括操作的管理员和图书分类(多表查询)
     *
     * @param bookDo
     * @return
     */
    public BookDetailsVo getBookInfoById(BookDo bookDo) {
        try {
            BookDetailsVo vo = new BookDetailsVo();
            //获取图书的详细信息 包括操作的管理员和图书分类
            BookDo book = dao.getBookInfoById(bookDo);

            BeanUtils.copyProperties(vo, book);

            vo.setISBN(book.getIsbn());
            vo.setBookname(book.getName());

            BooktypeDo booktype = bookTypeManageDao.getBookTypeById(new BooktypeDo(book.getFk_booktype()));
            AdminDo admin = adminDao.getAdminByID(new AdminDo(book.getFk_admin()));
            vo.setAdminName(admin.getName());
            vo.setBooktype(booktype.getName());

            return vo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public Integer updateBook(BookDo bookDo) {

        try {
            return dao.updateBook(bookDo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public Integer addBookNum(BookDo bookDo) {
        try {
            //获取到之前的数量
            BookDo book = dao.getBookInfoById(bookDo);

            //拿到添加的数量
            Integer addNum = bookDo.getNum();
            //设置添加图书后的图书数量
            bookDo.setNum(book.getNum() + addNum);
            bookDo.setCurrentNum(book.getCurrentNum() + addNum);

            return dao.addBookNum(bookDo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 导出数据到Excel表格中
     */
    public String exportBook() {
        List<BookDetailsVo> bookDetailsVos = dao.exportBook();
        String fileName = exportExcel(bookDetailsVos);
        return fileName;
    }


    /**
     * 批量添加图书，根据文件名来获取到硬盘上的指定文件，进行参数合法性判断，然后调用dao方法进行添加
     *
     * @param fileName 文件名
     * @param admin    管理员信息
     * @return
     */
    public JSONObject batchAddBook(String fileName, AdminDetailsVo admin) throws FileNotFoundException {
        //表头
        String[] titles = new String[]{"图书ISBN号", "图书类型", "图书名称", "作者名称", "出版社", "价格", "数量", "描述"};

        JSONObject jsonObject = new JSONObject();

        //获取上传的文件
        File file = new File(Constant.uploadPath + fileName);
        FileInputStream in = new FileInputStream(file);
        Workbook wk = StreamingReader.builder()
                //缓存到内存中的行数
                .rowCacheSize(10000)
                //内存大小
                .bufferSize(4096 * 2).open(in);
        Sheet sheet = wk.getSheetAt(0);


        //记录读取行数
        int readcount = 0;

        //获取最后一行的行数
        int latRowNum = sheet.getLastRowNum() + 1;

        //对读取到的行数据进行存储
        List<BookDo> success = new ArrayList<>();

        //用于封装错误信息
        List<BookExportFailureVO> failure = new ArrayList<>();

        for (Row cells : sheet) {
            readcount++;

//            判断表头是否合法
            if (readcount == 2) {
                if (cells.getLastCellNum() != 8) {
                    jsonObject.put("code", -1);
                    jsonObject.put("msg", "您的列数有问题，请下载模板重新上传");
                    return jsonObject;
                }

                for (Cell cell : cells) {
                    //cell.getColumnIndex获取当前列的下标
                    if (!titles[cell.getColumnIndex()].equals(cell.getStringCellValue())) {
                        jsonObject.put("code", -2);
                        jsonObject.put("msg", "表格不正确！,请重新下载模板进行上传");
                        return jsonObject;
                    }
//                    System.out.println(cell.getColumnIndex() + "   " + cell.getStringCellValue());
                }
            }
            if (readcount > 2) {
                // 封装读取到的一行数据
                List<String> rowlist = new ArrayList<>();
                //遍历数据
                for (Cell cell : cells) {
                    rowlist.add(cell.getStringCellValue());
                }
                //进行数据合理化判断
                String ISBN = rowlist.get(0);
                String bookType = rowlist.get(1);
                String bookName = rowlist.get(2);
                String author = rowlist.get(3);
                String press = rowlist.get(4);
                String price = rowlist.get(5);
                String num = rowlist.get(6);
                String description = rowlist.get(7);

                //可能出现空行
                if ("".equals(ISBN) && "".equals(bookType) && "".equals(bookName) && "".equals(author) && "".equals(press) && "".equals(price) && "".equals(num) && "".equals(description)) {
                    continue;//继续执行循环
                }

                //可能某一个单元格没有值
                if (" ".equals(ISBN) || " ".equals(bookType) || " ".equals(price) || " ".equals(bookName)) {
                    //必填字段
                    failure.add(BookExportFailureVO.createBookExportFailureVO(ISBN, bookName, author, press, num, price,
                            description, bookType, "ISBN号、书籍名称、书籍类型、书籍价格是必填的")
                    );
                    continue;
                }

//                如果必填字段不为空，那么将数据封装到 Book（BookDO）类中
                BookDo book=new BookDo();
                book.setIsbn(ISBN);
                book.setName(bookName);
                book.setAuthor(author);
                book.setDescription(description);
                book.setPress(press);

                try {
                    //价格必须是正数，并且大于 0
                    if (Double.parseDouble(price) <= 0) {
                        failure.add(BookExportFailureVO.createBookExportFailureVO(ISBN, bookName, author, press, num, price, description,
                                bookType, "价格必须大于 0")
                        );
                        continue;
                    } else {
                        book.setPrice(Double.parseDouble(price));
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    failure.add(BookExportFailureVO.createBookExportFailureVO(ISBN, bookName, author, press, num, price, description,
                            bookType, "价格必须是数字"));
                    continue;
                }

                try {
                    //图书数量必须是正数，如不填，默认为 1
                    if ("".equals(num)) {
                        book.setNum(1);
                        book.setCurrentNum(book.getNum());
                    } else {
                        if (Integer.parseInt(num) <= 0) {
                            failure.add(BookExportFailureVO.createBookExportFailureVO(ISBN, bookName, author, press, num, price, description,
                                    bookType, "图书数量必须大于 0"));
                            continue;
                        } else {
                            book.setNum(Integer.parseInt(num));
                            book.setCurrentNum(book.getNum());
                        }
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    failure.add(BookExportFailureVO.createBookExportFailureVO(ISBN, bookName, author, press, num, price, description,
                            bookType, "图书数量必须是数字"));
                    continue;
                }


                //判断 Excel 中的 ISBN 是否有重复
                boolean isRepeat = false;
                for (int i = 0; i < success.size(); i++) {
                    if (success.get(i).getIsbn().equals(ISBN)) {
                        failure.add(BookExportFailureVO.createBookExportFailureVO(ISBN, bookName, author, press, num, price, description,
                                bookType, "在添加的数据里发现重复的ISBN号"));
                        isRepeat = true;
                        break;
                    }
                }
                if (isRepeat) {
                    continue;
                }

                //判断图书 ISBN，如果 在数据库中ISBN 已经存在，那么也是不合法的
                BookDo bookByISBN = dao.getBookByISBN(new BookDo(ISBN));
                if (bookByISBN != null) {
                    failure.add(BookExportFailureVO.createBookExportFailureVO(ISBN, bookName, author, press, num, price, description,
                            bookType, "图书库中已存在该ISBN号"));
                    continue;
                }

                //判断图书类型，必须是数据库已有的图书类型
                BooktypeDo booktypeByName = bookTypeManageDao.getBooktypeByName(new BooktypeDo(bookType));
                if (booktypeByName == null) {
                    failure.add(BookExportFailureVO.createBookExportFailureVO(ISBN, bookName, author, press, num, price, description,
                            bookType, "没有此图书类型"));
                    continue;
                }

                book.setFk_booktype(booktypeByName.getId());
                book.setFk_admin(admin.getId());
                java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());
                book.setPutdate(currentDate);

                success.add(book);



            }

        }
        //导入数据
        //何时导入数据
        if ((!success.isEmpty())&&(success.size()==Constant.getimportsize(latRowNum))||(readcount==latRowNum)){
            //定义批处理参数
            List<Object[]> params = new ArrayList<>();
            //遍历success集合
            for (int i = 0; i < success.size(); i++) {
                params.add(new Object[]{success.get(i).getName(), success.get(i).getIsbn(), success.get(i).getAuthor(), success.get(i).getNum(),
                        success.get(i).getCurrentNum(), success.get(i).getPress(), success.get(i).getDescription(), success.get(i).getPrice(),
                        success.get(i).getPutdate(), success.get(i).getFk_booktype(), success.get(i).getFk_admin()});
            }
            System.out.println("正在导出："+success.size());
            int[] ints = dao.batchAddBook(params);
//                    System.out.println(ints.length);
//            success.clear();
        }
//
        if (failure.size() != 0) {
            //把错误信息导出到硬盘中
            String filePath = exportErrorsExcel(failure);
            jsonObject.put("code", 2);
            jsonObject.put("msg", "成功添加" + success.size() + "条，失败" + failure.size() + "条");
            jsonObject.put("filePath", filePath);
            return jsonObject;
        } else {
            jsonObject.put("code", 1);
            jsonObject.put("msg", "成功添加" + success.size() + "条");
            return jsonObject;
        }
//
//        System.out.println(failure);
//        System.out.println(success);

    }

    public String exportExcel(List<BookDetailsVo> booklist) {

        try {

            if (FileUtil.createOrExistsDir(new File(Constant.exportBook))) {

                //表头
                String[] titles = new String[]{"图书ISBN号", "图书类型", "图书名称", "作者名称", "出版社", "价格", "数量", "在馆数量", "上架时间", "操作管理员", "描述"};
                // 创建工作簿
                XSSFWorkbook workbook = new XSSFWorkbook();
                // 新建工作表
                XSSFSheet sheet = workbook.createSheet("数据导出");
                //自动列大小
                sheet.autoSizeColumn(1, true);
                //创建行，0表示第一行
                XSSFRow row = sheet.createRow(0);
                //合并单元格  参数说明：1：开始行 2：结束行  3：开始列 4：结束列
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));
                //获取列【标题列】
                XSSFCell title = row.createCell(0);
                //创建列的样式
                XSSFCellStyle titleStyle = workbook.createCellStyle();
                //垂直和水平居中
                titleStyle.setAlignment(HorizontalAlignment.CENTER);
                titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                //设置背景颜色
                titleStyle.setFillForegroundColor((short) 13);// 设置背景色
                titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

                //设置字体
                XSSFFont font = workbook.createFont();
                font.setFontName("仿宋_GB2312");
                font.setFontHeightInPoints((short) 20);
                titleStyle.setFont(font);


                //设置文本
                title.setCellValue("数据导出");
                //设置样式
                title.setCellStyle(titleStyle);
                //设置行高度
                row.setHeight((short) 800);

                //创建一行，第2行
                row = sheet.createRow(1);
                for (int i = 0; i < titles.length; i++) {
                    XSSFCell cell = row.createCell(i);

                    cell.setCellValue(titles[i]);
                }

                for (int i = 0; i < booklist.size(); i++) {
                    BookDetailsVo book = booklist.get(i);

                    row = sheet.createRow(i + 2);
                    XSSFCell cell = row.createCell(0);
                    cell.setCellValue(book.getISBN());
                    cell = row.createCell(1);
                    cell.setCellValue(book.getBooktype());
                    cell = row.createCell(2);
                    cell.setCellValue(book.getBookname());
                    cell = row.createCell(3);
                    cell.setCellValue(book.getAuthor());
                    cell = row.createCell(4);
                    cell.setCellValue(book.getPress());
                    cell = row.createCell(5);
                    cell.setCellValue(book.getPrice());
                    cell = row.createCell(6);
                    cell.setCellValue(book.getNum());
                    cell = row.createCell(7);
                    cell.setCellValue(book.getCurrentNum());
                    cell = row.createCell(8);
                    cell.setCellValue(book.getPutdate());
                    cell = row.createCell(9);
                    cell.setCellValue(book.getAdminName());
                    cell = row.createCell(10);
                    cell.setCellValue(book.getDescription());
                }

                // 文件名是时间格式
                SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
                String date = format.format(new Date());

                String fileName = date + ".xlsx";

                File file = new File(Constant.exportBook, fileName);

                FileOutputStream fos = new FileOutputStream(file);
                // 输出文件
                workbook.write(fos);
                workbook.close();
                fos.close();

                System.out.println("导出成功，文件的路径为：" + file.getAbsolutePath());
                return fileName;

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;


    }

    public String exportErrorsExcel(List<BookExportFailureVO>  voList) {

        try {

            if (FileUtil.createOrExistsDir(new File(Constant.exportError))) {

                //表头
                String[] titles = new String[]{"图书ISBN号", "图书类型", "图书名称", "作者名称", "出版社", "价格", "数量",  "描述","错误信息"};
                // 创建工作簿
                XSSFWorkbook workbook = new XSSFWorkbook();
                // 新建工作表
                XSSFSheet sheet = workbook.createSheet("错误数据导出");
                //自动列大小
                sheet.autoSizeColumn(1, true);
                //创建行，0表示第一行
                XSSFRow row = sheet.createRow(0);
                //合并单元格  参数说明：1：开始行 2：结束行  3：开始列 4：结束列
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));
                //获取列【标题列】
                XSSFCell title = row.createCell(0);
                //创建列的样式
                XSSFCellStyle titleStyle = workbook.createCellStyle();
                //垂直和水平居中
                titleStyle.setAlignment(HorizontalAlignment.CENTER);
                titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                //设置背景颜色
                titleStyle.setFillForegroundColor(IndexedColors.RED.getIndex());// 设置背景色
                titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

                //设置字体
                XSSFFont font = workbook.createFont();
                font.setFontName("仿宋_GB2312");
                font.setFontHeightInPoints((short) 20);
                titleStyle.setFont(font);


                //设置文本
                title.setCellValue("错误数据导出");
                //设置样式
                title.setCellStyle(titleStyle);
                //设置行高度
                row.setHeight((short) 800);

                //创建一行，第2行
                row = sheet.createRow(1);
                for (int i = 0; i < titles.length; i++) {
                    XSSFCell cell = row.createCell(i);

                    cell.setCellValue(titles[i]);
                }

                for (int i = 0; i < voList.size(); i++) {
                    BookExportFailureVO book = voList.get(i);
//                    "图书ISBN号", "图书类型", "图书名称", "作者名称", "出版社", "价格", "数量",  "描述","错误信息"
                    row = sheet.createRow(i + 2);
                    XSSFCell cell = row.createCell(0);
                    cell.setCellValue(book.getISBN());
                    cell = row.createCell(1);
                    cell.setCellValue(book.getBooktype());
                    cell = row.createCell(2);
                    cell.setCellValue(book.getBookName());
                    cell = row.createCell(3);
                    cell.setCellValue(book.getAuthor());
                    cell = row.createCell(4);
                    cell.setCellValue(book.getPress());
                    cell = row.createCell(5);
                    cell.setCellValue(book.getPrice());
                    cell = row.createCell(6);
                    cell.setCellValue(book.getNum());
                    cell = row.createCell(7);
                    cell.setCellValue(book.getDescription());
                    cell = row.createCell(8);
                    cell.setCellValue(book.getErrorMsg());
                }

                // 文件名是时间格式
                SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
                String date = format.format(new Date());

                String fileName = date + ".xlsx";

                File file = new File(Constant.exportError, fileName);

                FileOutputStream fos = new FileOutputStream(file);
                // 输出文件
                workbook.write(fos);
                workbook.close();
                fos.close();

                System.out.println("导出成功，文件的路径为：" + file.getAbsolutePath());
                return fileName;

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;


    }

    /**
     * 逻辑删除图书信息：将图书的总数量和在馆数量设置为0
     * @param bookDo
     * @return
     */
    public int deleteBook(BookDo bookDo) {
        BookDo bookInfoById = dao.getBookInfoById(bookDo);
        if (bookInfoById != null) {
            bookInfoById.setCurrentNum(bookInfoById.getCurrentNum()-1);
            bookInfoById.setNum(bookInfoById.getNum()-1);
            int count = dao.deleteBook(bookInfoById);
            return 1;
        }
        return 0;
    }
}