package com.advance.mistra.service.impl;

import com.advance.mistra.dao.mapper.ExcelModelMapper;
import com.advance.mistra.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
@Service
public class ExcelServiceImpl implements ExcelService {

    @Autowired
    private ExcelModelMapper excelModelDao;

    @Override
    public void excelExport(HttpServletResponse httpServletResponse) {

    }

    @Override
    public void excelImport(MultipartFile multipartFile) {

    }
}
