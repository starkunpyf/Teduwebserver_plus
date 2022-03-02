package com.webserver.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebServerApplication {
    public static Class TARGET;
    private ServerSocket serverSocket;
    private ExecutorService threadPool;

    public WebServerApplication(){
        try {
            System.out.println("正在启动服务端");
            serverSocket = new ServerSocket(8088);
            threadPool = Executors.newFixedThreadPool(50);
            System.out.println("服务端启动完毕");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void start(){
        while(true){
            try {
                System.out.println("等待客户端链接");
                Socket socket =serverSocket.accept();
                System.out.println("一个客户端链接了");
                ClientHandler handler = new ClientHandler(socket);
                threadPool.execute(handler);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static void run(Class target,String[] args) {
        TARGET = target;
        WebServerApplication server = new WebServerApplication();
        server.start();
    }
}
