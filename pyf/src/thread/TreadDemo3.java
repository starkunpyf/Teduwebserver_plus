package thread;

public class TreadDemo3 {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i <10; i++){
                System.out.println("t1");
            }
        });
        Runnable r1 = new Runnable(){

            @Override
            public void run() {
                for (int i = 0; i <10; i++){
                    System.out.println("r1");
                }
            }
        };
        Runnable r2 = ()->{
            for (int i = 0; i <10; i++){
                System.out.println("r2");
            }
        };
        t1.start();
        Thread t2 = new Thread(r1);
        Thread t3 = new Thread(r2);
        t2.start();
        t3.start();
        }
}
