package com.zyc.controller;

import com.zyc.model.User;
import com.zyc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by YuChen Zhang on 17/11/03.
 */
@Component("findByNameController")
public class FindByNameController extends AbstractController{
    @Autowired
    @Qualifier("userServiceImplement")
    private UserService userService;


    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = userService.findByName(request.getParameter("username"));
        try {
            PrintWriter out = new PrintWriter(response.getOutputStream());
            if(user!=null){
                out.print("error");
            }else{
                out.print("ok");
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{

        }
        return null;
    }
}
