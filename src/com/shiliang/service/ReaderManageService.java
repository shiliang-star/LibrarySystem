package com.shiliang.service;

import com.monitorjbl.xlsx.StreamingReader;
import com.shiliang.dao.ReaderManageDao;
import com.shiliang.dao.ReaderTypeManageDao;
import com.shiliang.domain.PageBean;
import com.shiliang.domain.data.BookDo;
import com.shiliang.domain.data.BooktypeDo;
import com.shiliang.domain.data.ReaderDo;
import com.shiliang.domain.data.ReadertypeDo;
import com.shiliang.domain.query.ReaderQuery;
import com.shiliang.domain.vo.*;
import com.shiliang.utils.CheckUtils;
import com.shiliang.utils.Constant;
import com.shiliang.utils.FileUtil;
import com.shiliang.utils.Md5Utils;
import net.sf.json.JSONObject;
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
 * @create 2020-03-18 18:18
 * @description 读者管理服务层
 */
public class ReaderManageService {

    ReaderManageDao dao=new ReaderManageDao();
    ReaderTypeManageDao readerTypeManageDao=new ReaderTypeManageDao();


    public PageBean<ReaderManageListVo> pageSearch(ReaderQuery query, int pageSize) {
        return dao.pageSearch(query, pageSize);

    }


    public List<ReadertypeDo> getAllReaderTypes() {
        try {
            return dao.getAllReaderTypes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ReaderDetailsVo getReaderInfoById(ReaderDetailsVo vo) {

        try {
            return dao.getReaderInfoById(vo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Integer updateReaderInfo(ReaderDo readerDo) {
        try {
            return dao.updateReaderInfo(readerDo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Integer addReader(ReaderDo readerDo) {
        try {
            return dao.addReader(readerDo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;


    }

    public ReaderDo getReaderByPaperNo(ReaderDo readerDo) {

            return dao.getReaderByPaperNo(readerDo);
    }

    /**
     * 导出读者信息
     * @return
     */
    public String exportReader() {

        List<ReaderDetailsVo> readerDetailsVos = dao.exportReader();

        String fileName = exportExcel(readerDetailsVos);
        return fileName;

    }

    public String exportExcel(List<ReaderDetailsVo> readerlist) {

        try {

            if (FileUtil.createOrExistsDir(new File(Constant.exportReader))) {

                //表头
                String[] titles = new String[]{"证件号码", "读者姓名", "读者类型", "邮箱", "联系方式", "操作管理员"};
                // 创建工作簿
                XSSFWorkbook workbook = new XSSFWorkbook();
                // 新建工作表
                XSSFSheet sheet = workbook.createSheet("数据导出");
                //自动列大小
                sheet.autoSizeColumn(1, true);
                //创建行，0表示第一行
                XSSFRow row = sheet.createRow(0);
                //合并单元格  参数说明：1：开始行 2：结束行  3：开始列 4：结束列
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
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

                for (int i = 0; i < readerlist.size(); i++) {
                    ReaderDetailsVo reader = readerlist.get(i);
//                    "证件号码", "读者姓名", "读者类型", "邮箱", "联系方式", "操作管理员"
                    row = sheet.createRow(i + 2);
                    XSSFCell cell = row.createCell(0);
                    cell.setCellValue(reader.getPaperNO());
                    cell = row.createCell(1);
                    cell.setCellValue(reader.getReaderName());
                    cell = row.createCell(2);
                    cell.setCellValue(reader.getReaderType());
                    cell = row.createCell(3);
                    cell.setCellValue(reader.getEmail());
                    cell = row.createCell(4);
                    cell.setCellValue(reader.getPhone());
                    cell = row.createCell(5);
                    cell.setCellValue(reader.getAdminName());
                }

                // 文件名是时间格式
                SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
                String date = format.format(new Date());

                String fileName = date + ".xlsx";

                File file = new File(Constant.exportReader, fileName);

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
     * 批量添加读者信息，根据文件名来获取到硬盘上的指定文件，进行参数合法性判断，然后调用dao方法进行添加
     *
     * @param fileName 文件名
     * @param admin   管理员信息
     * @return
     */
    public JSONObject batchAddBook(String fileName, AdminDetailsVo admin) throws FileNotFoundException {
        //表头
        String[] titles = new String[]{"证件号码", "读者姓名", "读者类型", "邮箱", "联系方式"};

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
        List<ReaderDo> success = new ArrayList<>();

        //用于封装错误信息
        List<ReaderExportFailureVO> failure = new ArrayList<>();

        for (Row cells : sheet) {
            readcount++;

//            判断表头是否合法
            if (readcount == 2) {
                if (cells.getLastCellNum() != 5) {
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
                String paperNO = rowlist.get(0);
                String readerName = rowlist.get(1);
                String readerType = rowlist.get(2);
                String email = rowlist.get(3);
                String phone = rowlist.get(4);

                //可能出现空行
                if ("".equals(paperNO) && "".equals(readerName) && "".equals(readerType) && "".equals(phone) && "".equals(email) ) {
                    continue;//继续执行循环
                }

                //可能某一个单元格没有值//
//                TODO 存在空值无法添加到错误数据中
                if (" ".equals(paperNO) || " ".equals(readerName) || " ".equals(readerType) ) {
                    //必填字段
                    failure.add(ReaderExportFailureVO.createReaderExportFailureVO(paperNO, readerName, readerType, phone, email, "读者证件号、读者名称、读者类型是必填的")
                    );
                    continue;
                }

                // 如果必填字段不为空，那么将数据封装到 Reader（ReaderDO）类中
               ReaderDo readerDo=new ReaderDo();
                readerDo.setPaperNo(paperNO);
                readerDo.setName(readerName);
                readerDo.setEmail(email);
                readerDo.setPhone(phone);

                //判断证件号是否重复
                ReaderDo readerByPaperNo = dao.getReaderByPaperNo(readerDo);

                if (readerByPaperNo != null) {
                    failure.add(ReaderExportFailureVO.createReaderExportFailureVO(paperNO, readerName, readerType, phone, email, "证件号已经存在")
                    );
                    continue;
                }

                boolean isRepeat = false;
                //判断添加的数据中是否存在相同的证件号
                for (int i = 0; i < success.size(); i++) {
                    if (success.get(i).getPaperNo().equals(paperNO)) {
                        failure.add(ReaderExportFailureVO.createReaderExportFailureVO(paperNO, readerName, readerType, phone, email, "在添加的数据里存在重复的证件号"));
                        isRepeat = true;
                        break;
                    }
                }
                    if (isRepeat) {
                        continue;
                    }

                    //判断读者类型是否存在
                    ReadertypeDo readerTypeByName = readerTypeManageDao.getReaderTypeByName(new ReadertypeDo(readerType));

                    if (readerTypeByName == null) {
                        failure.add(ReaderExportFailureVO.createReaderExportFailureVO(paperNO, readerName, readerType, phone, email, "读者类型不存在"));
                        continue;
                    }
                    readerDo.setFkReadertype(readerTypeByName.getId());


                    //判断邮箱格式是否正常
                    if (!CheckUtils.checkEmail(email)) {
                        failure.add(ReaderExportFailureVO.createReaderExportFailureVO(paperNO, readerName, readerType, phone, email, "邮箱格式有误"));
                        continue;
                    }

                    //判断电话号码格式是否正确
                    if (!CheckUtils.checkMobileNumber(phone)) {
                        failure.add(ReaderExportFailureVO.createReaderExportFailureVO(paperNO, readerName, readerType, phone, email, "手机号格式有误"));
                        continue;

                    }

                    //插入其他表格中没有的数据
                    // 设置初始密码为123456
                    readerDo.setPassword(Md5Utils.md5("123456"));
                    //设置操作的管理员
                    readerDo.setFkAdmin(admin.getId());
                    //设置创建时间
                    readerDo.setCreateTime(new java.sql.Date(System.currentTimeMillis()));
                    //将正确数据封装到集合中
                    success.add(readerDo);

            }

        }
        //导入数据
        //何时导入数据
        boolean flag = (!success.isEmpty()) && (success.size() == Constant.getimportsize(latRowNum)) || (readcount == latRowNum);
        if (flag){
            //定义批处理参数
            List<Object[]> params = new ArrayList<>();
            //遍历success集合
            for (int i = 0; i < success.size(); i++) {
                params.add(new Object[]{success.get(i).getPassword(), success.get(i).getName(), success.get(i).getPaperNo(), success.get(i).getPhone(),
                        success.get(i).getEmail(), success.get(i).getCreateTime(), success.get(i).getFkReadertype(), success.get(i).getFkAdmin()});
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


    /**
     * 导出错误数据到Excel表格中
     * @param voList
     * @return
     */
    public String exportErrorsExcel(List<ReaderExportFailureVO>  voList) {

        try {

            if (FileUtil.createOrExistsDir(new File(Constant.exportError))) {

                //表头
                String[] titles = new String[]{"证件号码", "读者姓名", "读者类型", "邮箱", "联系方式","错误信息"};
                // 创建工作簿
                XSSFWorkbook workbook = new XSSFWorkbook();
                // 新建工作表
                XSSFSheet sheet = workbook.createSheet("错误数据导出");
                //自动列大小
                sheet.autoSizeColumn(1, true);
                //创建行，0表示第一行
                XSSFRow row = sheet.createRow(0);
                //合并单元格  参数说明：1：开始行 2：结束行  3：开始列 4：结束列
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
                //获取列【标题列】
                XSSFCell title = row.createCell(0);
                //创建列的样式
                XSSFCellStyle titleStyle = workbook.createCellStyle();
                //垂直和水平居中
                titleStyle.setAlignment(HorizontalAlignment.CENTER);
                titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                //设置背景颜色
                titleStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
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
                    ReaderExportFailureVO book = voList.get(i);
//                    "证件号码", "读者姓名", "读者类型", "邮箱", "联系方式","错误信息"
                    row = sheet.createRow(i + 2);
                    XSSFCell cell = row.createCell(0);
                    cell.setCellValue(book.getPaperNO());
                    cell = row.createCell(1);
                    cell.setCellValue(book.getReaderName());
                    cell = row.createCell(2);
                    cell.setCellValue(book.getReaderType());
                    cell = row.createCell(3);
                    cell.setCellValue(book.getEmail());
                    cell = row.createCell(4);
                    cell.setCellValue(book.getPhone());
                    cell = row.createCell(5);
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

}
