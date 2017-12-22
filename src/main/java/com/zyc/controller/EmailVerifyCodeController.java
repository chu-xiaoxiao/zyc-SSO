package com.zyc.controller;

import com.zyc.service.UserService;
import com.zyc.util.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * Created by YuChen Zhang on 17/11/03.
 */
@Controller("emailVerifyCodeController")
public class EmailVerifyCodeController extends AbstractController {
    @Autowired
    @Qualifier("userServiceImplement")
    UserService userService;

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String url = request.getRequestURI();
        if(url.contains("AddUser.do")){
            this.getVerifyCodeAddNewUser(request,response);
        }else{
            this.getVerifyCOde(request,response);
        }
        return null;
    }
    private void getVerifyCOde(HttpServletRequest request, HttpServletResponse response) throws Exception {
        MailUtil mailUtil = new MailUtil(UserController.class.getClassLoader().getResource("mail.yml"));
        PrintWriter out = response.getWriter();
        String veudyCode = null;
        if(userService.findUserByEmail(request.getParameter("useremail")).size()==0){
            out.print("该邮箱暂未注册");
            return;
        }
        try {
            veudyCode = mailUtil.sendVerifyCode(10, request.getParameter("useremail"));
            out.print("验证码已发送至您的邮箱");
            HttpSession session = request.getSession();
            session.setAttribute("verifyCode",veudyCode);
        } catch (UnsupportedEncodingException e) {
            out.print("验证码获取失败");
            e.printStackTrace();
        } catch (MessagingException e) {
            out.print("验证码获取失败");
            e.printStackTrace();
        }finally{
            out.flush();
            out.close();
        }
    }
    private void getVerifyCodeAddNewUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
        MailUtil mailUtil = new MailUtil(UserController.class.getClassLoader().getResource("mail.yml"));
        PrintWriter out = response.getWriter();
        String veudyCode = null;
        try {
            veudyCode = mailUtil.sendVerifyCode(10, request.getParameter("useremail"),"用户注册验证码");
            out.print("验证码已发送至您的邮箱");
            HttpSession session = request.getSession();
            session.setAttribute("verifyCode",veudyCode);
        } catch (UnsupportedEncodingException e) {
            out.print("验证码获取失败");
            e.printStackTrace();
        } catch (MessagingException e) {
            out.print("验证码获取失败");
            e.printStackTrace();
        }finally {
            if(out!=null)
            out.flush();
            out.close();
        }
    }
}
