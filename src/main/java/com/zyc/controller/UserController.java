package com.zyc.controller;

import com.zyc.model.User;
import com.zyc.service.UserService;
import com.zyc.util.EncodeMD5;
import com.zyc.util.MailUtil;
import com.zyc.util.VerifyCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

@Controller
public class UserController {
	@Autowired
    @Qualifier("userServiceImplement")
	private UserService userService;

	@RequestMapping(value="/adduser.do")
	public ModelAndView add(HttpServletRequest request, ModelAndView modelAndView){
        String verifyCode = request.getParameter("veudyCode_email");
        HttpSession session = request.getSession();
        if(!session.getAttribute("verifyCode").equals(verifyCode)){
            modelAndView.addObject("addError","验证码错误");
            modelAndView.setViewName("redirect:/user/logIn.jsp");
            return modelAndView;
        }
		User user = new User();
		user.setUsername(request.getParameter("username"));
		user.setUserpassword(EncodeMD5.encodeMD5(request.getParameter("password"),user.getUsername()));
        user.setUsermail(request.getParameter("useremail"));
		user.setUsertype(1);
        userService.insertuUser(user);

        session.setAttribute("user",user);
        modelAndView.setViewName("redirect:/user/authorization.do");
		return modelAndView;
	}
	@RequestMapping("/getVerifyCode.do")
	public void getVerifyCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        HttpSession session = request.getSession();
        String verudyCode = VerifyCodeUtils.generateVerifyCode(4);
        session.setAttribute("veudyCode", verudyCode.toLowerCase());
        Integer x = 100;
        Integer y = 40;
        VerifyCodeUtils.outputImage(x, y, response.getOutputStream(), verudyCode);
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }
    @RequestMapping("/getVerifyCodeFromMail.do")
    public void getVerifyCodeFromMail(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }


}
