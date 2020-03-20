package com.lym.controller;

import com.lym.entity.Document;
import com.lym.entity.User;
import com.lym.service.DocumentService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.List;

@RequestMapping("doc")
@Controller
public class DocumentController {

    private static Logger logger = Logger.getLogger(DocumentController.class);

    @Autowired
    private DocumentService documentService;

    /**
     * 进入文档查询
     *
     * @return
     */
    @RequestMapping("/toShowDocument.do")
    public ModelAndView toShowDocument(String title,Integer pno) {
        ModelAndView mv = new ModelAndView();
        int totalcount=documentService.totalcountDocument(title);//用户记录条数
        int totalPage=totalcount%10==0?totalcount/10:totalcount/10+1;//总页数
        List<Document> docList = documentService.selectAllDocument(title,pno==null?0:(pno-1)*10);
        if (title!= null)
            mv.addObject("title", title);
        if(totalcount<10){
            mv.addObject("size",totalcount);
        }else{
            mv.addObject("size",10);
        }
        mv.addObject("docList", docList);
        mv.addObject("totalcount",totalcount);
        mv.addObject("totalPage",totalPage);
        mv.addObject("pno",pno);
        if(pno==null)
            mv.addObject("pno",1);
        mv.setViewName("document/document");
        return mv;
    }

    /**
     * 进入上传文档
     *
     * @return
     */
    @RequestMapping("/toShowAddDocument.do")
    public ModelAndView toShowAddDocument() {
        return new ModelAndView("document/showAddDocument");
    }

    /**
     * 上传文档
     * @param document
     * @return
     */
    @RequestMapping("/doAddDocument.do")
    public ModelAndView doAddDocument(Document document, MultipartFile mf, HttpServletRequest req) throws Exception{
        String fname = mf.getOriginalFilename();//获取文件名
        logger.debug(fname);
        String ext= FilenameUtils.getExtension(fname);//获取后缀名
        String saveName=System.currentTimeMillis()+"."+ext;//生成随机文件名
        //指定物理存储位置
        String Path=req.getServletContext().getRealPath("upload");
        logger.debug("存储路径:"+Path);
        File file=new File(Path+"/"+saveName); //实现上传
        FileUtils.copyInputStreamToFile(mf.getInputStream(),file);
        Date d = new Date();
        User user= (User) req.getSession().getAttribute("cUser");
        document.setUser_id(user.getId());
        document.setCreate_date(d);
        document.setFilename(saveName);
        int num=documentService.addDocument(document);
        if(num>0){
            return new ModelAndView("redirect:toShowDocument.do");
        }else{
            return new ModelAndView("forward:toShowAddDocument.do","msg","新增失败");
        }
    }

    /**
     * 下载文档
     * @param fileName
     * @param req
     * @param response
     * @throws Exception
     */
    @RequestMapping("/doDownload")
    public void downLoad(String fileName, HttpServletRequest req, HttpServletResponse response)throws Exception{
        //拿到文件所在位置
        String fPath=req.getServletContext().getRealPath("/upload/"+fileName);
        //通过文件路径构建一个输入流
        FileInputStream fis=new FileInputStream(fPath);
        //通过res对象 设置输出文件相关参数 --字符编码|文件类型|文档头等
        response.setHeader("content-disposition","attchment;fileName="+fileName);
        response.setCharacterEncoding("utf-8");//设置字符编码
        response.setContentType("application/octet-stream"); //任意的二进制文件
        response.setContentLength(fis.available());
        byte[] by=new byte[1024*4]; //设置一个缓冲容器
        int line=0;
        while ((line=fis.read(by))!=-1) {//循环读写
            response.getOutputStream().write(by,0,by.length);
            response.getOutputStream().flush(); //刷新
        }
        response.getOutputStream().close();
        fis.close();
    }

    /**
     * 进入修改文件
     * @param id
     * @return
     */
    @RequestMapping("/toShowUpdateDocument.do")
    public ModelAndView toShowUpdateDocument(Integer id){
        ModelAndView mv=new ModelAndView();
        Document doc=documentService.selectDocument(id);
        mv.addObject("doc",doc);
        mv.setViewName("document/showUpdateDocument");
        return mv;
    }

    /**
     * 执行修改文件
     * @param document
     * @return
     */
    @RequestMapping("/doUpdateDocument.do")
    public ModelAndView doUpdateDocument(Document document){
        int num=documentService.updateDocument(document);
        if(num>0)
            return new ModelAndView("redirect:toShowDocument.do");
        else
            return new ModelAndView("forward:toShowUpdateDocument.do","msg","更新失败");
    }
    /**
     * 逻辑删除文档
     * @param ids
     * @return
     */
    @RequestMapping("/doDelDocument.do")
    public ModelAndView doDelDocument(Integer[] ids){
        for(int id :ids){
            documentService.delDocument(id);
        }
        return new ModelAndView("redirect:toShowDocument.do");
    }
}
