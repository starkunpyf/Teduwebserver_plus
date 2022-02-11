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
                System.out.println("waitting for connect");
                Socket socket = server.accept();//阻塞方法
                System.out.println("connected");
                Runnable handler = new ClientHander(socket);
                Thread t1 = new Thread(handler);
                t1.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
    private class ClientHander implements Runnable {
        private Socket socket;
        private String host;
        public ClientHander(Socket socket) {
            this.socket = socket;
            host = socket.getInetAddress().getHostAddress();
        }
        @Override
        public void run() {
            try {
                InputStream in = socket.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(in, "UTF-8");
                BufferedReader br = new BufferedReader(inputStreamReader);
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println("waitting for client "+host+":");
                    System.out.println(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
