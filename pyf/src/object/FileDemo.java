package object;

import java.io.File;
import java.io.IOException;

public class FileDemo {
    public static void main(String[] args) throws IOException {
        File file = new File("./a.txt");
        System.out.println(file.getAbsolutePath());
        System.out.println(file.getName());
        System.out.println(file.length());
        File file1 = new File("./test.txt");
        if (file1.exists()) {
            System.out.println("exist");
        }else{
            file1.createNewFile();
            System.out.println("create");
        }
    }
}
