package com.advance.mistra.service.impl;

import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import com.advance.mistra.dao.mapper.ExcelModelMapper;
import com.advance.mistra.model.ExcelModel;
import com.advance.mistra.service.ExcelService;
import com.advance.mistra.utils.excel.UploadDataListener;
import com.alibaba.excel.EasyExcel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: Mistra
 * @Version: 1.0
 * @Time: 2020/1/18 12:39
 * @Description:
 * @Copyright (c) Mistra,All Rights Reserved.
 * @Github: https://github.com/MistraR
 * @CSDN: https://blog.csdn.net/axela30w
 */
@Slf4j
@Service
public class ExcelServiceImpl implements ExcelService {

    private final int EXCEL_ROW_LINE = 5000;

    @Autowired
    private ExcelModelMapper excelModelDao;

    @Override
    public void excelExport(HttpServletResponse httpServletResponse) {

    }

    @Override
    public void excelImport(MultipartFile multipartFile) {
        UploadDataListener<ExcelModel> uploadDataListener = new UploadDataListener<>(EXCEL_ROW_LINE);
        try {
            EasyExcel.read(multipartFile.getInputStream(), ExcelModel.class, uploadDataListener).sheet()
                    .headRowNumber(1).doRead();
        } catch (IOException e) {
            log.error("Add member tag read excel file error,file name:{}", multipartFile.getName());
        }
        for (Map.Entry<Integer, ExcelModel> excelModelEntry : uploadDataListener.getMap().entrySet()) {
            excelModelDao.insert(excelModelEntry.getValue());
        }
    }
}
