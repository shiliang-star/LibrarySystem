package com.shiliang.controller;

import com.shiliang.utils.Constant;
import com.shiliang.utils.DataUtils;
import com.shiliang.utils.myspringmvc.Controller;
import com.shiliang.utils.myspringmvc.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author SL
 * @create 2020-03-14 23:17
 * @description 1. 下载文件，传递两个参数：1. 文件类型、2.文件名
 *              文件类型：1.下载书籍导出
 *                       2.下载模板
 *                       3.下载错误
 *                       4.下载读者导出
 *              2. Controller 获取到参数，进行相应的判断（文件是否存在，参数是否合法）
 *              3. 获取到文件后，以流的形式输出文件给浏览器（下载）
 */
@Controller
public class FileDownloadController extends BaseController{


    @RequestMapping("/fileDownloadController_fileDownload")
    public void fileDownload(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String fileType = request.getParameter("fileType");
        String fileName = request.getParameter("fileName");

        //判断参数的合法性 文件类型必须是数字
        if (DataUtils.isValid(fileType) && DataUtils.isValid(fileName) && DataUtils.isNumber(fileType)) {

            int fileTypeNum = Integer.parseInt(fileType);

            String path=null;
            //对传入的文件类型进行panduan
            if (fileTypeNum == 1) {
                path=Constant.exportBook;
            }
            if (fileTypeNum == 2) {
                path = Constant.template;
            }
            if (fileTypeNum == 3) {
                path=Constant.exportError;
            }
            if (fileTypeNum == 4) {
                path = Constant.exportReader;
            }

            //对下载路径进行pandaun
            if (path != null) {
                File file = new File(path ,fileName);


                //判断文件是否存在
                if (file.exists()) {

                    // 设置文件的类型
                    response.setContentType("application/zip");
                    // 告诉客户端（浏览器）以下载的方式打开文件，设置 Context-Dispostion 头，并设置文件名字
                    response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
                    ServletOutputStream out = response.getOutputStream();
                    FileInputStream in = new FileInputStream(file);
                    byte[] bytes = new byte[1024];
                    int count = 0;
                    while ((count = in.read(bytes)) != -1) {
                        out.write(bytes, 0, count);
                    }

                    out.flush();
                    in.close();
                    out.close();
                } else {
                 response.getWriter().write("您要下载的文件不存在！");
                }
            } else {
             response.getWriter().write("您要下载的路径找不到！");
            }
        } else {
            response.getWriter().write("缺少参数或传入的参数不正确，必须传递两个参数：1. 文件类型（必须是数字）、2.文件名！");
        }




    }
}
