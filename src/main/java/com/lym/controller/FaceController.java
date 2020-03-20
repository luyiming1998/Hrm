package com.lym.controller;

import com.lym.entity.User;
import com.lym.face.FaceUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RequestMapping("face")
@Controller
public class FaceController {
    private static Logger logger = Logger.getLogger(FaceController.class);

    /**
     * 进入人脸注册页面
     * @return
     */
    @RequestMapping("/toFaceRegister.do")
    public ModelAndView toFaceRegister() {
        return new ModelAndView("user/face");
    }

    /**
     * 执行人脸注册
     * @param base
     * @param req
     * @return
     */
    @RequestMapping("/doFaceRegister.do")
    @ResponseBody
    public String doFaceRegister(String base, HttpServletRequest req) {
        HttpSession session=req.getSession();
        User user= (User) session.getAttribute("cUser");
        String result= FaceUtil.add(base,user.getLoginName(),user.getPassword());
        String jieguo = result.substring(result.indexOf(":")+1,result.indexOf(","));
        if("0".equals(jieguo)){
            return "ok";
        }else{
            return "error";
        }
    }

    /**
     * 进入人脸登录
     * @return
     */
    @RequestMapping("/toFaceLogin.do")
    public ModelAndView toFaceLogin(){
        return new ModelAndView("face/face");
    }

    /**
     * 执行人脸登录
     * @param base
     * @return
     */
    @RequestMapping("/doFaceLogin.do")
    @ResponseBody
    public String doFaceLogin(String base){
        return FaceUtil.search(base);
    }
}
