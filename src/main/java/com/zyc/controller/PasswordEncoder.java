package com.zyc.controller;

import com.zyc.util.EncodeMD5;

/**
 * Created by YuChen Zhang on 17/11/01.
 */
public class PasswordEncoder implements org.jasig.cas.authentication.handler.PasswordEncoder {
    @Override
    public String encode(String password) {
        return EncodeMD5.encodeMD5(password);
    }
}
