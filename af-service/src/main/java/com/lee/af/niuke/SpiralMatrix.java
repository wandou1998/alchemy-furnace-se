package com.lee.af.niuke;

import java.util.Scanner;

/**
 * 题目描述
 * 疫情期间，小明隔离在家，百无聊赖，在纸上写数字玩。他发明了一种写法：
 * 给出数字个数n和行数m（0 < n ≤ 999，0 < m ≤ 999），从左上角的1开始，按照顺时针螺旋向内写方式，依次写出2,3…n，最终形成一个m行矩阵。
 * 小明对这个矩阵有些要求：
 *
 * 每行数字的个数一样多
 * 列的数量尽可能少
 * 填充数字时优先填充外部
 * 数字不够时，使用单个*号占位
 * 输入描述
 * 输入一行，两个整数，空格隔开，依次表示n、m
 *
 * 输出描述
 * 符合要求的唯一矩阵
 *
 * 用例1
 * 输入：
 *
 * 9 4
 * 1
 * 输出：
 *
 * 1 2 3
 * * * 4
 * 9 * 5
 * 8 7 6
 * 1
 * 2
 * 3
 * 4
 * 说明：
 *
 * 9个数字写成4行，最少需要3列
 *
 * 用例2
 * 输入：
 *
 * 3 5
 * 输出：
 *
 * 1
 * 2
 * 3
 * *
 * *
 * 1
 * 2
 * 3
 * 4
 * 5
 * 说明：
 *
 * 3个数字写5行，只有一列，数字不够用*号填充
 *
 * 用例3
 * 输入：
 *
 * 120 7
 * 1
 * 输出：
 *
 * 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18
 * 46 47 48 49 50 51 52 53 54 55 56 57 58 59 60 61 62 19
 * 45 84 85 86 87 88 89 90 91 92 93 94 95 96 97 98 63 20
 * 44 83 114 115 116 117 118 119 120 * * * * * * 99 64 21
 * 43 82 113 112 111 110 109 108 107 106 105 104 103 102 101 100 65 22
 * 42 81 80 79 78 77 76 75 74 73 72 71 70 69 68 67 66 23
 * 41 40 39 38 37 36 35 34 33 32 31 30 29 28 27 26 25 24
 */
public class SpiralMatrix {
    public static void main(String[] args) {
       Scanner sc = new Scanner(System.in);
       int maxNum = sc.nextInt();
       int rows = sc.nextInt();
       sc.close();
       int cols = maxNum/rows +1;
       String[][] arr = new String[rows][cols];
       for(int i=0;i<rows;i++){
           for(int j=0;j<cols;j++){
               arr[i][j]="*";
           }
       }
       int i=1;
       int top=0,bottom=rows-1,left=0,right=cols-1;
       while (i< maxNum) {
           //方向1 从左到右
           for (int m=left; m<=right && i<=maxNum; m++) {
             arr[top][m] = String.valueOf(i++);
           }
           top++;
           //方向2 从上到下
          for (int m=top;m<=bottom && i<=maxNum;m++) {
              arr[m][right] = String.valueOf(i++);
          }
          right--;
          //方向3 从右到左
           for (int m=right;m>=left && i<=maxNum;m--) {
              arr[bottom][m] = String.valueOf(i++);
           }
           bottom--;
           //方向4 从下到上
           for (int m=bottom;m>=top && i<=maxNum;m--) {
              arr[m][left] = String.valueOf(i++);
          }
           left++;
       }
       for (int m=0;m<rows;m++){
           for (int n=0;n<cols;n++){
               System.out.print(arr[m][n]+" ");
           }
           System.out.println(); //换行
       }
    }
}
