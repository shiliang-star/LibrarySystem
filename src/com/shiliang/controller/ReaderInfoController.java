package com.shiliang.controller;

import com.shiliang.domain.data.AdminDo;
import com.shiliang.domain.data.ReaderDo;
import com.shiliang.domain.vo.AdminDetailsVo;
import com.shiliang.service.ReaderService;
import com.shiliang.utils.Md5Utils;
import com.shiliang.utils.myspringmvc.Controller;
import com.shiliang.utils.myspringmvc.RequestContextHolder;
import com.shiliang.utils.myspringmvc.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author SL
 * @create 2020-04-03 17:37
 * @description 读者个人信息（读者端）
 */
@Controller
public
class ReaderInfoController {

   ReaderService service=new ReaderService();
    /**
     * 更改管理员的个人信息
     *
     * @throws IOException
     */
    @RequestMapping("/readerInfoController_updateReaderInfo")
    public void updateReaderInfo(ReaderDo readerDo) throws IOException {
        ReaderDo vo= (ReaderDo) RequestContextHolder.getSession().getAttribute("reader");

        readerDo.setId(vo.getId());
        int count = service.updateReaderInfo(readerDo);

        int resultcode = -1;

        if (count > 0) {
            resultcode = 1;
            vo.setPhone(readerDo.getPhone());
            vo.setEmail(readerDo.getEmail());
            RequestContextHolder.getSession().setAttribute("reader", vo);
        }
        RequestContextHolder.getResponse().getWriter().print(resultcode);

    }

    /**
     * 修改读者密码
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/readerInfoController_updateReaderPassword")
    public void updateAdminPassword(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ReaderDo vo= (ReaderDo) request.getSession().getAttribute("reader");

        String oldPwd = request.getParameter("oldPwd");
        String newPwd = request.getParameter("newPwd");
        String confirmPwd = request.getParameter("confirmPwd");

        int resultcode = -1;//原密码输入不一致
        if (vo.getPassword().equals(Md5Utils.md5(oldPwd))) {
            if (newPwd.equals(confirmPwd)) {
                ReaderDo readerDo=new ReaderDo();
                readerDo.setId(vo.getId());
                readerDo.setPassword(Md5Utils.md5(newPwd));


                service.updateReaderPassword(readerDo);
                vo.setPassword(readerDo.getPassword());
                request.getSession().setAttribute("reader", vo);


                resultcode = 1;//修改成功
            } else {
                resultcode = -2;//两次输入新密码不一致
            }

        }


        response.getWriter().print(resultcode);


    }



}
