package io;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

//缓冲字符流
public class PWDemo {
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter pw = new PrintWriter("./pw.txt","utf-8");
        pw.println("testline1");
        pw.println("testline2");
        pw.close();
    }
}
