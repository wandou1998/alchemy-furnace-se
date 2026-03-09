package com.lee.af.niuke;

import java.util.HashMap;
import java.util.Scanner;

public class Hfszxl {
    void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("开始输入：");
        String s = sc.next();
        int k = sc.nextInt();
        HashMap<Character, Integer> base = new HashMap<>();
        for (int i=0; i<=s.length()-1; i++) {
            char c = s.charAt(i);
            base.put(c, base.getOrDefault(c,0)+1);
        }
        /**
         * 从0开始模拟，每次k个连续的正整数
         */
        int i=0;
        while(i<1000-k+1) {
            HashMap<Character, Integer> compareMap = new HashMap<>();
            for (int j=i;j<i+k;j++) {
                String num = String.valueOf(j);
                for (int m=0; m<=num.length()-1; m++) {
                    char c = num.charAt(m);
                    compareMap.put(c, compareMap.getOrDefault(c,0)+1);
                }
            }
            //开始对比
            boolean isMatch = true;
            for (Character c : base.keySet()) {
                if (compareMap.get(c) == null || compareMap.get(c).compareTo(base.get(c)) != 0) {
                    isMatch = false;
                    break;
                }
            }
            if (isMatch) {
                System.out.println(i);
                return;
            }
            i++;
        }
    }

}
