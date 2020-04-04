package com.shiliang.controller;

import com.shiliang.domain.data.ReaderDo;
import com.shiliang.service.ReaderService;
import com.shiliang.utils.Md5Utils;
import com.shiliang.utils.myspringmvc.Controller;
import com.shiliang.utils.myspringmvc.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author SL
 * @create 2020-03-09 17:48
 * @description 读者登陆管理
 */
@Controller
public class ReaderLoginController extends BaseController {

    ReaderService service=new ReaderService();

    @RequestMapping("/readerLoginController_login")
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String paperNo = request.getParameter("paperNo");
        String password = request.getParameter("password");

        ReaderDo readerDo=new ReaderDo();
        readerDo.setPaperNo(paperNo);
        readerDo.setPassword(Md5Utils.md5(password));

        ReaderDo reader = service.findReaderByPaperNo(readerDo);


        //用户不存在 -1
        //用户密码错误 -2
        //登陆成功  1
        int resultcode=1;
        if (reader == null) {
            resultcode = -1;
        } else if (!reader.getPassword().equals(readerDo.getPassword())) {
            resultcode = -2;
        } else {
            request.getSession().setAttribute("reader",reader);
        }
        response.getWriter().print(resultcode);

    }

    @RequestMapping("/readerLoginController_logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {


        request.getSession().removeAttribute("reader");

        response.sendRedirect(request.getContextPath()+"/reader/readerLogin.jsp");

    }
}
