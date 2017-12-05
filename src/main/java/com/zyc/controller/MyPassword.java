package com.zyc.controller;

import com.sun.corba.se.spi.servicecontext.UEInfoServiceContext;
import com.zyc.exception.MyLoginException;
import com.zyc.mapper.UserMapper;
import com.zyc.model.User;
import com.zyc.model.UserExample;
import com.zyc.util.EncodeMD5;
import org.hibernate.sql.Select;
import org.jasig.cas.authentication.HandlerResult;
import org.jasig.cas.authentication.PreventedException;
import org.jasig.cas.authentication.UsernamePasswordCredential;
import org.jasig.cas.authentication.handler.support.AbstractUsernamePasswordAuthenticationHandler;
import org.jasig.cas.authentication.principal.SimplePrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.security.auth.login.FailedLoginException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by YuChen Zhang on 17/10/31.
 */
@Component(value="primaryAuthenticationHandler")
public class MyPassword extends AbstractUsernamePasswordAuthenticationHandler  {
    @Autowired
    UserMapper userMapper;

    @Override
    protected HandlerResult authenticateUsernamePasswordInternal(UsernamePasswordCredential transformedCredential) throws GeneralSecurityException, PreventedException {

        //UsernamePasswordCredential参数包含了前台页面输入的用户信息
        String username = transformedCredential.getUsername();
        String pattern ="^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";        User temp = new User();
        boolean isMail = Pattern.matches(pattern,username);
        if(isMail){
            temp.setUsermail(username);
        }else{
            temp.setUsername(username);
        }
        temp.setUserpassword(transformedCredential.getPassword());
        //认证用户名和密码是否正确
        User user = null;
        if((user = this.verifyAccount(temp))!=null){
            transformedCredential.setUsername(user.getId().toString());
            return createHandlerResult(transformedCredential, new SimplePrincipal(user.getId().toString()), null);
        }
        throw new FailedLoginException();
    }
    public Integer getId(User user){
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        if(user.getUsername()!=null){
            criteria.andUsernameEqualTo(user.getUsername());
        }
        if(user.getUsermail()!=null){
            criteria.andUsermailEqualTo(user.getUsermail());
        }
        List<User>result = userMapper.selectByExample(userExample);
        if(result.size()>0){
            return result.get(0).getId();
        }else{
            return null;
        }
    }
    public User verifyAccount(User temp){
        User user = userMapper.selectByPrimaryKey(getId(temp));
        String password = EncodeMD5.encodeMD5(temp.getUserpassword(),user.getId().toString());
        if(user.getUserpassword().equals(password)){
            return user;
        }else{
            return null;
        }
    }
}
