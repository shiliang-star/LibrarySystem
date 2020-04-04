package com.shiliang.controller;

import com.shiliang.domain.data.AuthorizationDo;
import com.shiliang.service.AuthorizationService;
import com.shiliang.utils.myspringmvc.Controller;
import com.shiliang.utils.myspringmvc.RequestContextHolder;
import com.shiliang.utils.myspringmvc.RequestMapping;
import net.sf.json.JSONObject;

import java.io.IOException;

/**
 * @author SL
 * @create 2020-03-24 18:32
 * @description 管理员权限管理
 */
@Controller
public class AuthorizationManageController {

    AuthorizationService service=new AuthorizationService();

    @RequestMapping("/admin/AuthorizationManageController_getAuthorizationById")
    public void getAuthorizationById(int id) throws IOException {
        AuthorizationDo authorizationDo = new AuthorizationDo(id);
        AuthorizationDo authorizationById = service.getAuthorizationById(authorizationDo);
        RequestContextHolder.getResponse().getWriter().print(JSONObject.fromObject(authorizationById).toString());

    }

    /**
     * 修改管理员的权限
     */
    @RequestMapping("/admin/AuthorizationManageController_updateAuthorization")
    public void updateAuthorization(int id, String power) throws IOException {

        AuthorizationDo authorizationDo=new AuthorizationDo(id,0,0,0,0,0,0,0);
        String[] powers = power.split(",");
        for (String s : powers) {
            if (s.equals("bookSet")) {
                //图书设置权限
                authorizationDo.setBookSet(1);
            } else if (s.equals("readerSet")) {
                //读者设置权限
                authorizationDo.setReaderSet(1);
            } else if (s.equals("borrowSet")) {
                //借阅设置权限
                authorizationDo.setBorrowSet(1);
            }  else if (s.equals("typeSet")) {
                //图书分类设置权限
                authorizationDo.setTypeSet(1);
            } else if (s.equals("backSet")) {
                //归还设置权限
                authorizationDo.setBackSet(1);
            } else if (s.equals("forfeitSet")) {
                //逾期设置权限
                authorizationDo.setForfeitSet(1);
            } else if (s.equals("sysSet")) {
                //系统设置权限
                authorizationDo.setSysSet(1);
            }
        }

        int count = service.updateAuthorization(authorizationDo);

        int resultCode = -1;
        if (count > 0) {
            resultCode = 1;
        }
        RequestContextHolder.getResponse().getWriter().print(resultCode);

//        System.out.println(id+" "+power);
    }

}
