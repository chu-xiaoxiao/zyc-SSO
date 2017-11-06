package com.zyc.model;

import com.sun.istack.NotNull;
import org.jasig.cas.authentication.UsernamePasswordCredential;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by YuChen Zhang on 17/11/03.
 */
public class MyUsernamePasswordCredential extends UsernamePasswordCredential {
    @NotNull
    @Size(min=1,message = "required.verifyCode")
    private String verifyCode;


    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
}
