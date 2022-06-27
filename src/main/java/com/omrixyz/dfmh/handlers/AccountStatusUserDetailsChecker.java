package com.omrixyz.dfmh.handlers;

import com.omrixyz.dfmh.controllers.DenyController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AccountStatusUserDetailsChecker implements UserDetailsChecker, AuthenticationFailureHandler {

    protected final MessageSourceAccessor messages = SpringSecurityMessageSource
            .getAccessor();

    public void check(UserDetails user) {

        if (!user.isCredentialsNonExpired()) {
            throw new CredentialsExpiredException(messages.getMessage(
                    "AccountStatusUserDetailsChecker.credentialsExpired",
                    "User credentials have expired"));
        }

        if (!user.isAccountNonLocked()) {
            throw new LockedException(messages.getMessage(
                    "AccountStatusUserDetailsChecker.locked", "User account is locked"));
        }

        if (!user.isEnabled()) {
            throw new DisabledException("User is disabled");
        }

        if (!user.isAccountNonExpired()) {
            throw new AccountExpiredException(
                    messages.getMessage("AccountStatusUserDetailsChecker.expired",
                            "User account has expired"));
        }


    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String user = request.getParameter("username");
        if (exception instanceof DisabledException)
            response.sendRedirect("/auth/validation?user=" + user);
        else {
            response.sendRedirect("/auth/error");
        }
    }
}