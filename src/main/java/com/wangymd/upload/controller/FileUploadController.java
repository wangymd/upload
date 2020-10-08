package com.wangymd.upload.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONObject;

@Controller  //此处必须是@Controller，@RestController不行  
public class FileUploadController {
    private final static Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    @RequestMapping("/")
    public String index() {
        return "/index";
    }
    
    /**
     http://ip:port/file
    */
    @RequestMapping("/file")
    public String file() {
        logger.info("单文件上传页面");
        return "/fileUpload";
    }
    
    @RequestMapping("/file1")
    public String file1() {
        logger.info("单文件上传页面");
        return "/fileUpload1";
    }
    
    /**
    http://ip:port/file
   */
   @RequestMapping("/file2")
   public String file2() {
       logger.info("单文件上传页面");
       return "/fileUpload2";
   }
   
   /**
   http://ip:port/file
  */
  @RequestMapping("/file3")
  public String file3() {
      logger.info("单文件上传页面");
      return "/fileUpload3";
  }

    /**
     http://ip:port/multifile
    */
    @RequestMapping("/multifile")
    public String multifile() {
        logger.info("多文件上传页面");
        return "/multifileUpload";
    }

    /**
     * 实现单文件上传
     *http://ip:port/fileUpload
     * */
    @RequestMapping("fileUpload")
    @ResponseBody 
    public String fileUpload(@RequestParam("fileName") MultipartFile file){
    	JSONObject jsonObject = new JSONObject();
    	String code = "0";
    	String msg = "ok";
        if(file.isEmpty()){
        	msg = "this file is empty";
        }else {
            String fileName = file.getOriginalFilename();
            int size = (int) file.getSize();
            System.out.println(fileName + "-->" + size);

            String path = "F:/test" ;//文件保存路径
            File targetFile = new File(path + "/" + fileName);
            if(!targetFile.getParentFile().exists()){ //判断文件父目录是否存在
                targetFile.getParentFile().mkdir();
            }
            try {
                file.transferTo(targetFile); //保存文件
            } catch (Exception e) {
                e.printStackTrace();
                msg = e.getMessage();
            }
        }
        jsonObject.put("code", code);
        jsonObject.put("msg", msg);
        return jsonObject.toJSONString();
    }

    /**
     * 实现多文件上传
     * http://ip:port/multifileUpload
     * */
    @RequestMapping(value="multifileUpload",method=RequestMethod.POST) 
    public @ResponseBody String multifileUpload(HttpServletRequest request){

        List<MultipartFile> files = ((MultipartHttpServletRequest)request).getFiles("fileName");

        if(files.isEmpty()){
            return "this file is empty";
        }

        String path = "F:/test" ;

        for(MultipartFile file:files){
            String fileName = file.getOriginalFilename();
            int size = (int) file.getSize();
            System.out.println(fileName + "-->" + size);

            if(file.isEmpty()){
                return "this file is empty";
            }else{        
                File targetFile= new File(path + "/" + fileName);
                if(!targetFile.getParentFile().exists()){ //判断文件父目录是否存在
                    targetFile.getParentFile().mkdir();
                }
                try {
                    file.transferTo(targetFile);
                }catch (Exception e) {
                    e.printStackTrace();
                    return "upload file fail";
                } 
            }
        }
        return "upload file success";
    }

    @RequestMapping("download")
    public String downLoad(HttpServletResponse response){
        String filename="test.jpg";
        String filePath = "F:/test" ;
        File file = new File(filePath + "/" + filename);
        if(file.exists()){ //判断文件父目录是否存在
            response.setContentType("application/force-download");
            response.setHeader("Content-Disposition", "attachment;fileName=" + filename);

            byte[] buffer = new byte[1024];
            FileInputStream fis = null; //文件输入流
            BufferedInputStream bis = null;

            OutputStream os = null; //输出流
            try {
                os = response.getOutputStream();
                fis = new FileInputStream(file); 
                bis = new BufferedInputStream(fis);
                int i = bis.read(buffer);
                while(i != -1){
                    os.write(buffer);
                    i = bis.read(buffer);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("file download:  " + filename);
            try {
                bis.close();
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}