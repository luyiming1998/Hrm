package com.lym.dao;

import com.lym.entity.Employee;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmployeeMapper {
    int addEmployee(Employee employee);

    List<Employee> selectAllEmployee(@Param("employee") Employee employee,@Param("start") Integer start);

    Employee selectEmployee(Integer id);

    int updateEmployee(Employee employee);

    int deleteEmployee(Integer id);

    int totalcountEmployee(Employee employee);
}
