package fruitmarket;

import java.util.Scanner;

public class Cart {
    public static double calculateSum(int[] arr) {
        Apple a = new Apple();
        Banana b = new Banana();
        Pear c = new Pear();
        double sum = 0;
        sum = sum + arr[0] * a.getPrice();
        sum = sum + arr[1] * b.getPrice();
        sum = sum + arr[2] * c.getPrice();
        if (sum >= 100) {
            sum = sum * 0.95;
        }
        return sum;
    }

    public static boolean calculateSum(int[] arr, Fruit goods, int newNum) {
        Apple a = new Apple();
        
        Banana b = new Banana();
        Pear c = new Pear();
        double sum = 0;
        sum = sum + arr[0] * a.getPrice();
        sum = sum + arr[1] * b.getPrice();
        sum = sum + arr[2] * c.getPrice();
        sum = sum + goods.getPrice() * newNum;
        if (sum >= 200) {
            System.out.println("总金额超过200，四肢给你打错位");
            System.out.println("请输入购买数量");
            return false;

        }
        return true;
    }


    public static void main(String[] args) {

        int[] shoppingtrolley = new int[3];
        while (true) {
            System.out.println("欢迎来到水果店，本店售卖苹果，香蕉，梨三种水果。");
            System.out.println("输入代表水果的数字购买水果，选择水果数量。");
            System.out.println("一次购买水果不能超过200元，输入-1时结账退出购买");
            System.out.println("苹果 -- 1 香蕉 -- 2 梨 -- 3 退出请输入-1");
            Scanner scan = new Scanner(System.in);
            if (scan.hasNextLine()) {
                String str = scan.nextLine();
                if (str.equals("1")) {
                    System.out.println("请输入购买数量");
                    Fruit goods = new Apple();
                    while (true) {
                        if (scan.hasNextInt()) {
                            int num = scan.nextInt();
                            if (calculateSum(shoppingtrolley, goods, num)) {
                                shoppingtrolley[0] = shoppingtrolley[0] + num;
                                break;
                            }

                        } else {
                            System.out.println("请正确输入数字");
                            break;
                        }
                    }

                } else if (str.equals("2")) {
                    System.out.println("请输入购买数量");
                    Fruit goods = new Banana();
                    while (true) {
                        if (scan.hasNextInt()) {

                            int num = scan.nextInt();
                            if (calculateSum(shoppingtrolley, goods, num)) {
                                shoppingtrolley[1] = shoppingtrolley[1] + num;
                                break;
                            }
                        } else {
                            System.out.println("请正确输入数字");
                            break;
                        }
                    }
                } else if (str.equals("3")) {
                    System.out.println("请输入购买数量");
                    Fruit goods = new Pear();
                    while (true) {
                        if (scan.hasNextInt()) {

                            int num = scan.nextInt();
                            if (calculateSum(shoppingtrolley, goods, num)) {
                                shoppingtrolley[2] = shoppingtrolley[2] + num;
                                break;
                            }
                        } else {
                            System.out.println("请正确输入数字");
                            break;
                        }
                    }
                } else if (str.equals("-1")) {
                    System.out.println("欢迎下次光临");
                    double result = calculateSum(shoppingtrolley);
                    System.out.println("总金额为" + result);
                    return;
                } else {
                    System.out.println("请正确输入数字");
                    continue;
                }
            }
        }
    }
}
class A{

}
class B extends A{
    void say(){
        System.out.println("say");
    }
}
