package com.zyc.controller;

import com.zyc.model.User;
import com.zyc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

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
        if(request.getRequestURI().contains("findByName")){
            this.findByName(request,response);
        }else if(request.getRequestURI().contains("findByEmail")){
            this.findByEmail(request,response);
        }
        return null;
    }

    public ModelAndView findByName(HttpServletRequest request,HttpServletResponse response){
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
    public ModelAndView findByEmail(HttpServletRequest request,HttpServletResponse response){
        List<User> result = userService.findUserByEmail(request.getParameter("useremail"));
        User user = null;
        PrintWriter out = null;
        if(!result.isEmpty()){
            user=result.get(0);
        }
        try {
            out = new PrintWriter(response.getOutputStream());
            if(user!=null){
                out.print("error");
            }else{
                out.print("ok");
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            out.close();
        }
        return null;
    }
}
