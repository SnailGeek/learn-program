package com.lagou.edu.dao;

import com.lagou.edu.pojo.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ResumeDao extends JpaRepository<Resume, Long>, JpaSpecificationExecutor<Resume> {
    @Query("from Resume where id = ?1")
    Resume findByJpql(Long id);

    @Query("from Resume  where id = ?1 and name = ?2")
    List<Resume> findByJpqlParams(Long id, String name);


    @Query(value = "select *  from tb_resume where name like ?1 and address like ?2", nativeQuery = true)
    Resume findBySql(String name, String address);

    List<Resume> findByNameLikeAndAddress(String name, String address);



}
