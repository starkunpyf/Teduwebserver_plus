package thread;

public class JoinDemo {
    static boolean isFinished = false;//表示图片是否下载完成
    public static void main(String[] args) {
        Thread downloadThread = new Thread(()->{
            for (int i = 0; i <=100; i++){
                System.out.println("down"+i+"%");
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("succeed");
            isFinished = true;
        });

        Thread show = new Thread(()->{
            System.out.println("show 显示文字");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("文字显示完成");
            try {
                downloadThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("开始显示图片");
            if(!isFinished){
                throw new RuntimeException("图片加载失败");
            }
            System.out.println("图片显示完成");
        });
        downloadThread.start();
        show.start();


    }
}
