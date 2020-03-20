package com.lym.controller;

import com.lym.entity.User;
import com.lym.service.UserService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RequestMapping("user")
@Controller
public class UserController {
    private static Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    private UserService uService;

    /**
     * 登录模块
     *
     * @param loginName
     * @param password
     * @return
     */
    @RequestMapping("/login.do")
    public ModelAndView Login(String loginName, String password, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        User user = uService.Login(loginName, password);
        HttpSession session=request.getSession();
        session.setAttribute("cUser",user);
        if (user != null) {
            logger.debug("登录成功");
            mv.addObject("user", user);
            mv.setViewName("main");
        } else {
            mv.addObject("msg", "用户名或密码错误，登陆失败");
            mv.setViewName("forward:staticJsp/loginForm.jsp");
        }
        return mv;
    }

    /**
     * 退出登陆
     * @param request
     * @return
     */
    @RequestMapping("/logout.do")
    public ModelAndView Logout(HttpServletRequest request){
        HttpSession session=request.getSession();
        session.invalidate();
        return new ModelAndView("redirect:../staticJsp/loginForm.jsp");
    }
    /**
     * 用户查询模块
     *
     * @return
     */
    @RequestMapping("/toShowUser.do")
    public ModelAndView toShowUser(@ModelAttribute User user, Integer pno) {
        logger.debug("进入用户查询");
        ModelAndView mv = new ModelAndView();
        int totalcount=uService.totalcountUser(user);//用户记录条数
        int totalPage=totalcount%10==0?totalcount/10:totalcount/10+1;//总页数
        List<User> ulist = uService.selectAllUser(user,pno==null?0:(pno-1)*10);
        if (user.getUsername() != null)
            mv.addObject("uname", user.getUsername());
        if (user.getStatus() != 0)
            mv.addObject("status", user.getStatus());
        if(totalcount<10){
            mv.addObject("size",totalcount);
        }else{
            mv.addObject("size",10);
        }
        mv.addObject("ulist", ulist);
        mv.addObject("totalcount",totalcount);
        mv.addObject("totalPage",totalPage);
        mv.addObject("pno",pno);
        if(pno==null)
            mv.addObject("pno",1);
        mv.setViewName("user/user");
        return mv;
    }

    /**
     * 进入用户修改模块
     *
     * @param id
     * @return
     */
    @RequestMapping("/toShowUpdateUser.do")
    public ModelAndView toShowUpdateUser(Integer id) {
        logger.debug("进入用户修改");
        logger.debug(id);
        ModelAndView mv = new ModelAndView();
        User user = uService.selectUpdateUser(id);
        if (user != null)
            mv.addObject("user", user);
        mv.setViewName("user/showUpdateUser");
        return mv;
    }

    /**
     * 执行修改用户
     * @param user
     * @return
     */
    @RequestMapping("/doUpdateUser.do")
    public ModelAndView doUpdateUser(User user) {
        logger.debug("执行用户修改");
        int num = uService.updateUser(user);
        if (num > 0) {
            return new ModelAndView("redirect:toShowUser.do");
        } else {
            return new ModelAndView("forward:toShowUpdateUser.do","msg","修改失败");
        }
    }

    /**
     * 进入新增用户界面
     *
     * @return
     */
    @RequestMapping("/toShowAddUser.do")
    public ModelAndView toShowAddUser() {
        logger.debug("进入新增用户界面");
        return new ModelAndView("user/showAddUser");
    }

    /**
     * 执行新增用户
     * @param user
     * @return
     */
    @RequestMapping("/doAddUser.do")
    public ModelAndView doAddUser(User user) {
        logger.debug("增加用户");
        Date d = new Date();
        user.setCreateDate(d);
        int num = uService.addUser(user);
        if (num > 0) {
            return new ModelAndView("redirect:toShowUser.do");
        } else {
            return new ModelAndView("forward:toAddUser", "msg", "新增用户失败");
        }
    }

    /**
     * 进入用户密码修改界面
     * @return
     */
    @RequestMapping("/toEditPassword.do")
    public ModelAndView toEditPassword(){
        return new ModelAndView("editPassword/editPassword");
    }

    /**
     * 执行修改密码
     * @param newPassword
     * @param oldPassword
     * @param request
     * @return
     */
    @RequestMapping("/doEditPassword.do")
    public ModelAndView doEditPassword(String newPassword,String oldPassword,HttpServletRequest request){
        logger.debug("进入修改密码");
        HttpSession session=request.getSession();
        User user= (User) session.getAttribute("cUser");
        logger.debug(user.getPassword());
        if(user.getPassword().equals(oldPassword)){
            int num=uService.updatePwd(user.getId(),newPassword);
            if (num>0){
                logger.debug("修改成功");
                session.invalidate();//销毁会话
                return new ModelAndView("redirect:staticJsp/loginForm.jsp");
            }else{
                return new ModelAndView("forward:toEditPassword.do","msg","修改密码失败");
            }
        }else {
            return new ModelAndView("forward:toEditPassword.do","msg","修改密码失败,请重新输入当前密码");
        }
    }

    /**
     * 逻辑删除用户
     * @param ids
     * @return
     */
    @RequestMapping("/doDelUser.do")
    public ModelAndView doDelUser(Integer[] ids){
        for(int id :ids){
            uService.delUser(id);
        }
        return new ModelAndView("redirect:toShowUser.do");
    }

    @RequestMapping("checkName.do")
    @ResponseBody
    public String checkName(String loginName){
        logger.debug("进入查重");
        int num=uService.selectName(loginName);
        if(num==0)
            return "ok";
        else
            return "error";
    }

    @RequestMapping("toExport.do")
    public ModelAndView toExport(){
        return new ModelAndView("user/export");
    }
/*    @RequestMapping("toImport")
    @ResponseBody
    public String importXls(HttpServletRequest request){

    }*/
}
