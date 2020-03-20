package com.lym.service;

import com.lym.entity.Dept;

import java.util.List;

public interface DeptService {

    List<Dept> selectAllDept(Dept dept, Integer start);

    Dept selectUpdateDept(Integer id);

    public int totalcountDept(Dept dept);

    int addDept(Dept dept);

    int updateDept(Dept dept);

    int delDept(Integer id);

    List<Dept> selectAll();

    int checkDeptName(String name);
}
