package com.zyc.controller;

import com.zyc.util.VerifyCodeUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by YuChen Zhang on 17/11/03.
 */
public class VerifyCodeController extends AbstractController{
    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        HttpSession session = request.getSession();
        String verudyCode = VerifyCodeUtils.generateVerifyCode(4);
        session.setAttribute("verifyCode", verudyCode.toLowerCase());
        Integer x = 100;
        Integer y = 40;
        VerifyCodeUtils.outputImage(x, y, response.getOutputStream(), verudyCode);
        response.getOutputStream().flush();
        response.getOutputStream().close();
        return null;
    }
}
