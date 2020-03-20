package com.lym.controller;

import com.lym.entity.Dept;
import com.lym.entity.Employee;
import com.lym.entity.Job;
import com.lym.service.DeptService;
import com.lym.service.EmployeeService;
import com.lym.service.JobService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

@RequestMapping("emp")
@Controller
public class EmployeeController {
    private static Logger logger = Logger.getLogger(EmployeeController.class);
    @Autowired
    private EmployeeService empService;
    @Autowired
    private DeptService deptService;
    @Autowired
    private JobService jobService;

    /**
     * 进入员工查询
     *
     * @return
     */
    @RequestMapping("/toShowEmployee.do")
    public ModelAndView toShowEmployee(Employee employee,Integer pno) {
        ModelAndView mv = new ModelAndView();
        List<Dept> deptList = deptService.selectAll();
        List<Job> jobList = jobService.selectAll();
        int totalcount=empService.totalcountEmployee(employee);//员工记录条数
        int totalPage=totalcount%10==0?totalcount/10:totalcount/10+1;//总页数
        List<Employee> empList = empService.selectAllEmployee(employee,pno==null?0:(pno-1)*10);
        if(totalcount<10){
            mv.addObject("size",totalcount);
        }else{
            mv.addObject("size",10);
        }
        mv.addObject("totalcount",totalcount);
        mv.addObject("totalPage",totalPage);
        mv.addObject("pno",pno);
        if(pno==null)
            mv.addObject("pno",1);

        mv.addObject(jobList);
        mv.addObject(deptList);
        mv.addObject("empList", empList);
        mv.addObject("job_id",employee.getJob_id());
        mv.addObject("name",employee.getName());
        mv.addObject("card_id",employee.getCard_id());
        mv.addObject("sex",employee.getSex());
        mv.addObject("phone",employee.getPhone());
        mv.addObject("dept_id",employee.getDept_id());
        mv.setViewName("employee/employee");
        return mv;
    }

    /**
     * 进入添加员工界面
     *
     * @return
     */
    @RequestMapping("/toShowAddEmployee.do")
    public ModelAndView toShowAddEmployee() {
        logger.debug("进入添加员工");
        ModelAndView mv = new ModelAndView();
        List<Dept> deptList = deptService.selectAll();
        List<Job> jobList = jobService.selectAll();
        mv.addObject(jobList);
        mv.addObject(deptList);
        mv.setViewName("employee/showAddEmployee");
        return mv;
    }

    /**
     * 执行添加员工
     *
     * @return
     */
    @RequestMapping("/doAddEmployee.do")
    public ModelAndView doAddEmployee(Employee employee) {
        Date d = new Date();
        logger.debug(d);
        employee.setCreate_date(d);
        int num = empService.addEmployee(employee);
        if (num > 0) {
            return new ModelAndView("redirect:toShowEmployee.do");
        } else {
            return new ModelAndView("forward:toShowAddEmployee.do", "msg", "添加失败");
        }
    }

    /**
     * 前往员工更新页面
     * @param id
     * @return
     */
    @RequestMapping("/toShowUpdateEmployee.do")
    public ModelAndView toShowUpdateEmployee(Integer id) {
        Employee employee = empService.selectEmployee(id);
        ModelAndView mv = new ModelAndView();
        List<Dept> deptList = deptService.selectAll();
        List<Job> jobList = jobService.selectAll();
        mv.addObject(jobList);
        mv.addObject(deptList);
        mv.addObject("employee", employee);
        mv.setViewName("employee/showUpdateEmployee");
        return mv;
    }

    /**
     * 执行员工更新
     * @param employee
     * @return
     */
    @RequestMapping("/doUpdateEmployee.do")
    public ModelAndView doUpdateEmployee(Employee employee) {
        int num = empService.updateEmployee(employee);
        if (num > 0) {
            return new ModelAndView("redirect:toShowEmployee.do");
        } else {
            return new ModelAndView("forward:toShowUpdateEmployee.do", "msg", "更新失败");
        }
    }

    /**
     * 逻辑删除员工
     * @param ids
     * @return
     */
    @RequestMapping("/doDeleteEmployee")
    public ModelAndView doDeleteEmployee(Integer[] ids) {
        for (int id : ids) {
            empService.deleteEmployee(id);
        }
        return new ModelAndView("redirect:toShowEmployee.do");

    }
}
