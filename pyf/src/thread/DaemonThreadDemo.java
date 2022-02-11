package thread;

public class DaemonThreadDemo {
    public static void main(String[] args) {
        Thread rose = new Thread(()->{
            for (int i = 0; i <5; i++){
                System.out.println("let me go");
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            System.out.println("dead");
        });

        Thread jack = new Thread(()->{
            while(true){
                System.out.println("you jump i jump");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        rose.start();
        jack.setDaemon(true);
        jack.start();

    }
}
