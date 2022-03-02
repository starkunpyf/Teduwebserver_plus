package socket;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * 聊天室客户端
 */
public class Client {
    /**
     * java.net.Socket 套接字
     * Socket封装了TCP协议的通讯细节，我们使用他可以与远端计算机建立TCP链接。并
     * 基于一堆流的IO操作完成与远端计算机的数据交换。
     */
    private Socket socket;

    /**
     * 初始化客户端
     */
    public Client(){
        try {
            /*
                实例化Socket时需要传入两个参数:
                参数1:远端计算机的地址信息
                参数2:远端计算机打开的服务端口
                上述构造器实例化的过程就是与远端计算机建立链接的过程，如果成功建立链接
                则实例化成功，否则构造器会抛出异常。
                我们通过远端计算机地址(IP)可以找到网络上的远端计算机，通过服务端口可以
                链接上运行在该计算机上的服务端应用程序(就是这个程序开打的该端口等待我们
                链接的。)

                本机地址信息可以选取:
                localhost
                127.0.0.1
             */
            System.out.println("正在链接服务端...");
            socket = new Socket("localhost",8088);
            System.out.println("与服务端建立链接!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 客户端开始工作的方法
     */
    public void start(){
        try {
            //启动一个线程来读取服务端发送过来的消息
            ServerHandler handler = new ServerHandler();
            Thread t = new Thread(handler);
            //将读取服务端消息的线程设置为守护线程
            //这样以来，当我们停止给服务端发送消息(主线程结束,进程没有其他用户线程活着)
            //那么守护线程就会被杀死
            t.setDaemon(true);
            t.start();


            /*
                通过Socket的方法:
                OutputStream getOutputStream()
                获取的字节输出流写出的字节会通过网络发送给远端建立好链接的计算机。
             */
            OutputStream out = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(out, StandardCharsets.UTF_8);
            BufferedWriter bw = new BufferedWriter(osw);
            PrintWriter pw = new PrintWriter(bw,true);

            Scanner scanner = new Scanner(System.in);
            while(true) {
                String line = scanner.nextLine();
                if("exit".equalsIgnoreCase(line)){
                    break;
                }
                pw.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            try {
                socket.close();//与远端计算机断开连接，进行TCP挥手
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }
    /*
        该线程负责读取服务端发送过来的消息
     */
    private class ServerHandler implements Runnable{
        public void run(){//线程的run方法不允许使用throws声明异常的抛出
            try {
                //通过socket获取输入流读取服务端发送过来的消息
                InputStream in = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(in, StandardCharsets.UTF_8);
                BufferedReader br = new BufferedReader(isr);

                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
            }catch(Exception e){
                /*
                    这里不输出错误信息了。当远端计算机异常断开时会出现异常，可以不输出错误信息
                 */
            }
        }
    }

}






