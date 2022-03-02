package org.tedu.controller;

import com.webserver.annotation.Controller;
import com.webserver.annotation.RequestMapping;
import com.webserver.http.HttpServletRequest;
import com.webserver.http.HttpServletResponse;

@Controller
public class FristController {
    @RequestMapping("/test")
    public void test(HttpServletRequest request, HttpServletResponse response){
        System.out.println("frist process");
        response.sendRedirect("/index.html");
    }
}
