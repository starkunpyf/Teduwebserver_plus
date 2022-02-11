package thread;

public class CurrentThreadDemo {
    public static void dosome(){
        Thread t1 = Thread.currentThread();
        System.out.println(t1);
    }
    public static void main(String[] args) {
        Thread t1 = Thread.currentThread();
        System.out.println(t1);
        dosome();
    }
}
