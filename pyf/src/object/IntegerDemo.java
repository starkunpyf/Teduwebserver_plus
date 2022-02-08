package object;

public class IntegerDemo {
    public static void main(String[] args) {
        String line = "123";
        int a = Integer.parseInt(line);
        int b = Integer.valueOf(line);
        System.out.println(a);
        System.out.println(b);
        int c = 123;
        Integer i = c;
        c = i;
    }
}
