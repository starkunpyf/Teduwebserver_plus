package socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * 聊天室服务端
 */
public class Server {
    /**
     * java.net.ServerSocket
     * ServerSocket是运行在服务端的，它的主要工作:
     * 1:打开服务端口(客户端就是通过这个端口与服务端建立链接)
     * 2:监听该服务端口，一旦一个客户端链接，则会返回一个Socket实例，并通过这个
     * Socket实例与链接的客户端进行交互
     * <p>
     * 如果我们将Socket比喻为"电话"，那么ServerSocket相当于是"总机"。
     */
    private ServerSocket server;
    //该数组用于存放所有客户端的输出流，用于广播消息给所有客户端
    private PrintWriter[] allOut = {};

    public Server() {
        try {
            /*
                实例化ServerSocket的同时需要指定打开的服务端口，客户端就是通过该
                端口建立链接的。
                如果该端口已经被操作系统其他程序占用了，那么这里会抛出异常:
                java.net.BindException:address already in use
                         绑定异常        地址    已经     被占用了
             */
            System.out.println("正在启动服务端...");
            server = new ServerSocket(8088);
            System.out.println("服务端启动完毕!");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void start() {
        try {
            /*
                ServerSocket提供的方法:
                Socket accept()
                该方法是一个阻塞方法，调用后开始等待，直到一个客户端与服务端建立链接
                为止，此时该方法会立即返回一个Socket，通过这个Socket与该客户端交互
             */
            while (true) {
                System.out.println("等待客户端链接...");
                Socket socket = server.accept();
                System.out.println("一个客户端链接了!");
                //启动一个线程来处理与该客户端的交互
                Runnable handler = new ClientHandler(socket);
                Thread t = new Thread(handler);
                t.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

    /**
     * 该线程任务负责与特定客户端交互
     */
    private class ClientHandler implements Runnable{
        private Socket socket;
        private String host;//客户端的地址信息

        public ClientHandler(Socket socket){
            this.socket = socket;
            //通过socket获取远端计算机的地址信息
            host = socket.getInetAddress().getHostAddress();
        }

        public void run(){
            PrintWriter pw = null;
            try {
                 /*
                        通过Socket的方法:
                        InputStream getInputStream()
                        获取一个字节输入流，可以读取来自远端计算机发送过来的字节数据
                     */
                InputStream in = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(in, StandardCharsets.UTF_8);
                BufferedReader br = new BufferedReader(isr);

                //通过socket获取输出流，用于给客户端发送消息
                OutputStream out = socket.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(out,StandardCharsets.UTF_8);
                BufferedWriter bw = new BufferedWriter(osw);
                pw = new PrintWriter(bw,true);
                //将该客户端的输出流存入共享数组allOut中
                //1对allOut扩容
                synchronized (Server.this) {
                    allOut = Arrays.copyOf(allOut,allOut.length+1);
                    //2将pw存入共享数组最后一个位置
                    allOut[allOut.length-1] = pw;
                }
                sendMessage(host + "上线了，在线人数：" +allOut.length);

                String line;
                    /*
                        服务端在读取客户端消息这里，如果客户端没有调用socket.close()与服务端
                        正常断开连接(例如客户端直接被杀掉了进程等操作)，那么服务端这里会抛出
                        一个异常:SocketException: Connection reset
                        这是由于客户端非正常操作导致的，服务端无法通过逻辑避免该异常的产生。
                     */
                while ((line = br.readLine()) != null) {

                    System.out.println(host+"说:" + line);
                    //将消息回复给所有客户端
                    sendMessage(host + "说:" + line);

                }
            }catch(IOException e){
                e.printStackTrace();
            }finally{
                synchronized (Server.this) {
                    for(int i=0; i<allOut.length; i++){
                        if(pw.equals(allOut[i])){
                            allOut[i] = allOut[allOut.length-1];
                            allOut = Arrays.copyOf(allOut, allOut.length-1);
                            break;
                        }
                    }
                }
                sendMessage(host + "下线了，在线人数：" +allOut.length);
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        private void sendMessage(String message){
            synchronized (Server.this) {
                for(int i=0; i<allOut.length; i++){
                    allOut[i].println(message);
                }
            }

        }
    }

}
