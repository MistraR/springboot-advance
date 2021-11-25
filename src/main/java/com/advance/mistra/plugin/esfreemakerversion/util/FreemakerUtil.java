package com.advance.mistra.plugin.esfreemakerversion.util;

import freemarker.core.ParseException;
import freemarker.template.*;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import static com.advance.mistra.common.SystemConstans.UTF8;

/**
 * @author Mistra
 * @ Version: 1.0
 * @ Time: 2020/3/15 17:13
 * @ Description:
 * @ Copyright (c) Mistra,All Rights Reserved.
 * @ Github: https://github.com/MistraR
 * @ CSDN: https://blog.csdn.net/axela30w
 */
public class FreemakerUtil {

    private static final Logger logger = LoggerFactory.getLogger(FreemakerUtil.class);

    /**
     * 根据查询模板生成es查询参数
     *
     * @param data
     * @param esTemplatePath
     * @return
     */
    public static String generateEsJson(Map<String, Object> data, String esTemplatePath) {
        Configuration Configuration = new Configuration();
        Configuration.setDefaultEncoding(UTF8);
        Configuration.setClassForTemplateLoading(FreemakerUtil.class, FilenameUtils.getFullPathNoEndSeparator(esTemplatePath));
        StringWriter stringWriter = new StringWriter();
        try {
            Template template = Configuration.getTemplate(FilenameUtils.getName(esTemplatePath));
            template.process(data, stringWriter);
            stringWriter.close();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (MalformedTemplateNameException e) {
            e.printStackTrace();
        } catch (TemplateNotFoundException | TemplateException e) {
            logger.error(e.getMessage());
            throw new RuntimeException("Freemarker json TemplateException");
        } catch (IOException e) {
            throw new RuntimeException("Freemarker url IOException");
        }
        return stringWriter.toString();
    }
}
