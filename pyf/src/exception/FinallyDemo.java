package exception;

public class FinallyDemo {
    public static void main(String[] args) {
        try {
            String str = null;
            System.out.println(str.length());
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            System.out.println("finally");
        }
    }
}
