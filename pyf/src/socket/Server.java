package socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket server;

    public Server() {
        try {
            System.out.println("server starting");
            server = new ServerSocket(8088);
            System.out.println("server started");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void start(){
        try {
            while (true) {
                System.out.println("waitting");
                Socket socket = server.accept();//阻塞方法
                System.out.println("connected");


                InputStream in = socket.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(in, "UTF-8");
                BufferedReader br = new BufferedReader(inputStreamReader);
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println("waitting");
                    System.out.println(line);
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}
