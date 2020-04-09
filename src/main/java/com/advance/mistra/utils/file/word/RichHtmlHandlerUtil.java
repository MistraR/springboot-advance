package com.advance.mistra.utils.file.word;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Mistra
 * @ Version: 1.0
 * @ Time: 2020/4/7 22:04
 * @ Description: 导出Word，Word包含富文本，处理工具类，参考博客：https://blog.csdn.net/yz357823669/article/details/80840855
 * @ Copyright (c) Mistra,All Rights Reserved.
 * @ Github: https://github.com/MistraR
 * @ CSDN: https://blog.csdn.net/axela30w
 */
@Data
public class RichHtmlHandlerUtil {

    private Document doc = null;
    private String html;
    private String docSrcParent = "";
    private String docSrcLocationPrex = "";
    private String nextPartId;
    private String shapeidPrex;
    private String spidPrex;
    private String typeid;
    private String handledDocBodyBlock;
    private List<String> docBase64BlockResults = new ArrayList<String>();
    private List<String> xmlImgRefs = new ArrayList<String>();

    public RichHtmlHandlerUtil(String html) {
        this.html = html;
        doc = Jsoup.parse(wrapHtml(this.html));
    }

    public String getHandledDocBodyBlock() {
        String raw = WordHtmlGenerator.string2Ascii(doc.getElementsByTag("body").html());
        return raw.replace("=3D", "=").replace("=", "=3D");
    }

    public String getRawHandledDocBodyBlock() {
        String raw = doc.getElementsByTag("body").html();
        return raw.replace("=3D", "=").replace("=", "=3D");
    }

    /**
     * 富文本数据入库的时候一般不是完成的html，需要加上html和body标签
     *
     * @param html
     * @return
     */
    private String wrapHtml(String html) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append("<body>");
        sb.append(html);
        sb.append("</body>");
        sb.append("</html>");
        return sb.toString();
    }

    /**
     * 处理富文本HTML文件
     *
     * @throws IOException
     */
    public void handledHtml() throws IOException {
        Elements imags = doc.getElementsByTag("img");
        System.out.println("原始的doc:\n" + doc);
        if (imags == null || imags.size() == 0) {
            return;
        }
        // 转换成mht能识别的图片标签内容，去替换html中的图片标签
        for (Element item : imags) {
            // 把图片文件地址取出来
            String srcRealPath = item.attr("src");
            File imageFile = new File(srcRealPath);
            String imageFielName = imageFile.getName();
            String fileSuffix = RichHtmlImageHandler.getFileSuffix(srcRealPath);
            String docFileName = "image" + UUID.randomUUID().toString() + "." + fileSuffix;
            String srcLocationShortName = docSrcParent + "/" + docFileName;
            String styleAttr = item.attr("style");
            String imagHeightStr = item.attr("height");
            if (StringUtils.isEmpty(imagHeightStr)) {
                imagHeightStr = getStyleAttrValue(styleAttr, "height");
            }
            String imagWidthStr = item.attr("width");
            if (StringUtils.isEmpty(imagHeightStr)) {
                imagHeightStr = getStyleAttrValue(styleAttr, "width");
            }
            imagHeightStr = imagHeightStr.replace("px", "");
            imagWidthStr = imagWidthStr.replace("px", "");
            if (StringUtils.isEmpty(imagHeightStr)) {
                // 去得到默认的文件高度
                imagHeightStr = "0";
            }
            if (StringUtils.isEmpty(imagWidthStr)) {
                imagWidthStr = "0";
            }
            int imageHeight = Integer.parseInt(imagHeightStr);
            int imageWidth = Integer.parseInt(imagWidthStr);
            // 得到文件的word mht的body块
            String handledDocBodyBlock = RichHtmlImageHandler.toDocBodyBlock(srcRealPath,
                    imageFielName, imageHeight, imageWidth, styleAttr,
                    srcLocationShortName, shapeidPrex, spidPrex, typeid);
            // 这里的顺序有点问题：应该是替换item，而不是整个后面追加
            // doc.rreplaceAll(item.toString(), handledDocBodyBlock);
            item.after(handledDocBodyBlock);
            // item.parent().append(handledDocBodyBlock);
            item.remove();
            // 去替换原生的html中的imag
            String base64Content = RichHtmlImageHandler.imageToBase64(srcRealPath);
            String contextLoacation = docSrcLocationPrex + "/" + docSrcParent + "/" + docFileName;
            String docBase64BlockResult = RichHtmlImageHandler.generateImageBase64Block(nextPartId, contextLoacation,
                    fileSuffix, base64Content);
            docBase64BlockResults.add(docBase64BlockResult);
            String imagXMLHref = "<o:File HRef=3D\"" + docFileName + "\"/>";
            xmlImgRefs.add(imagXMLHref);
        }
    }

    private String getStyleAttrValue(String style, String attributeKey) {
        if (StringUtils.isEmpty(style)) {
            return "";
        }
        // 以";"分割
        String[] styleAttrValues = style.split(";");
        for (String item : styleAttrValues) {
            // 在以 ":"分割
            String[] keyValuePairs = item.split(":");
            if (attributeKey.equals(keyValuePairs[0])) {
                return keyValuePairs[1];
            }
        }
        return "";
    }

}
