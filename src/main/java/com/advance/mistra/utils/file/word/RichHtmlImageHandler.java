package com.advance.mistra.utils.file.word;

import org.apache.commons.codec.binary.Base64;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * @author Mistra
 * @ Version: 1.0
 * @ Time: 2020/4/7 23:00
 * @ Description: 文档图片转换器
 * @ Copyright (c) Mistra,All Rights Reserved.参考博客：https://blog.csdn.net/yz357823669/article/details/80840855
 * @ Github: https://github.com/MistraR
 * @ CSDN: https://blog.csdn.net/axela30w
 */
public class RichHtmlImageHandler {

    /**
     * 将图片转换成base64编码的字符串
     *
     * @param imageSrc 文件路径
     * @return
     * @throws IOException
     */
    public static String imageToBase64(String imageSrc) throws IOException {
        File file = new File(imageSrc);
        if (!file.exists()) {
            throw new FileNotFoundException("文件不存在！");
        }
        StringBuilder pictureBuffer = new StringBuilder();
        FileInputStream input = new FileInputStream(file);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] temp = new byte[1024];
        for (int len = input.read(temp); len != -1; len = input.read(temp)) {
            out.write(temp, 0, len);
        }
        pictureBuffer.append(new String(Base64.encodeBase64Chunked(out.toByteArray())));
        input.close();
        return pictureBuffer.toString();
    }

    /**
     * 得到文件的word mht的body块
     *
     * @param imageFilePath
     * @param imageFielShortName
     * @param imageHeight
     * @param imageWidth
     * @param imageStyle
     * @param srcLocationShortName
     * @param shapeidPrex
     * @param spidPrex
     * @param typeid
     * @return
     */
    public static String toDocBodyBlock(String imageFilePath, String imageFielShortName, int imageHeight, int imageWidth,
                                        String imageStyle, String srcLocationShortName, String shapeidPrex, String spidPrex, String typeid) {
        // mht文件中针对shapeid的生成好像规律，其内置的生成函数没法得知，但是只要保证其唯一就行
        // 这里用前置加32位的uuid来保证其唯一性。
        String shapeid = shapeidPrex;
        shapeid += UUID.randomUUID().toString();
        //spid ,同shapeid处理
        String spid = spidPrex;
        spid += UUID.randomUUID().toString();
        StringBuilder sb1 = new StringBuilder();
        sb1.append(" <!--[if gte vml 1]>");
        sb1.append("<v:shape id=3D\"" + shapeid + "\"");
        sb1.append("\n");
        sb1.append(" o:spid=3D\"" + spid + "\"");
        sb1.append(" type=3D\"" + typeid + "\" alt=3D\"" + imageFielShortName + "\"");
        sb1.append("\n");
        sb1.append(" style=3D' " + generateImageBodyBlockStyleAttr(imageFilePath, imageHeight, imageWidth) + imageStyle + "'");
        sb1.append(">");
        sb1.append("\n");
        sb1.append(" <v:imagedata src=3D\"" + srcLocationShortName + "\"");
        sb1.append("\n");
        sb1.append(" o:title=3D\"" + imageFielShortName.split("\\.")[0] + "\"");
        sb1.append("/>");
        sb1.append("</v:shape>");
        sb1.append("<![endif]-->");
        return sb1.toString();
    }

    /**
     * 生成图片的base4块
     *
     * @param nextPartId
     * @param contextLoacation
     * @param fileTypeName
     * @param base64Content
     * @return
     */
    public static String generateImageBase64Block(String nextPartId, String contextLoacation,
                                                  String fileTypeName, String base64Content) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("\n");
        sb.append("------=_NextPart_" + nextPartId);
        sb.append("\n");
        sb.append("Content-Location: " + contextLoacation);
        sb.append("\n");
        sb.append("Content-Transfer-Encoding: base64");
        sb.append("\n");
        sb.append("Content-Type: " + getImageContentType(fileTypeName));
        sb.append("\n");
        sb.append("\n");
        sb.append(base64Content);
        return sb.toString();
    }

    private static String generateImageBodyBlockStyleAttr(String imageFilePath, int height, int width) {
        StringBuilder sb = new StringBuilder();
        BufferedImage sourceImg;
        try {
            sourceImg = ImageIO.read(new FileInputStream(imageFilePath));
            if (height == 0) {
                height = sourceImg.getHeight();
            }
            if (width == 0) {
                width = sourceImg.getWidth();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //将像素转化成pt
        BigDecimal heightValue = new BigDecimal(height * 12 / 16);
        heightValue = heightValue.setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal widthValue = new BigDecimal(width * 12 / 16);
        widthValue = widthValue.setScale(2, BigDecimal.ROUND_HALF_UP);
        sb.append("height:" + heightValue + "pt;");
        sb.append("width:" + widthValue + "pt;");
        sb.append("visibility:visible;");
        sb.append("mso-wrap-style:square; ");
        return sb.toString();
    }

    private static String getImageContentType(String fileTypeName) {
        String result = "image/jpeg";
        if (fileTypeName.equals("tif") || fileTypeName.equals("tiff")) {
            result = "image/tiff";
        } else if (fileTypeName.equals("fax")) {
            result = "image/fax";
        } else if (fileTypeName.equals("gif")) {
            result = "image/gif";
        } else if (fileTypeName.equals("ico")) {
            result = "image/x-icon";
        } else if (fileTypeName.equals("jfif") || fileTypeName.equals("jpe")
                || fileTypeName.equals("jpeg") || fileTypeName.equals("jpg")) {
            result = "image/jpeg";
        } else if (fileTypeName.equals("net")) {
            result = "image/pnetvue";
        } else if (fileTypeName.equals("png") || fileTypeName.equals("bmp")) {
            result = "image/png";
        } else if (fileTypeName.equals("rp")) {
            result = "image/vnd.rn-realpix";
        } else if (fileTypeName.equals("rp")) {
            result = "image/vnd.rn-realpix";
        }
        return result;
    }

    public static String getFileSuffix(String srcRealPath) {
        return srcRealPath.substring(srcRealPath.indexOf(".") + 1);
    }
}
