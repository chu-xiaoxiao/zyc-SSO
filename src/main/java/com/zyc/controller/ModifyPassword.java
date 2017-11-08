package com.zyc.controller;

import com.zyc.model.User;
import com.zyc.service.UserService;
import com.zyc.util.EncodeMD5;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import sun.misc.Request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by YuChen Zhang on 17/11/07.
 */
public class ModifyPassword extends AbstractController{
    @Autowired
    @Qualifier("userServiceImplement")
    UserService userService;
    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        String useremail = httpServletRequest.getParameter("useremail");
        String verifCode = httpServletRequest.getParameter("verifCode");
        String sVerifCode = (String) httpServletRequest.getSession().getAttribute("verifyCode");
        List<User> result = userService.findUserByEmail(useremail);
        PrintWriter out = httpServletResponse.getWriter();
        if(result.size()==0){
            out.print("该邮箱不存在");
            return null;
        }
        if(sVerifCode==null){
            out.print("请获取验证码");
            return null;
        }
        if(verifCode==null){
            out.print("输入验证码不能为空");
            return null;
        }
        if(!sVerifCode.toLowerCase().equals(verifCode.toLowerCase())){
            out.print("验证码不正确");
            return null;
        }

        String pwd =  httpServletRequest.getParameter("upwd");
        User user = result.get(0);
        user.setUserpassword(EncodeMD5.encodeMD5(pwd));
        userService.modifyUserInfo(user);
        out.print("修改成功<input type='hidden' value = 'true'/>");
        out.close();

        return null;
    }
}
