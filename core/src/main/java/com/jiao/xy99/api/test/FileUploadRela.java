package com.jiao.xy99.api.test;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.*;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUploadRela {
    private static String logFile="";
    private static String batchNum;
    private static String logPath;

    /**
     * 初始化，路径
     * @throws IOException
     */
    public void init()throws IOException{
        //创建批次号
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd HHmmss");
        batchNum=sdf.format(new Date());
        //创建日志文件
        logFile =logPath+batchNum+".log";
        creatFile(logFile);
    }

    /**
     * 复制单个文件
     * @param oldPath
     * @param newPath
     * @throws IOException
     */
    public  static void copyFileStreams(String oldPath,String newPath) throws IOException {
        int byteSum=0;
        int byteRead=0;
        InputStream in=null;
        FileOutputStream fs=null;
        try {
            File oldFile=new File(oldPath);
            if(oldFile.exists()){
                //读入源文件
                in=new FileInputStream(oldFile);
                fs=new FileOutputStream(newPath);
                byte[] buffer=new byte[1444];
                int length;
                while ((byteRead=in.read(buffer))!=-1){
                    byteSum +=byteRead;
                    fs.write(buffer,0,byteRead);
                }
                filechaseFW(logFile,"Copy file success!");
            }
        }catch (Exception e){
            e.printStackTrace();
            filechaseFW(logFile,"Copy file exception:"+e.getLocalizedMessage());
        }finally {
            in.close();
            fs.close();
        }
    }
        /**
         * 复制文件
         * @param oldPath
         * @param newPath
         * @throws IOException
         */
    public  static void copyFileChannels(String oldPath,String newPath) throws IOException {
        FileChannel inputChannel=null;
        FileChannel outputChannel=null;
        File soure =new File(oldPath);
        File dest =new File(newPath);
        //目标文件存在，不需要重复COPY
        if(dest.exists()){
            filechaseFW(logFile,"File alreay exist ,no need copy!");
            return;
        }
        try{
            inputChannel=new FileInputStream(soure).getChannel();
            outputChannel=new FileInputStream(dest).getChannel();
            outputChannel.transferFrom(inputChannel,0,inputChannel.size());

            filechaseFW(logFile,"Copy file success!");
        }catch (Exception e){
            e.printStackTrace();
            filechaseFW(logFile,"Copy file exception:"+e.getLocalizedMessage());
        }finally {
            inputChannel.close();
            outputChannel.close();
        }
    }
    /**
     * 创建文件
     * @param fileName
     * @throws IOException
     */
    public  static void creatFile(String fileName) throws IOException {
        try {
            File file=new File(fileName);
            if(!file.exists()){
                file.createNewFile();
            }
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
        /**
         * 写入TXT ,追加写入
         * @param filePath
         * @param content
         * @throws IOException
         */
    public  static void filechaseFW(String filePath,String content) throws IOException{
        try {
            //构造函数中的第二个参数true表示以追加形式写文件
            FileWriter fw=new FileWriter(filePath,true);
            fw.write(new Date().toString()+" "+ content+System.getProperty("line.separator"));
            fw.close();
        }catch (IOException e){
            e.printStackTrace();
            throw e;
        }
    }
}
