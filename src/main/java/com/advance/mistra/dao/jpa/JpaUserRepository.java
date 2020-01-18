package com.advance.mistra.dao.jpa;

import com.advance.mistra.model.jpa.JpaUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author: Mistra
 * @Version: 1.0
 * @Time: 2020/1/18 19:02
 * @Description:
 * @Copyright (c) Mistra,All Rights Reserved.
 * @Github: https://github.com/MistraR
 * @CSDN: https://blog.csdn.net/axela30w
 */
public interface JpaUserRepository extends JpaRepository<JpaUser,Long>, JpaSpecificationExecutor<JpaUser> {


    JpaUser findFirstByNickName(String name);

    @Query(value = "from User where position =?1")
    List<JpaUser> selectByCustomSqlTest1(String position);

    @Query(value = "select * from jpa_user where position = ?1",nativeQuery = true)
    List<JpaUser> selectByCustomSqlTest2(String position);

    Page<JpaUser> findByNickName(String nickName, Pageable pageable);
}
