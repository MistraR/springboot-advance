package com.advance.mistra.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author: Mistra
 * @Version: 1.0
 * @Time: 2020/1/18 12:39
 * @Description:
 * @Copyright (c) Mistra,All Rights Reserved.
 * @Github: https://github.com/MistraR
 * @CSDN: https://blog.csdn.net/axela30w
 */
public interface ExcelService {

    /**
     * 导出
     * @param httpServletResponse
     * @return void
     * @author Mistra
     * @date 2020/1/18 18:30
     */
    void excelExport(HttpServletResponse httpServletResponse);

    /**
     * 导入
     * @param multipartFile
     * @return void
     * @author Mistra
     * @date 2020/1/18 18:32
     */
    void excelImport(MultipartFile multipartFile);
}
