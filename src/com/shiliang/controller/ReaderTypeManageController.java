package com.shiliang.controller;

import com.shiliang.domain.PageBean;
import com.shiliang.domain.data.ReadertypeDo;
import com.shiliang.service.ReaderTypeManageService;
import com.shiliang.utils.DataUtils;
import com.shiliang.utils.myspringmvc.Controller;
import com.shiliang.utils.myspringmvc.RequestMapping;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * @author SL
 * @create 2020-03-17 18:28
 * @description 实现图书系统设置功能
 */
@Controller
public class ReaderTypeManageController extends BaseController {

    ReaderTypeManageService service=new ReaderTypeManageService();

    @RequestMapping("/admin/readerTypeManageController_list")
    public void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int currentPage = DataUtils.getPageCode(request.getParameter("currentPage"));
        int pageSize = 5;

        PageBean<ReadertypeDo> pb = service.pageSearch(currentPage, pageSize);

        request.setAttribute("pb",pb);

        request.getRequestDispatcher("/admin/readerTypeManage.jsp").forward(request, response);

    }


//

    @RequestMapping("/admin/readerTypeManageController_getReaderTypeById")
    public void getReaderTypeById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");

        ReadertypeDo readertypeDo=new ReadertypeDo();
        readertypeDo.setId(Integer.parseInt(id));

        ReadertypeDo readerTypeByID = service.getReaderTypeByID(readertypeDo);

        JSONObject jsonObject = JSONObject.fromObject(readerTypeByID);


        response.getWriter().print(jsonObject);
    }

    @RequestMapping("/admin/readerTypeManageController_updateReaderType")
    public void updateReaderType(HttpServletRequest request, HttpServletResponse response) throws InvocationTargetException, IllegalAccessException, IOException {

        String readerTypeId = request.getParameter("readerTypeId");
        String readerTypeName = request.getParameter("readerTypeName");
//        String maxNum = request.getParameter("maxNum");
//        String bday = request.getParameter("bday");
//        String penalty = request.getParameter("penalty");
//        String renewDays = request.getParameter("renewDays");

        ReadertypeDo readertypeDo = new ReadertypeDo();

        BeanUtils.copyProperties(readertypeDo,request.getParameterMap());

        readertypeDo.setId(Integer.parseInt(readerTypeId));
        readertypeDo.setName(readerTypeName);

        Integer count = service.updateReaderTypeById(readertypeDo);

        int resultCode = -1;

        if (count > 0) {
            resultCode = 1;//添加成功
        }

        response.getWriter().print(resultCode);

    }

    @RequestMapping("/admin/readerTypeManageController_addReaderType")
    public void addReaderType(HttpServletRequest request, HttpServletResponse response) throws InvocationTargetException, IllegalAccessException, IOException {

        ReadertypeDo readertypeDo=new ReadertypeDo();


        BeanUtils.copyProperties(readertypeDo, request.getParameterMap());

        int count = service.addReaderType(readertypeDo);


        int resultCode = -1;

        if (count > 0) {
            resultCode = 1;
        }


        response.getWriter().print(resultCode);

//        System.out.println(readertypeDo);


    }

    @RequestMapping("/admin/readerTypeManageController_getReaderTypeByName")
    public void getReaderTypeByName(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String name = request.getParameter("name");
        ReadertypeDo readertypeDo=new ReadertypeDo();
        readertypeDo.setName(name);

        ReadertypeDo readerTypeByName = service.getReaderTypeByName(readertypeDo);

        int resultCode = -1;//存在该读者类型名称

        if (readerTypeByName == null) {
            resultCode = 1;//不存在该读者类型名称
        }

        response.getWriter().print(resultCode);


    }
}
