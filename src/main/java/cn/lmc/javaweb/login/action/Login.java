package main.java.cn.lmc.javaweb.login.action;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class Login extends javax.servlet.http.HttpServlet {
    @Override
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    @Override
    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        System.out.println(new Date());
        //响应乱码处理// 设置响应内容类型
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out =response.getWriter();
        //输出到网页 out.println("时间为："+new Date());
         String accept =request.getHeader("Accept");
        // 获取网页信息 再写出到网页上
         out.println(accept);
    }
}
