package com.zyc.controller;

import com.zyc.util.MailUtil;
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
    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        MailUtil mailUtil = new MailUtil(UserController.class.getClassLoader().getResource("mail.yml"));
        PrintWriter out = response.getWriter();
        String veudyCode = null;
        try {
            veudyCode = mailUtil.sendVerifyCode(10, request.getParameter("useremail"));
        } catch (UnsupportedEncodingException e) {
            out.print("验证码获取失败");
            e.printStackTrace();
        } catch (MessagingException e) {
            out.print("验证码获取失败");
            e.printStackTrace();
        }
        out.print("验证码已发送至您的邮箱");
        HttpSession session = request.getSession();
        session.setAttribute("verifyCode",veudyCode);
        out.flush();
        out.close();
        return null;
    }
}
