package thread;

class MyThread1 extends Thread{
    public void run() {
        for(int i = 0; i <10; i++){
            System.out.println("thread1");
        }
    }
}
class MyThread2 extends Thread{
    public void run() {
        for(int i = 0; i <10; i++){
            System.out.println("thread2");
        }
    }
}
public class TreadDemo1 {

    public static void main(String[] args) {
        Thread t1 = new MyThread1();
        Thread t2 = new MyThread2();
        t1.start();
        t2.start();
    }
}
