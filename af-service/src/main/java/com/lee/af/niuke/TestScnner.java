package com.lee.af.niuke;

import java.util.Scanner;

public class TestScnner {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("输入结果是：");
        // 注意 hasNext 和 hasNextLine 的区别
        while (in.hasNextInt()) { // 注意 while 处理多个 case
            int a = in.nextInt();
            System.out.println("a:"+a);
            System.out.println("结果1："+ getResult(a));
        }
    }

    public static int  getResult(int a) {
        if (a<=0) {
            return 0;
        }
        int resulta=a/3;
        int remain = a%3;
        int  result = resulta;
        while (resulta + remain >=2 ) {
            if (resulta + remain == 2) {
                result++;
                resulta=0;
                remain=0;
                break;
            }
            else {
                int total = resulta + remain;
                resulta=total/3;
                remain = total%3;
                result = result + resulta;
            }

        }
        return result;
    }
}
