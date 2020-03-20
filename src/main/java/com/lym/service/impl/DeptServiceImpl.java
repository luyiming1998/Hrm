package com.lym.service.impl;

import com.lym.dao.DeptMapper;
import com.lym.entity.Dept;
import com.lym.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Service("DeptService")
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptMapper deptMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Dept> selectAllDept(Dept dept, Integer start) {
        return deptMapper.selectAllDept(dept, start);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Dept selectUpdateDept(Integer id) {
        return deptMapper.selectUpdateDept(id);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public int totalcountDept(Dept dept) {
        return deptMapper.totalcountDept(dept);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int addDept(Dept dept) {
        return deptMapper.addDept(dept);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int updateDept(Dept dept) {
        return deptMapper.updateDept(dept);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int delDept(Integer id) {
        return deptMapper.delDept(id);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Dept> selectAll() {
        return deptMapper.selectAll();
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public int checkDeptName(String name) {
        return deptMapper.checkDeptName(name);
    }
}
