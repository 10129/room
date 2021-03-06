package com.jiao.xy99.weixin.weChatServlet;

import com.jiao.xy99.user.service.IUserService;
import com.jiao.xy99.weixin.service.IWeixinService;
import com.jiao.xy99.weixin.service.impl.WeixinServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;


/**
 *Created by xieshuai on 2018/1/15.
 */


@Service
public class weChatAccounts extends HttpServlet {
    static Logger logger = LoggerFactory.getLogger(weChatAccounts.class);
    @Autowired
    private IUserService userService;
    @Autowired
    private IWeixinService weixinService;
    /*
    * 自定义token, 用作生成签名,从而验证安全性
    * */
    private final String TOKEN = "cherry";
@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");
    IWeixinService weixinService=new WeixinServiceImpl();
    String xml=weixinService.processRequest(request);
    System.out.print(xml);
    PrintWriter out = response.getWriter();
    out.print(xml);
    out.close();
}
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        System.out.println("‐‐‐‐‐开始校验签名‐‐‐‐‐");
/**
 * 接收微信服务器发送请求时传递过来的参数
 */
        String signature = req.getParameter("signature");
        String timestamp = req.getParameter("timestamp");
        String nonce = req.getParameter("nonce"); //随机数
        String echostr = req.getParameter("echostr");//随机字符串
/**
 * 将token、timestamp、nonce三个参数进行字典序排序
 * 并拼接为一个字符串
 */
        String sortStr = sort(TOKEN,timestamp,nonce);
/**
 * 字符串进行shal加密
 */
        String mySignature = shal(sortStr);
/**
 * 校验微信服务器传递过来的签名 和 加密后的字符串是否一致, 若一致则签名通过
 */
        if(!"".equals(signature) && !"".equals(mySignature) && signature.equals(mySignature)){
            System.out.println("‐‐‐‐‐签名校验通过‐‐‐‐‐");
            resp.getWriter().write(echostr);
        }else {
            System.out.println("‐‐‐‐‐校验签名失败‐‐‐‐‐");
        }
    }
    /**在web.xml中配置 servlet:
     * * 参数排序
     * @param token
     * @param timestamp
     * @param nonce
     * @return
     * */
    public String sort(String token, String timestamp, String nonce) {
        String[] strArray = {token, timestamp, nonce};
        Arrays.sort(strArray);
        StringBuilder sb = new StringBuilder();
        for (String str : strArray) {
            sb.append(str);
        }
       return sb.toString();
    }
    /**
    * 字符串进行shal加密
    * @param decript
    * @return
    */
    public  String shal(String decript) {
        try {
            MessageDigest digest = MessageDigest
                    .getInstance("SHA-1");
            digest.update(decript.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}