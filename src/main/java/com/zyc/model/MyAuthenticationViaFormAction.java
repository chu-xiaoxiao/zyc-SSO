package com.zyc.model;

import com.zyc.exception.MyLoginException;
import org.jasig.cas.authentication.Credential;
import org.jasig.cas.web.flow.AuthenticationViaFormAction;
import org.jasig.cas.web.support.WebUtils;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.util.StringUtils;
import org.springframework.webflow.execution.RequestContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by YuChen Zhang on 17/11/03.
 */
public class MyAuthenticationViaFormAction extends AuthenticationViaFormAction{

    public final String validatorCaptcha(final RequestContext context, final MyUsernamePasswordCredential myUsernamePasswordCredential,
                                         final MessageContext messageContext) throws Exception {
        final HttpServletRequest request = WebUtils.getHttpServletRequest(context);
        HttpSession session = request.getSession();
        String verifyCode = (String)session.getAttribute("verifyCode");
        session.removeAttribute("verifyCode");

        MyUsernamePasswordCredential upc = (MyUsernamePasswordCredential)myUsernamePasswordCredential;
        String submitAuthcodeCaptcha =upc.getVerifyCode();


        if(!StringUtils.hasText(verifyCode) || !StringUtils.hasText(submitAuthcodeCaptcha)){
            messageContext.addMessage(new MessageBuilder().error().code("required.verifyCode").build());
            return "error";
        }
        if(verifyCode.toLowerCase().equals(submitAuthcodeCaptcha.toLowerCase())){
            return "success";
        }
        messageContext.addMessage(new MessageBuilder().error().code("error.authentication.verifyCode.bad").build());
        return "error";
    }
}
