package socket;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    public Client(){
        try {
            System.out.println("正在连接服务端");
            socket = new Socket("176.17.2.29",8088);//127.0.0.1,176.17.2.29
            System.out.println("与服务端建立链接");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void Start(){
        try {
            OutputStream out = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(out, "UTF-8");
            BufferedWriter bw = new BufferedWriter(osw);
            PrintWriter pw = new PrintWriter(bw,true);
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String line = scanner.nextLine();
                pw.println(line);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.Start();
    }
}
