package com.zyc.exception;

import javax.security.auth.login.AccountException;
import java.io.Serializable;

/**
 * Created by YuChen Zhang on 17/11/09.
 */
public class MyLoginException  extends AccountException implements Serializable {
    private static final long serialVersionUID = -6699752791525619208L;

    public MyLoginException() {
        super();
    }

    public MyLoginException(final String message) {
        super(message);
    }
}
