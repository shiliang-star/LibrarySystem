package com.shiliang.controller;

import com.shiliang.utils.Constant;
import com.shiliang.utils.myspringmvc.Controller;
import com.shiliang.utils.myspringmvc.RequestMapping;
import net.sf.json.JSONObject;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author SL
 * @create 2020-03-15 15:54
 * @description 实现文件上传功能
 */
@Controller
public class FileUploadController extends BaseController{


    @RequestMapping("/admin/fileUploadController_fileUpload")
    public void fileUpload(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 检测我们是否一个文件上传的请求
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        //创建磁盘工厂
        FileItemFactory factory = new DiskFileItemFactory();
        //创建处理工具
        ServletFileUpload upload = new ServletFileUpload(factory);
        //解析
        List<FileItem> fileItems = upload.parseRequest(request);

        JSONObject jsonObject=new JSONObject();
        for (FileItem fileItem : fileItems) {

            System.out.println(fileItem);
            if (fileItem.getContentType() != null && fileItem.getContentType().equals(Constant.xls) || fileItem.getContentType().equals(Constant.xlsx)) {

                //处理不在表单的字段
                if (!fileItem.isFormField()) {
                    String fileName = System.currentTimeMillis()+"_"+fileItem.getName();

                    //保存文件到硬盘中
                    File storeFile=new File(Constant.uploadPath+fileName);
                    fileItem.write(storeFile);



                    jsonObject.put("code", "1");
                    jsonObject.put("fileName",fileName);
                    jsonObject.put("msg","上传成功");


                }


            } else {
                jsonObject.put("code", "-1");
                jsonObject.put("msg","上传的文件格式不正确");
            }

            response.getWriter().print(jsonObject.toString());



        }

//        System.out.println("我被调用了");
    }
}
