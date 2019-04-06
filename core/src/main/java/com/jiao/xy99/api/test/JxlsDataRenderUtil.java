package com.jiao.xy99.api.test;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.sun.deploy.net.HttpResponse;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;


public class JxlsDataRenderUtil {
    private static Logger logger = LoggerFactory.getLogger(com.jiao.xy99.api.test.JxlsDataRenderUtil.class);

    /**
     * Excel数据渲染
     * @param response
     * @param dataMap
     * @param TemplateFileName
     * @param filename
     * @return
     */
        public Boolean  excelDataRender(HttpServletResponse response, Map<String,Object> dataMap,String TemplateFileName,String filename){
           boolean st=true;
            XLSTransformer transformer = new XLSTransformer();
            InputStream in=null;
            OutputStream  out=null;
            try {
                // 下面几行是为了解决文件名乱码的问题
                response.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes(), "iso-8859-1"));
                response.setContentType("application/vnd.ms-excel;charset=UTF-8");
                response.setHeader("Pragma", "no-cache");
                response.setHeader("Cache-Control", "no-cache");
                response.setDateHeader("Expires", 0);
                //得到文件
                //File file = ResourceUtils.getFile("classpath:/conf/testExcel.xlsx");
                String file= this.getClass().getResource("/").getFile();
                in=new BufferedInputStream(new FileInputStream(file+TemplateFileName));
                out = response.getOutputStream();

                Workbook workbook= null;
                workbook = transformer.transformXLS(in, dataMap);
                workbook.write(out);
                out.flush();
                // 把下载地址返回给前端
            } catch (InvalidFormatException e) {
                e.printStackTrace();
                logger.debug("debug: InvalidFormatException"+e);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (in!=null){try {in.close();} catch (IOException e) {}}
                if (out!=null){try {out.close();} catch (IOException e) {}}
            }
           return  st;
        }

    }

