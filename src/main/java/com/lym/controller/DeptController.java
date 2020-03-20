package com.lym.controller;

import com.lym.entity.Dept;
import com.lym.service.DeptService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequestMapping("dept")
@Controller
public class DeptController {
    private static Logger logger = Logger.getLogger(DeptController.class);
    @Autowired
    private DeptService deptService;

    /**
     * 部门查询
     *
     * @return
     */
    @RequestMapping("/toShowDept.do")
    public ModelAndView toShowDept(Dept dept, Integer pno) {
        logger.debug("进入部门查询");
        ModelAndView mv = new ModelAndView();
        int totalcount = deptService.totalcountDept(dept);//部门记录条数
        int totalPage = totalcount % 10 == 0 ? totalcount / 10 : totalcount / 10 + 1;//总页数
        List<Dept> deptList = deptService.selectAllDept(dept,pno == null ? 0 : (pno - 1) * 10);
        if (dept.getName() != null)
            mv.addObject("deptname", dept.getName());
        if (totalcount < 10) {
            mv.addObject("size", totalcount);
        } else {
            mv.addObject("size", 10);
        }
        mv.addObject("deplist", deptList);
        mv.addObject("totalcount", totalcount);
        mv.addObject("totalPage", totalPage);
        mv.addObject("pno", pno);
        if (pno == null)
            mv.addObject("pno", 1);
        mv.setViewName("dept/dept");
        return mv;
    }

    /**
     * 进入新增部门页面
     *
     * @return
     */
    @RequestMapping("/toShowAddDept.do")
    public ModelAndView toShowAddDept() {
        logger.debug("进入新增部门");
        return new ModelAndView("dept/showAddDept");
    }

    /**
     * 执行新增部门
     *
     * @param dept
     * @return
     */
    @RequestMapping("/doAddDept.do")
    public ModelAndView doAddDept(Dept dept) {
        logger.debug("执行新增部门");
        int num = deptService.addDept(dept);
        if (num > 0) {
            return new ModelAndView("redirect:toShowDept.do");
        } else {
            return new ModelAndView("forward:toShowAddDept.do", "msg", "新增失败");
        }
    }

    /**
     * 进入部门修改页面
     * @param id
     * @return
     */
    @RequestMapping("/toShowUpdateDept.do")
    public ModelAndView toShowUpdateDept(Integer id){
        logger.debug("进入部门修改");
        ModelAndView mv=new ModelAndView();
        Dept dept=deptService.selectUpdateDept(id);
        mv.addObject("dept",dept);
        mv.setViewName("dept/showUpdateDept");
        return mv;
    }

    /**
     * 修改部门信息
     * @param dept
     * @return
     */
    @RequestMapping("/doUpdateDept.do")
    public ModelAndView doUpdateDept(Dept dept){
        ModelAndView mv =new ModelAndView();
        int num=deptService.updateDept(dept);
        if(num>0){
            mv.setViewName("redirect:toShowDept.do");
        }else {
            mv.addObject("msg","更新失败");
            mv.setViewName("forward:toShowUpdateDept.do");
        }
        return mv;
    }

    /**
     * 逻辑删除部门
     * @param ids
     * @return
     */
    @RequestMapping("doDelDept.do")
    public ModelAndView doDelDept(Integer[] ids){
        for(int id :ids){
            deptService.delDept(id);
        }
        return new ModelAndView("redirect:toShowDept.do");
    }

    @RequestMapping("checkDeptName.do")
    @ResponseBody
    public String checkDeptName(String name){
        logger.debug("进入查重");
        int num=deptService.checkDeptName(name);
        if(num==0)
            return "ok";
        else
            return "error";
    }
}
