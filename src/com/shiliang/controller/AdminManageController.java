package com.shiliang.controller;

import com.shiliang.domain.PageBean;
import com.shiliang.domain.data.AdminDo;
import com.shiliang.domain.query.AdminQuery;
import com.shiliang.domain.vo.AdminManageListVO;
import com.shiliang.service.AdminManageService;
import com.shiliang.utils.DataUtils;
import com.shiliang.utils.Md5Utils;
import com.shiliang.utils.myspringmvc.Controller;
import com.shiliang.utils.myspringmvc.ModelAndView;
import com.shiliang.utils.myspringmvc.RequestContextHolder;
import com.shiliang.utils.myspringmvc.RequestMapping;
import net.sf.json.JSONObject;

import java.io.IOException;

/**
 * @author SL
 * @create 2020-03-21 21:22
 * @description 管理员管理
 */
@Controller
public class AdminManageController {

    AdminManageService service = new AdminManageService();

    /**
     * 分页查询显示管理员信息
     *
     * @param query
     * @return
     */
    @RequestMapping("/admin/adminManageController_list")
    public ModelAndView list(AdminQuery query) {
        //设置分页最多显示的条数
        int pageSize = 5;
        query.setCurrentPage(DataUtils.getPageCode(query.getCurrentPage() + ""));
        PageBean<AdminManageListVO> pb = service.pageSearch(query, pageSize);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addParam("query", query);
        modelAndView.addParam("pb", pb);
        modelAndView.setViewName("/admin/adminManage.jsp");
        return modelAndView;
    }

    @RequestMapping("/admin/adminManageController_addAdmin")
    public void addAdmin(AdminDo adminDo) throws IOException {
        //设置删除状态 1为未删除
        adminDo.setState(1);
        //设置管理员初始密码:123456
        adminDo.setPassword(Md5Utils.md5("123456"));
        AdminDo adminByUserName = service.getAdminByUserName(adminDo);

        //管理员用户名重复
        int resultCode = -2;
        if (adminByUserName == null) {
            int count = service.addAdmin(adminDo);
            if (count > 0) {
                //添加成功
                resultCode = 1;
            } else {
                //添加失败
                resultCode = -1;
            }
        }
        RequestContextHolder.getResponse().getWriter().print(resultCode);
//        System.out.println(adminDo);
    }

    /**
     * 修改管理员信息
     */
    @RequestMapping("/admin/adminManageController_updateAdmin")
    public void updateAdmin(AdminDo adminDo) throws IOException {




        int resultCode = -1;

        int count = service.updateAdmin(adminDo);
        if (count > 0) {
            resultCode = 1;
        } else {
            resultCode = -1;
        }

        RequestContextHolder.getResponse().getWriter().print(resultCode);
    }

    /**
     * 获取管理员信息
     */
    @RequestMapping("/admin/adminManageController_getAdminInfoById")
    public void getAdminInfoById(int id) throws IOException {

        AdminDo adminDo=new AdminDo(id);
        AdminDo adminInfoById = service.getAdminInfoById(adminDo);

        RequestContextHolder.getResponse().getWriter().print(JSONObject.fromObject(adminInfoById).toString());
    }

    /**
     * 删除管理员信息
     *  逻辑删除
     *      将管理员的state设置为0
     */
    @RequestMapping("/admin/adminManageController_deleteAdmin")
    public void deleteAdmin(int id) throws IOException {

        AdminDo adminDo = new AdminDo(id);
        int count = service.deleteAdmin(adminDo);

        int resultCode = -1;
        if (count > 0) {
            resultCode = 1;
        }
        RequestContextHolder.getResponse().getWriter().print(resultCode);

    }


}
