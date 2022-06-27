package com.omrixyz.dfmh.handlers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;

import javax.servlet.Filter;

public class ResponseFilter extends SecurityContextPersistenceFilter implements Filter {

    private static final Log log = LogFactory
            .getLog(ResponseFilter.class);

    public static final String USERNAME_KEY = "username";

    public ResponseFilter() {
        System.out.println("Inside constructor of ResponseFilter");
    }
}