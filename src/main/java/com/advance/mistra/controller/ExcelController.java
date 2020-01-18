package com.advance.mistra.controller;

import com.advance.mistra.common.response.annotation.MistraResponse;
import com.advance.mistra.service.ExcelService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author: Mistra
 * @Version: 1.0
 * @Time: 2020/1/18 13:04
 * @Description: Excel
 * @Copyright (c) Mistra,All Rights Reserved.
 * @Github: https://github.com/MistraR
 * @CSDN: https://blog.csdn.net/axela30w
 */
@RestController
@RequestMapping("/excel")
public class ExcelController {

    @Autowired
    private ExcelService excelService;

    @ApiOperation("导入")
    @PostMapping("/import")
    @MistraResponse
    public void excelImport(MultipartFile multipartFile) {
        excelService.excelImport(multipartFile);
    }

    @ApiOperation("导出")
    @PostMapping("/export")
    @MistraResponse
    public void excelExport(HttpServletResponse httpServletResponse) {
        excelService.excelExport(httpServletResponse);
    }

}

