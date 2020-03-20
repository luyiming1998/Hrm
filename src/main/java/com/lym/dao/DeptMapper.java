package com.lym.dao;

import com.lym.entity.Dept;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeptMapper {
    List<Dept> selectAllDept(@Param("dept") Dept dept, @Param("start") Integer start);

    Dept selectUpdateDept(Integer id);

    int totalcountDept(Dept dept);

    int addDept(Dept dept);

    int updateDept(Dept dept);

    int delDept(Integer id);

    List<Dept> selectAll();

    int checkDeptName(String name);
}
