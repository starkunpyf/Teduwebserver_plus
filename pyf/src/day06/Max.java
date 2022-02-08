package day06;

import java.util.Arrays;

public class Max {
    public static void main(String[] args) {
        int[] arr = new int[10];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int)(Math.random()*100);
        }
        System.out.println(Arrays.toString(arr));

        for (int i = 0; i < arr.length-1; i++) {
            for (int j = 0;j < arr.length-1-i; j++) {
                if (arr[j]>arr[j+1]) {
                    int temp = 0;
                    temp = arr[j+1];
                    arr[j+1] = arr[j];
                    arr[j] = temp;
                }
            }
        }

        System.out.println(Arrays.toString(arr));
        func1();
    }
    public static void func1() {
        System.out.println("fun1");
    }
}
