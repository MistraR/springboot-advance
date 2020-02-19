package com.advance.mistra.dao.jpa;

import com.advance.mistra.model.jpa.JpaPermission;
import com.advance.mistra.model.jpa.JpaRole;
import com.advance.mistra.model.jpa.JpaUser;
import com.advance.mistra.model.jpa.JpaUserQueryVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: Mistra
 * @Version: 1.0
 * @Time: 2020/2/19 22:01
 * @Description: Jpa多表查询条件构造类
 * @Copyright (c) Mistra,All Rights Reserved.
 * @Github: https://github.com/MistraR
 * @CSDN: https://blog.csdn.net/axela30w
 */
public class JpaUserSpecification implements Specification<JpaUser> {

    private JpaUserQueryVo param;

    public JpaUserSpecification(JpaUserQueryVo jpaUserQueryVo) {
        this.param = jpaUserQueryVo;
    }

    @Override
    public Specification<JpaUser> and(Specification<JpaUser> other) {
        return null;
    }

    @Override
    public Specification<JpaUser> or(Specification<JpaUser> other) {
        return null;
    }

    @Override
    public Predicate toPredicate(Root<JpaUser> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<Predicate>();
        /**
         * in查询
         */
        if (StringUtils.isNotEmpty(param.getIds())) {
            CriteriaBuilder.In in = criteriaBuilder.in(root.get("id").as(Long.class));
            List<String> ids = Arrays.asList(param.getIds().split(","));
            ids.forEach(s -> in.value(Long.parseLong(s)));
            predicates.add(criteriaBuilder.and(in));
        }
        /**
         * like查询
         */
        if (StringUtils.isNotEmpty(param.getUserName())) {
            predicates.add(criteriaBuilder.like(root.get("userName"), "%" + param.getUserName() + "%"));
        }
        /**
         * 全等 = 查询
         */
        if (StringUtils.isNotEmpty(param.getNickName())) {
            predicates.add(criteriaBuilder.equal(root.get("nickName"), param.getNickName()));
        }
        /**
         * 大于小于等等
         */
        if (param.getAge() != null) {
            predicates.add(criteriaBuilder.le(root.get("age"), param.getAge()));
            predicates.add(criteriaBuilder.gt(root.get("age"), param.getAge() - 10));
        }
        if (StringUtils.isNotEmpty(param.getPosition())) {
            predicates.add(criteriaBuilder.equal(root.get("position"), param.getPosition()));
        }
        /**
         * 2张表
         */
        if (StringUtils.isNotEmpty(param.getRoleName())) {
            Join<JpaRole, JpaUser> join = root.join("role", JoinType.LEFT);
            predicates.add(criteriaBuilder.equal(join.get("roleName"), param.getRoleName()));
        }
        /**
         * 跨表查询，3个表
         * 需要通过权限名称查出对应的用户，JpaUser.role.permission = param.getPermissionName()
         */
        if (StringUtils.isNotEmpty(param.getPermissionName())) {
            Join<JpaPermission, JpaUser> join = root.join("role", JoinType.LEFT);
            predicates.add(criteriaBuilder.equal(join.get("permission").get("permissionName"), param.getPermissionName()));
        }
        return predicates.isEmpty() ? criteriaBuilder.conjunction() : criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}
