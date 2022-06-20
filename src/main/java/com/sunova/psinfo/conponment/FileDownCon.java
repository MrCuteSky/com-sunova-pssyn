package com.sunova.psinfo.conponment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
@Component
public class FileDownCon{
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${ExcelPath}")
    private String excelPath;

    public void downloadFile(HttpServletRequest request, HttpServletResponse response, String fileName) throws Exception{
        File file = new File(excelPath + fileName);
        if(file.exists()){
            // 配置文件下载
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            // 下载文件能正常显示中文
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));

            // 实现文件下载
            byte[] buffer = new byte[1024];
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            OutputStream os = response.getOutputStream();
            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
            logger.info("*****文件" + fileName + "下载完成！*****");
        }else{
            throw new FileNotFoundException("*****文件" + file.getName() + "不存在*****");
        }
    }


}
