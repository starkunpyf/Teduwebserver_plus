package com.tedu.myspring.controller;


import com.tedu.myspring.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * MVC模型
 * M:model
 * V:view
 * C:controller
 *
 * 处理与用户相关的业务操作
 */
@Controller
public class UserController {
    private static File USER_DIR = new File("./users");
    //private static File staticDir;
    static{
        if(!USER_DIR.exists()){
            USER_DIR.mkdirs();
        }
//        try {
//            staticDir = new File(
//                    ClientHandler.class.getClassLoader().getResource(
//                            "./static"
//                    ).toURI()
//            );
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * 处理用户注册
     * @param request
     * @param response
     */
    @RequestMapping("/myweb/reg")
    public void reg(HttpServletRequest request, HttpServletResponse response){
        System.out.println("开始处理用户注册...");
        //1获取用户表单提交上来的数据
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String nickname = request.getParameter("nickname");
        String ageStr = request.getParameter("age");
        System.out.println(username+","+password+","+nickname+","+ageStr);

        if(username==null||password==null||nickname==null||ageStr==null
        ||!ageStr.matches("[0-9]+")){
//            File file = new File(staticDir,"myweb/reg_fail.html");
//            response.setContentFile(file);
            try {
                response.sendRedirect("myweb/reg_fail.html");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        //2将用户信息以一个User实例形式表示，并序列化到文件中
        int age = Integer.parseInt(ageStr);
        User user = new User(username,password,nickname,age);
        //将该用户信息以用户名.obj的形式保存到users目录中
        File userFile = new File(USER_DIR,username+".obj");

        if(userFile.exists()){
//            File file = new File(staticDir,"/myweb/have_user.html");
//            response.setContentFile(file);
            try {
                response.sendRedirect("/myweb/have_user.html");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        try(
                FileOutputStream fos = new FileOutputStream(userFile);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
        ){
            oos.writeObject(user);
            //注册成功了
//            File file = new File(staticDir,"/myweb/reg_success.html");
//            response.setContentFile(file);
            response.sendRedirect("/myweb/reg_success.html");

        }catch(IOException e){

        }



        System.out.println("处理用户注册完毕!");
    }
    /**
     * 处理用户登录
     * @param request
     * @param response
     */
    @RequestMapping("/myweb/login")
    public void login(HttpServletRequest request, HttpServletResponse response){
        System.out.println("开始处理登录...");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println(username+","+password);

        if(username==null||password==null){
//            File file = new File(staticDir,"/myweb/login_info_error.html");
//            response.setContentFile(file);
            try {
                response.sendRedirect("/myweb/login_info_error.html");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        File userFile = new File(USER_DIR,username+".obj");
        if(userFile.exists()){//该文件存在才说明是注册用户
            //反序列化该注册用户信息
            try(
                    FileInputStream fis = new FileInputStream(userFile);
                    ObjectInputStream ois = new ObjectInputStream(fis);
            ){
                User user = (User)ois.readObject();
                if(user.getPassword().equals(password)){
                    //登录成功
//                    File file = new File(staticDir,"/myweb/login_success.html");
//                    response.setContentFile(file);
                    response.sendRedirect("/myweb/login_success.html");
                    return;
                }
            }catch(IOException | ClassNotFoundException e){
                e.printStackTrace();
            }
        }

        //如果程序能走到这里，就说明要么是用户名不对，要么是密码不对
//        File file = new File(staticDir,"/myweb/login_fail.html");
//        response.setContentFile(file);
        try {
            response.sendRedirect("/myweb/login_fail.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("处理登录完毕!");
    }

    /**
     * 展示用户
     * @param request
     * @param response
     */
    @RequestMapping("/myweb/showAllUser")
    public void showAllUser(HttpServletRequest request, HttpServletResponse response){
        System.out.println("开始生成动态页面");

        //1将users目录下的所有obj文件进行反序列化，并将得到的所有User对象存入一个List集合备用
        List<User> userList = new ArrayList<>();

        //先将users下所有的.obj文件获取回来
        File[] subs = USER_DIR.listFiles(f->f.getName().endsWith(".obj"));
        //遍历每一个obj文件并进行反序列化得到user对象
        for(File userFile : subs){
            try(
                    FileInputStream fis = new FileInputStream(userFile);
                    ObjectInputStream ois = new ObjectInputStream(fis);
            ){
                User user = (User)ois.readObject();
                //将user对象存入userList集合备用
                userList.add(user);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        response.setContentType("text/html;UTF-8");
        //2生成页面

        //2生成页面
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        pw.println("<!DOCTYPE html>");
        pw.println("<html lang=\"en\">");
        pw.println("<head>");
        pw.println("<meta charset=\"UTF-8\">");
        pw.println("<title>用户列表</title>");
        pw.println("</head>");
        pw.println("<body>");
        pw.println("<center>");
        pw.println("<h1>用户列表</h1>");
        pw.println("<table border=\"1\">");
        pw.println("<tr>");
        pw.println("<td>用户名</td>");
        pw.println("<td>密码</td>");
        pw.println("<td>昵称</td>");
        pw.println("<td>年龄</td>");
        pw.println("</tr>");
        for (User user : userList) {
            pw.println("<tr>");
            pw.println("<td>" + user.getUsername() + "</td>");
            pw.println("<td>" + user.getPassword() + "</td>");
            pw.println("<td>" + user.getNickname() + "</td>");
            pw.println("<td>" + user.getAge() + "</td>");
            pw.println("</tr>");
        }
        pw.println("</table>");
        pw.println("</center>");
        pw.println("</body>");
        pw.println("</html>");

        //设置正文类型Content-Type
        response.setContentType("text/html");

        System.out.println("动态页面生成完毕!");






        System.out.println("动态页面生成");
    }
}






