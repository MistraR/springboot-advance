package com.advance.mistra.controller;

import com.advance.mistra.common.request.PageCondition;
import com.advance.mistra.common.request.annotation.AddUrl;
import com.advance.mistra.common.request.annotation.DeleteUrl;
import com.advance.mistra.common.request.annotation.FindOneUrl;
import com.advance.mistra.common.request.annotation.PageUrl;
import com.advance.mistra.model.jpa.JpaUser;
import com.advance.mistra.model.jpa.JpaUserQueryVo;
import com.advance.mistra.service.JpaUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: Mistra
 * @Version: 1.0
 * @Time: 2020/1/18 19:04
 * @Description: Jpa
 * @Copyright (c) Mistra,All Rights Reserved.
 * @Github: https://github.com/MistraR
 * @CSDN: https://blog.csdn.net/axela30w
 */
@RestController
@RequestMapping("/jpa")
@Api("JpaUserController")
public class JpaUserController {

    @Autowired
    private JpaUserService jpaUserService;

    @ApiOperation("新增")
    @AddUrl
    public void save(JpaUser user){
        jpaUserService.save(user);
    }

    @ApiOperation("查询单条")
    @FindOneUrl
    public JpaUser getOne(Long id){
        return jpaUserService.getOne(id);
    }

    @ApiOperation("删除")
    @DeleteUrl
    public void delete(Long id){
        jpaUserService.delete(id);
    }

    @ApiOperation("分页查询2-Jpa自带的查询辅助类")
    @PageUrl
    public Page<JpaUser> getPage(Pageable pageable, JpaUserQueryVo userQueryVo){
        return jpaUserService.getPage(pageable,userQueryVo);
    }

    @ApiOperation("自定义sql测试1")
    @GetMapping("/customSql1")
    public List<JpaUser> customSql1(JpaUserQueryVo userQueryVo){
        return jpaUserService.findByCustomSqlTest1(userQueryVo);
    }

    @ApiOperation("自定义sql测试2")
    @GetMapping("/customSql2")
    public List<JpaUser> customSql2(JpaUserQueryVo userQueryVo){
        return jpaUserService.findByCustomSqlTest2(userQueryVo);
    }

    @ApiOperation("Jpa命名规范接口测试")
    @GetMapping("/jpaName")
    public JpaUser jpaName(JpaUserQueryVo userQueryVo){
        return jpaUserService.jpaName(userQueryVo);
    }

    @ApiOperation("Jpa自带的分页,排序和条件查询测试")
    @GetMapping("/jpaPageSelect")
    public Page<JpaUser> jpaPageSelect(PageCondition condition, JpaUserQueryVo userQueryVo){
        return jpaUserService.jpaPageSelect(condition,userQueryVo);
    }

    @ApiOperation("JpaSpecificationExecutor条件查询接口测试1")
    @GetMapping("/jpaSpecification1")
    public Page<JpaUser> jpaSpecificationTest1(PageCondition condition,JpaUserQueryVo userQueryVo){
        return jpaUserService.jpaSpecificationTest1(condition,userQueryVo);
    }

    @ApiOperation("JpaSpecificationExecutor条件查询接口测试2")
    @GetMapping("/jpaSpecification2")
    public Page<JpaUser> jpaSpecificationTest2(PageCondition condition,JpaUserQueryVo userQueryVo){
        return jpaUserService.jpaSpecificationTest2(condition,userQueryVo);
    }

    @ApiOperation("关联查询")
    @GetMapping("/getRelation")
    public List<JpaUser> getRelation(){
        return jpaUserService.getRelation();
    }

    @ApiOperation("关联查询-分页")
    @GetMapping("/getRelatePage")
    public Page<JpaUser> getRelatePage(){
        return jpaUserService.getRelatePage();
    }
}
