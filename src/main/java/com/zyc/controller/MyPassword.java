package com.zyc.controller;

import com.zyc.mapper.UserMapper;
import com.zyc.model.User;
import com.zyc.model.UserExample;
import com.zyc.util.EncodeMD5;
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
        String password = EncodeMD5.encodeMD5(transformedCredential.getPassword());
        //认证用户名和密码是否正确
        if(this.verifyAccount(username, password)){
            return createHandlerResult(transformedCredential, new SimplePrincipal(username), null);
        }
        throw new FailedLoginException();
    }

    public boolean verifyAccount(String username, String plainPassword){
        UserExample userExample = new UserExample();
        userExample.getOredCriteria().add(userExample.createCriteria().andUsernameEqualTo(username).andUserpasswordEqualTo(plainPassword));
        List<User> result = userMapper.selectByExample(userExample);
        if(result.size()>0){
            return true;
        }else{
            return false;
        }
    }

}
