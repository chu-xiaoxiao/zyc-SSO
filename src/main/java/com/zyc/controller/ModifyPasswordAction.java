package com.zyc.controller;

import com.zyc.model.User;
import com.zyc.service.UserService;
import com.zyc.util.EncodeMD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import sun.misc.Request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Created by YuChen Zhang on 17/11/07.
 */
public class ModifyPasswordAction extends AbstractController{
    @Autowired
    @Qualifier("userServiceImplement")
    UserService userService;

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        return null;
    }
}
