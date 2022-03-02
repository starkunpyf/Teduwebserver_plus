package com.webserver.core;

import com.webserver.http.EmptyRequestException;
import com.webserver.http.HttpServletResponse;
import com.webserver.http.HttpServletRequest;

import java.io.IOException;
import java.net.Socket;
import java.net.URISyntaxException;

public class ClientHandler implements Runnable{
    private Socket socket;

    public ClientHandler(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            //解析请求
            HttpServletRequest request = new HttpServletRequest(socket);
            HttpServletResponse response = new HttpServletResponse(socket);
            //处理请求
            DispatcherServlet servlet = new DispatcherServlet();
            servlet.service(request,response);
            //发送响应
            response.Response();


        } catch (IOException e) {
            e.printStackTrace();
        } catch (EmptyRequestException e) {

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } finally{
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
