package com.lym.service.impl;

import com.lym.dao.EmployeeMapper;
import com.lym.entity.Employee;
import com.lym.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("EmployeeService")
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeMapper empMapper;
    @Transactional(propagation = Propagation.REQUIRED)
    public int addEmployee(Employee employee) {
        return empMapper.addEmployee(employee);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Employee> selectAllEmployee(Employee employee,Integer start) {
        return empMapper.selectAllEmployee(employee,start);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Employee selectEmployee(Integer id) {
        return empMapper.selectEmployee(id);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public int updateEmployee(Employee employee) {
        return empMapper.updateEmployee(employee);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int deleteEmployee(Integer id) {
        return empMapper.deleteEmployee(id);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public int totalcountEmployee(Employee employee) {
        return empMapper.totalcountEmployee(employee);
    }


}
