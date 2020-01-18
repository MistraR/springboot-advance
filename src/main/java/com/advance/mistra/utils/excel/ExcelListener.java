package com.advance.mistra.utils.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Mistra
 * @Version: 1.0
 * @Time: 2020/1/18 12:47
 * @Description:
 * @Copyright (c) Mistra,All Rights Reserved.
 * @Github: https://github.com/MistraR
 * @CSDN: https://blog.csdn.net/axela30w
 */
public class ExcelListener extends AnalysisEventListener {

    private List<Object> datas = new ArrayList<>();

    @Override
    public void invoke(Object o, AnalysisContext analysisContext) {

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
