package io;

import java.io.*;

public class OOSDemo {
    public static void main(String[] args) throws IOException {
        Person person = new Person("p1",18,"female", new String[]{"1", "2"});
        FileOutputStream fos = new FileOutputStream("./person.obj");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        //解决序列化问题
        oos.writeObject(person);
        oos.close();
    }
}
