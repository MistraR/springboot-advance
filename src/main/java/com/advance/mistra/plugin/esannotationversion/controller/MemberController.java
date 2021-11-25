package com.advance.mistra.plugin.esannotationversion.controller;

import java.util.Map;

import com.advance.mistra.common.response.ResponseResult;
import com.advance.mistra.plugin.esannotationversion.service.MemberDocumentIndexService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * MemberController
 *
 * @author mistra@future.com
 * @date 2021/11/25
 */
@RestController
@RequestMapping("/member")
public class MemberController {

    private final MemberDocumentIndexService memberDocumentIndexService;

    public MemberController(MemberDocumentIndexService memberDocumentIndexService) {
        this.memberDocumentIndexService = memberDocumentIndexService;
    }

    /**
     * 保存索引
     *
     * @param params 数据
     * @return ResponseResult
     */
    @PostMapping("/index")
    public ResponseResult index(@RequestBody Map<String, Object> params) {
        memberDocumentIndexService.index(params);
        return ResponseResult.buildSuccess();
    }

    /**
     * 搜索
     *
     * @param params 查询参数
     * @return 搜索结果
     */
    @PostMapping("/search")
    public ResponseResult search(@RequestBody Map<String, String> params) {
        return ResponseResult.buildSuccess(memberDocumentIndexService.search(params));
    }
}
