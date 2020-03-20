package com.lym.controller;

import com.lym.entity.Job;
import com.lym.service.JobService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

@RequestMapping("job")
@Controller
public class JobController {
    private static Logger logger = Logger.getLogger(JobController.class);
    @Autowired
    private JobService jobService;

    /**
     * 职位查询
     *
     * @return
     */
    @RequestMapping("/toShowJob.do")
    public ModelAndView toShowJob(Job job, Integer pno) {
        logger.debug("进入职位查询");
        ModelAndView mv = new ModelAndView();
        int totalcount = jobService.totalcountJob(job);//职位记录条数
        int totalPage = totalcount % 10 == 0 ? totalcount / 10 : totalcount / 10 + 1;//总页数
        List<Job> jobList = jobService.selectAllJob(job,pno == null ? 0 : (pno - 1) * 10);
        if (job.getName() != null)
            mv.addObject("jobname", job.getName());
        if (totalcount < 10) {
            mv.addObject("size", totalcount);
        } else {
            mv.addObject("size", 10);
        }
        mv.addObject("joblist", jobList);
        mv.addObject("totalcount", totalcount);
        mv.addObject("totalPage", totalPage);
        mv.addObject("pno", pno);
        if (pno == null)
            mv.addObject("pno", 1);
        mv.setViewName("job/job");
        return mv;
    }

    /**
     * 进入新增职位页面
     *
     * @return
     */
    @RequestMapping("/toShowAddJob.do")
    public ModelAndView toShowAddJob() {
        logger.debug("进入新增职位");
        return new ModelAndView("job/showAddJob");
    }

    /**
     * 执行新增职位
     *
     * @param job
     * @return
     */
    @RequestMapping("/doAddJob.do")
    public ModelAndView doAddJob(Job job) {
        logger.debug("执行新增职位");
        int num = jobService.addJob(job);
        if (num > 0) {
            return new ModelAndView("redirect:toShowJob.do");
        } else {
            return new ModelAndView("forward:toShowAddJob.do", "msg", "新增失败");
        }
    }

    /**
     * 进入职位修改页面
     * @param id
     * @return
     */
    @RequestMapping("/toShowUpdateJob.do")
    public ModelAndView toShowUpdateJob(Integer id){
        logger.debug("进入职位修改");
        ModelAndView mv=new ModelAndView();
        Job job=jobService.selectUpdateJob(id);
        mv.addObject("job",job);
        mv.setViewName("job/showUpdateJob");
        return mv;
    }

    /**
     * 修改职位信息
     * @param job
     * @return
     */
    @RequestMapping("/doUpdateJob.do")
    public ModelAndView doUpdateJob(Job job){
        ModelAndView mv =new ModelAndView();
        int num=jobService.updateJob(job);
        if(num>0){
            mv.setViewName("redirect:toShowJob.do");
        }else {
            mv.addObject("msg","更新失败");
            mv.setViewName("forward:toShowUpdateJob.do");
        }
        return mv;
    }

    /**
     * 逻辑删除职位
     * @param ids
     * @return
     */
    @RequestMapping("doDelJob.do")
    public ModelAndView doDelJob(Integer[] ids){
        for(int id :ids){
            jobService.delJob(id);
        }
        return new ModelAndView("redirect:toShowJob.do");
    }

    @RequestMapping("checkJobName.do")
    @ResponseBody
    public String checkJobName(String name){
        logger.debug("进入查重");
        int num=jobService.checkJobName(name);
        if(num==0)
            return "ok";
        else
            return "error";
    }
}
