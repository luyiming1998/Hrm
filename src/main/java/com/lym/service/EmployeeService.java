package com.lym.service;

import com.lym.entity.Employee;

import java.util.List;

public interface EmployeeService {
    int addEmployee(Employee employee);

    List<Employee> selectAllEmployee(Employee employee,Integer start);

    Employee selectEmployee(Integer id);

    int updateEmployee(Employee employee);

    int deleteEmployee(Integer id);

    int totalcountEmployee(Employee employee);
}
