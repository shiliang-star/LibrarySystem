package com.shiliang.controller;

import com.shiliang.domain.data.AdminDo;
import com.shiliang.domain.vo.AdminDetailsVo;
import com.shiliang.service.AdminService;
import com.shiliang.utils.Md5Utils;
import com.shiliang.utils.myspringmvc.Controller;
import com.shiliang.utils.myspringmvc.RequestMapping;
import jdk.nashorn.internal.ir.RuntimeNode;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author SL
 * @create 2020-03-10 18:24
 * @description 管理员信息
 */
@Controller
public class AdminInfoController extends BaseController{

     AdminService service=new AdminService();

    /**
     * 更改管理员的个人信息
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/adminInfoController_updateAdminInfo")
    public void updateAdminInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        AdminDetailsVo vo= (AdminDetailsVo) request.getSession().getAttribute("admin");

        AdminDo adminDo=new AdminDo();
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        adminDo.setId(vo.getId());
        adminDo.setName(name);
        adminDo.setPhone(phone);

        Integer count = service.updateAdminInfo(adminDo);

        int resultcode = -1;

        if (count > 0) {
            resultcode = 1;
            vo.setName(name);
            vo.setPhone(phone);
            request.getSession().setAttribute("admin", vo);
        }
        response.getWriter().print(resultcode);

    }

    /**
     * 修改管理员密码
     * 获取到数据后的逻辑：
     *           判断修改前的密码跟原密码是否一致
     *                                  一致
     *                                      判断两次输入新密码是否一致
     *                                                        一致
     *                                                            通过调service进行修改密码，然后重新保存到session域中
     *                                                        不一致
     *                                                             返回两次密码输入不一致
     *                                  不一致
     *                                       返回与原密码不一致
     * @param request
     * @param response
     */

    @RequestMapping("/adminInfoController_updateAdminPassword")
    public void updateAdminPassword(HttpServletRequest request, HttpServletResponse response) throws IOException {

        AdminDetailsVo vo= (AdminDetailsVo) request.getSession().getAttribute("admin");

        String oldPwd = request.getParameter("oldPwd");
        String newPwd = request.getParameter("newPwd");
        String confirmPwd = request.getParameter("confirmPwd");

        int resultcode = -1;//原密码输入不一致
        if (vo.getPassword().equals(Md5Utils.md5(oldPwd))) {
            if (newPwd.equals(confirmPwd)) {
                AdminDo adminDo=new AdminDo();
                adminDo.setId(vo.getId());
                adminDo.setPassword(Md5Utils.md5(newPwd));


                service.updateAdminPassword(adminDo);
                vo.setPassword(adminDo.getPassword());
                request.getSession().setAttribute("admin", vo);


                resultcode = 1;//修改成功
            } else {
                resultcode = -2;//两次输入新密码不一致
            }

        }


        response.getWriter().print(resultcode);


    }
}
