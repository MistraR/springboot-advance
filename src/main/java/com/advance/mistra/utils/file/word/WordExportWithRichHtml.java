package com.advance.mistra.utils.file.word;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mistra
 * @ Version: 1.0
 * @ Time: 2020/4/7 23:09
 * @ Description: word导出帮助类 通过freemarker模板引擎来实现。参考博客：https://blog.csdn.net/yz357823669/article/details/80840855
 * @ Copyright (c) Mistra,All Rights Reserved.
 * @ Github: https://github.com/MistraR
 * @ CSDN: https://blog.csdn.net/axela30w
 */
public class WordExportWithRichHtml {

    private static Configuration configuration = null;

    static {
        configuration = new Configuration(Configuration.VERSION_2_3_23);
        configuration.setDefaultEncoding("utf-8");
        configuration.setClassicCompatible(true);
        configuration.setClassForTemplateLoading(
                WordExportWithRichHtml.class,
                "C:\\Users\\Administrator\\IdeaProjects\\springboot-advance\\src\\main\\java\\com\\advance\\mistra\\utils\\file\\word\\ftl");
    }

    public static void createDoc(Map<String, Object> dataMap, String templateName, OutputStream out) throws Exception {
        Configuration cfg = new Configuration();
        cfg.setDirectoryForTemplateLoading(new File("C:\\Users\\Administrator\\IdeaProjects\\springboot-advance\\src\\main\\java\\com\\advance\\mistra\\utils\\file\\word\\ftl"));
        Template t = cfg.getTemplate(templateName);
        WordHtmlGenerator.handleAllObject(dataMap);
        try {
            Writer w = new OutputStreamWriter(out, StandardCharsets.UTF_8);
            t.process(dataMap, w);
            w.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    public static void main(String[] args) throws Exception {
        HashMap<String, Object> data = new HashMap<String, Object>();
        StringBuilder sb = new StringBuilder();
        sb.append("<div>");
        sb.append("<img style='height:100px;width:200px;display:block;' src='C:\\Users\\Administrator\\Desktop\\test.png' />");
        sb.append("</br><span>导出Word包含富文本测试！</span>");
        sb.append("</div>");
        RichHtmlHandlerUtil handler = new RichHtmlHandlerUtil(sb.toString());
        handler.setDocSrcLocationPrex("file:///C:/8595226D");
        handler.setDocSrcParent("file3405.files");
        handler.setNextPartId("01D214BC.6A592540");
        handler.setShapeidPrex("_x56fe__x7247__x0020");
        handler.setSpidPrex("_x0000_i");
        handler.setTypeid("#_x0000_t75");
        handler.handledHtml();
        String bodyBlock = handler.getHandledDocBodyBlock();
        System.out.println("bodyBlock:\n" + bodyBlock);
        StringBuilder handledBase64Block = new StringBuilder();
        if (handler.getDocBase64BlockResults() != null && handler.getDocBase64BlockResults().size() > 0) {
            for (String item : handler.getDocBase64BlockResults()) {
                handledBase64Block.append(item).append("\n");
            }
        }
        data.put("imagesBase64String", handledBase64Block.toString());
        StringBuilder xmlimaHref = new StringBuilder();
        if (handler.getXmlImgRefs() != null && handler.getXmlImgRefs().size() > 0) {
            for (String item : handler.getXmlImgRefs()) {
                xmlimaHref.append(item).append("\n");
            }
        }
        data.put("imagesXmlHrefString", xmlimaHref.toString());
        data.put("name", "Mistra");
        data.put("content", bodyBlock);
        String docFilePath = "C:\\Users\\Administrator\\Desktop\\temp.doc";
        System.out.println(docFilePath);
        File f = new File(docFilePath);
        OutputStream out;
        try {
            out = new FileOutputStream(f);
            WordExportWithRichHtml.createDoc(data, "temp.ftl", out);
        } catch (FileNotFoundException e) {
        } catch (MalformedTemplateNameException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
