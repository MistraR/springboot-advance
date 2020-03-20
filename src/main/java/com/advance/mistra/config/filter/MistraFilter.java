package com.advance.mistra.config.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author Mistra
 * @ Version: 1.0
 * @ Time: 2020/3/20 23:15
 * @ Description:
 * @ Copyright (c) Mistra,All Rights Reserved.
 * @ Github: https://github.com/MistraR
 * @ CSDN: https://blog.csdn.net/axela30w
 */
@Slf4j
public class MistraFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info(">>>>>>>>>>>>>>>>>>>>>> MistraFilter过滤器初始化完成！");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info(">>>>>>>>>>>>>>>>>>>>>> {}请求进入MistraFilter过滤器 <<<<<<<<<<<<<<<<<<<<<<", ((HttpServletRequest) servletRequest).getRequestURI());
        // 进行参数过滤，身份验证等等操作

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        log.info("MistraFilter过滤器已销毁！");
    }
}
