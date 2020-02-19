package com.advance.mistra.service;

import com.advance.mistra.common.request.PageCondition;
import com.advance.mistra.model.jpa.JpaUser;
import com.advance.mistra.model.jpa.JpaUserQueryVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Author: Mistra
 * @Version: 1.0
 * @Time: 2020/1/18 19:03
 * @Description:
 * @Copyright (c) Mistra,All Rights Reserved.
 * @Github: https://github.com/MistraR
 * @CSDN: https://blog.csdn.net/axela30w
 */
public interface JpaUserService {

    void save(JpaUser user);

    JpaUser getOne(Long id);

    void delete(Long id);

    Page<JpaUser> getPage(Pageable pageable, JpaUserQueryVo userQueryVo);

    List<JpaUser> findByCustomSqlTest1(JpaUserQueryVo userQueryVo);

    List<JpaUser> findByCustomSqlTest2(JpaUserQueryVo userQueryVo);

    JpaUser jpaName(JpaUserQueryVo userQueryVo);

    Page<JpaUser> jpaPageSelect(PageCondition condition, JpaUserQueryVo userQueryVo);

    Page<JpaUser> jpaSpecificationTest1(PageCondition condition, JpaUserQueryVo userQueryVo);

    Page<JpaUser> jpaSpecificationTest2(PageCondition condition, JpaUserQueryVo userQueryVo);

    List<JpaUser> getRelation();

    Page<JpaUser> getRelatePage();

    Page<JpaUser> tables(JpaUserQueryVo userQueryVo);
}
