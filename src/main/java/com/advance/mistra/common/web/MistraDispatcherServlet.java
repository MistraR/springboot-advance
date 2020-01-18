package com.advance.mistra.common.web;

import com.advance.mistra.common.SystemConstans;
import com.advance.mistra.common.exception.BusinessErrorCode;
import com.advance.mistra.common.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.SimpleLocaleContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * @Author: Mistra
 * @Version: 1.0
 * @Time: 2020/1/18 19:44
 * @Description:
 * @Copyright (c) Mistra,All Rights Reserved.
 * @Github: https://github.com/MistraR
 * @CSDN: https://blog.csdn.net/axela30w
 */
@Slf4j
public class MistraDispatcherServlet extends DispatcherServlet {

    private static final long serialVersionUID = -496289734540025913L;

    private static final String ZH_CN = "zh";

    public MistraDispatcherServlet(AnnotationConfigWebApplicationContext applicationContext) {
        super(applicationContext);
    }

    @Override
    protected void noHandlerFound(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("request uri not found >>>> " + request.getRequestURL());
        if (HttpServletHelper.isAjax(request)) {
            response(response, BusinessErrorCode.REQUEST_NO_HANDLER_FOUND, "请求资源不存在,请确认相关资源!");
            return;
        }
        response.sendRedirect(request.getContextPath() + "/static/404.html");
    }

    /**
     * 根据请求头参数"lan"设置语言本地化对象,可以选择用 Accept-Language
     *
     * @param request
     * @return org.springframework.context.i18n.LocaleContext
     * @author Mistra
     * @date 2020/1/18 20:09
     */
    @Override
    public LocaleContext buildLocaleContext(HttpServletRequest request) {
        String language = request.getHeader(SystemConstans.HEADER_LANGUAGE_FLAG);
        LocaleContext localeContext = new SimpleLocaleContext(new Locale("zh", "CN"));
        if (!StringUtils.isEmpty(language)) {
            return language.startsWith(ZH_CN) ? localeContext : new SimpleLocaleContext(new Locale("en", "US"));
        } else {
            return localeContext;
        }
    }

    /**
     * 提示失败信息
     *
     * @param response
     * @param code
     * @param message
     * @return void
     * @author Mistra
     * @date 2020/1/18 20:09
     */
    public void response(HttpServletResponse response, int code, String message) {
        HttpServletHelper.build(response, new ResponseResult(false, code, message).toJson());
    }
}
