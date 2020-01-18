package com.advance.mistra.service.impl;

import com.advance.mistra.common.request.PageCondition;
import com.advance.mistra.dao.jpa.JpaUserRepository;
import com.advance.mistra.model.jpa.JpaUser;
import com.advance.mistra.model.jpa.JpaUserQueryVo;
import com.advance.mistra.service.JpaUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.List;

/**
 * @Author: Mistra
 * @Version: 1.0
 * @Time: 2020/1/18 19:04
 * @Description:
 * @Copyright (c) Mistra,All Rights Reserved.
 * @Github: https://github.com/MistraR
 * @CSDN: https://blog.csdn.net/axela30w
 */
@Service
public class JpaUserServiceImpl implements JpaUserService {

    @Autowired
    private JpaUserRepository jpaJpaUserRepository;

    @Override
    public void save(JpaUser user) {
        jpaJpaUserRepository.save(user);
    }

    @Override
    public JpaUser getOne(Long id) {
        return jpaJpaUserRepository.getOne(id);
    }

    @Override
    public void delete(Long id) {
        jpaJpaUserRepository.deleteById(id);
    }

    @Override
    public Page<JpaUser> getPage(Pageable pageable, JpaUserQueryVo userQueryVo) {
        return jpaJpaUserRepository.findAll(pageable);
    }

    @Override
    public List<JpaUser> findByCustomSqlTest1(JpaUserQueryVo userQueryVo) {
        return jpaJpaUserRepository.selectByCustomSqlTest1(userQueryVo.getPosition());
    }

    @Override
    public List<JpaUser> findByCustomSqlTest2(JpaUserQueryVo userQueryVo) {
        return jpaJpaUserRepository.selectByCustomSqlTest2(userQueryVo.getPosition());
    }

    @Override
    public JpaUser jpaName(JpaUserQueryVo userQueryVo) {
        return jpaJpaUserRepository.findFirstByJpaUserName(userQueryVo.getJpaUserName());
    }

    /**
     * Jpa自带的分页,排序和条件查询测试
     *
     * @param condition
     * @param userQueryVo
     * @return
     */
    @Override
    public Page<JpaUser> jpaPageSelect(PageCondition condition, JpaUserQueryVo userQueryVo) {
        PageRequest pageRequest = PageRequest.of(condition.getPageNum(), condition.getPageSize(), Sort.Direction.ASC, condition.getOrder());
        Page<JpaUser> users = jpaJpaUserRepository.findByNickName(userQueryVo.getNickName(), pageRequest);
        return users;
    }

    /**
     * JpaSpecificationExecutor条件查询接口测试1
     *
     * @param condition
     * @param userQueryVo
     * @return
     */
    @Override
    public Page<JpaUser> jpaSpecificationTest1(PageCondition condition, JpaUserQueryVo userQueryVo) {
        PageRequest pageRequest = PageRequest.of(condition.getPageNum(), condition.getPageSize(), Sort.Direction.DESC, condition.getOrder());
        Page<JpaUser> page = jpaJpaUserRepository.findAll(new Specification<JpaUser>() {
            @Override
            public javax.persistence.criteria.Predicate toPredicate(Root<JpaUser> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Path<String> userName = root.get("userName");
                Path<String> position = root.get("position");
                //这里可以设置任意条查询条件
                query.where(cb.like(userName, "%" + userQueryVo.getJpaUserName() + "%"), cb.like(position, "%" + userQueryVo.getPosition() + "%"));
                //这种方式使用JPA的API设置了查询条件，所以不需要再返回查询条件Predicate给Spring Data Jpa，故最后return null;即可。
                return null;
            }
        }, pageRequest);
        return page;
    }

    /**
     * JpaSpecificationExecutor条件查询接口测试2
     *
     * @param condition
     * @param userQueryVo
     * @return
     */
    @Override
    public Page<JpaUser> jpaSpecificationTest2(PageCondition condition, JpaUserQueryVo userQueryVo) {
        PageRequest pageRequest = PageRequest.of(condition.getPageNum(), condition.getPageSize(), Sort.Direction.DESC, condition.getOrder());
        Page<JpaUser> page = jpaJpaUserRepository.findAll(new Specification<JpaUser>() {
            @Override
            public javax.persistence.criteria.Predicate toPredicate(Root<JpaUser> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate1 = cb.like(root.get("userName"),"%" + userQueryVo.getJpaUserName() + "%");
                Predicate predicate2 = cb.like(root.get("position"),"%" + userQueryVo.getPosition() + "%");
                query.where(cb.or(predicate1,predicate2));
                //等于:query.where(cb.or(cb.like(root.get("userName"),"%" + userQueryVo.getJpaUserName() + "%"),cb.like(root.get("position"),"%" + userQueryVo.getPosition() + "%")));
                return null;
            }
        }, pageRequest);
        return page;
    }

    @Override
    public List<JpaUser> getRelation() {
        return jpaJpaUserRepository.findAll();
    }

    @Override
    public Page<JpaUser> getRelatePage() {
//        Predicate p1 = cb.like(root.get("name").as(String.class), "%"+um.getName()+"%");  
//        Predicate p2 = cb.equal(root.get("uuid").as(Integer.class), um.getUuid());  
//        Predicate p3 = cb.gt(root.get("age").as(Integer.class), um.getAge());  
//        //把Predicate应用到CriteriaQuery中去,因为还可以给CriteriaQuery添加其他的功能，比如排序、分组啥的  
//        query.where(cb.and(p3,cb.or(p1,p2)));  
//        //添加排序的功能  
//        query.orderBy(cb.desc(root.get("uuid").as(Integer.class)));  
//        return query.getRestriction();  

        Specification<JpaUser> specification= new Specification<JpaUser>(){
            @Override
            public Predicate toPredicate(Root<JpaUser> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.like(root.get("userName").as(String.class),"%索%");
                criteriaQuery.where(predicate);
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id").as(Integer.class)));
                return criteriaQuery.getRestriction();
            }
        };
        PageRequest pageRequest = new PageRequest(0,10);
        return jpaJpaUserRepository.findAll(specification,pageRequest);
    }
}
