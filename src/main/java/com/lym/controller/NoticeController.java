package com.lym.controller;

import com.lym.entity.Notice;
import com.lym.entity.User;
import com.lym.service.NoticeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@RequestMapping("notice")
@Controller
public class NoticeController {
    private static Logger logger = Logger.getLogger(NoticeController.class);
    @Autowired
    private NoticeService noticeService;

    /**
     * 进入公告查询
     *
     * @return
     */
    @RequestMapping("/toShowNotice.do")
    public ModelAndView toShowNotice(Notice notice,Integer pno) {
        ModelAndView mv = new ModelAndView();
        int totalcount=noticeService.totalcountNotice(notice);//公告记录条数
        int totalPage=totalcount%10==0?totalcount/10:totalcount/10+1;//总页数
        List<Notice> noticeList = noticeService.selectAllNotice(notice,pno==null?0:(pno-1)*10);
        if(notice.getTitle()!=null)
            mv.addObject("title",notice.getTitle());
        if(notice.getContent()!=null)
            mv.addObject("content",notice.getContent());
        if(totalcount<10){
            mv.addObject("size",totalcount);
        }else{
            mv.addObject("size",10);
        }
        mv.addObject("noticeList", noticeList);
        mv.addObject("totalcount",totalcount);
        mv.addObject("totalPage",totalPage);
        mv.addObject("pno",pno);
        if(pno==null)
            mv.addObject("pno",1);
        mv.setViewName("notice/notice");
        return mv;
    }

    /**
     * 进入公告预览
     *
     * @return
     */
    @RequestMapping("/toPreviewNotice.do")
    public ModelAndView toPreviewNotice(Integer id) {
        logger.debug("进入公告预览");
        Notice notice = noticeService.selectNotice(id);
        ModelAndView mv = new ModelAndView();
        mv.addObject("notice", notice);
        mv.setViewName("notice/previewNotice");
        return mv;
    }

    /**
     * 进入添加公告
     *
     * @return
     */
    @RequestMapping("/toShowAddNotice.do")
    public ModelAndView toShowAddNotice() {
        return new ModelAndView("notice/showAddNotice");
    }

    /**
     * 执行添加公告
     *
     * @return
     */
    @RequestMapping("/doAddNotice.do")
    public ModelAndView doAddNotice(Notice notice, HttpServletRequest request) {
        Date d = new Date();
        notice.setCreate_date(d);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("cUser");
        notice.setUser_id(user.getId());
        int num = noticeService.addNotice(notice);
        if (num > 0) {
            return new ModelAndView("redirect:toShowNotice.do");
        } else {
            return new ModelAndView("forward:toShowAddNotice.do", "msg", "新增失败");
        }
    }

    /**
     * 进入修改公共页面
     *
     * @param id
     * @return
     */
    @RequestMapping("/toShowUpdateNotice.do")
    public ModelAndView toShowUpdateNotice(Integer id) {
        Notice notice = noticeService.selectNotice(id);
        ModelAndView mv = new ModelAndView();
        mv.addObject("notice", notice);
        mv.setViewName("/notice/showUpdateNotice");
        return mv;
    }

    /**
     * 执行修改公告
     *
     * @param notice
     * @return
     */
    @RequestMapping("/doUpdateNotice.do")
    public ModelAndView doUpdateNotice(Notice notice) {
        int num = noticeService.updateNotice(notice);
        if (num > 0) {
            return new ModelAndView("redirect:toShowNotice.do");
        } else {
            return new ModelAndView("forward:toShowUpdateNotice.do", "msg", "更新失败");
        }
    }

    /**
     * 逻辑删除公告
     *
     * @param ids
     * @return
     */
    @RequestMapping("/doDelNotice.do")
    public ModelAndView doDelNotice(Integer[] ids) {
        for (int id : ids) {
            noticeService.delNotice(id);
        }
        return new ModelAndView("redirect:toShowNotice.do");
    }
}
